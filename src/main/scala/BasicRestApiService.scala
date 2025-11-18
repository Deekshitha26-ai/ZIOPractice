

import zio.*
import zio.http.*
import zio.json.*

/** 

curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{"id": "1", "task": "Learn ZIO", "completed": false}'

GET ALL:

curl http://localhost:8080/todos
curl -i http://localhost:8080/todos

GET ONE:

curl http://localhost:8080/todos/1
curl -i http://localhost:8080/todos/1

DELETE ONE:

curl -X DELETE http://localhost:8080/todos/1
curl -v -X DELETE http://localhost:8080/todos/1

*/


case class ToDo(
    id: String,
    task: String,
    completed: Boolean
) derives JsonEncoder, JsonDecoder



// we want to create mock database as a service,

trait TodoRepository:
    def getAll(): Task[Map[String, ToDo]]
    def get(id: String): Task[Option[ToDo]]
    def create(todo: ToDo): Task[Unit]
    def delete(id: String): Task[Boolean]

//  implementation of that trait for each environment.
class DevTodoRepository(ref: Ref[Map[String, ToDo]]) extends TodoRepository:

    def getAll(): Task[Map[String, ToDo]] =
         ref.get

    def get(id: String): Task[Option[ToDo]] =
        ref.get.map(map => map.get(id))

    def create(todo: ToDo): Task[Unit] =
        ref.update(map => map + (todo.id -> todo))

    def delete(id: String): Task[Boolean] =
        ref.modify { todos =>
            val newTodos = todos - id
            val found = todos.contains(id)
            (found, newTodos)
        }

object DevTodoRepository:
    // create a value as a ZLayer for dependency injection.
    val live: ZLayer[Any, Nothing, TodoRepository] = ZLayer.fromZIO {
        val ref = Ref.make(Map.empty[String, ToDo])
        ref.map(ref => new DevTodoRepository(ref)) 
    }
    // `ZLayer.fromZIO` converts the ZIO effect into a ZLayer



object BasicRestApiWithService extends ZIOAppDefault:


    def makeRoutes(repo: TodoRepository): Routes[Any, Response] = Routes(

        Method.GET / "todos" -> handler { (_: Request) =>
            repo.getAll()
                .mapError(Response.fromThrowable)   
                .map { todos =>                     
                    if todos.isEmpty then
                        Response.status(Status.NoContent)
                    else
                        Response.json(todos.values.toList.toJsonPretty)
                }
        },

        // get a single todo value by its `id`:
        Method.GET / "todos" / string("id") -> handler { (id: String, _: Request) =>
            repo.get(id)
                .mapError(Response.fromThrowable)
                .map { maybeToDo =>
                    maybeToDo match
                        case Some(todo) => Response.json(todo.toJsonPretty)
                        case None => Response.status(Status.NoContent)
                }
        },

    
        Method.POST / "todos" -> handler { (req: Request) =>
            (for
                body <- req.body.asString
                todo <- ZIO.fromEither(body.fromJson[ToDo]).mapError(new RuntimeException(_))
                _    <- repo.create(todo)
            yield
                Response.json(todo.toJsonPretty)
                        .status(Status.Created))
                        .mapError(Response.fromThrowable)
        },

    
        Method.DELETE / "todos" / string("id") -> handler { (id: String, _: Request) =>
            repo.delete(id)                         // returns Task[Boolean]
                .tap(exists => ZIO.logInfo(s"DELETE: id=$id, value exists=$exists"))
                .tapError(error => ZIO.logError(s"DELETE: id=$id, error=${error.getMessage}"))
                .mapError(Response.fromThrowable)  
                .map { exists =>                   
                    if exists then
                        Response.status(Status.NoContent)
                    else
                        // found in the datastore:
                        Response(
                            status = Status.NotFound,
                            body = Body.fromString(s"Todo with id '$id' not found")
                        )
                }
        }

    ) //end of Routes(...)

    val program = for
        repo <- ZIO.service[TodoRepository]   
        app   = makeRoutes(repo)
        _    <- Server.serve(app)
    yield ()


    val serverConfig = Server.Config.default
        .port(8080)               
        .keepAlive(true)          
        .idleTimeout(30.seconds)      

    val run = program.provide(
        ZLayer.succeed(serverConfig),
        DevTodoRepository.live,   
        Server.live
    )




