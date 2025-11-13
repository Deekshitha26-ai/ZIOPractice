

import zio._
import zio.http._
import zio.http.Method
import zio.http.Handler
import zio.http.Method.GET
import zio.http.Method.POST

object MyRoutes{
    val app =
        Routes(

            GET / "hello"  -> Handler.text("hello"),
            GET / "health-check" -> Handler.ok,
            POST / "echo" ->
                handler  { (req: Request) => 
                    req.body.asString.map(Response.text)
                
                }.sandbox
        )
    val runServ = Server.serve(app).provide(Server.default)
}