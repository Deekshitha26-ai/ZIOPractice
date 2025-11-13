

import zio._
import zio.http._

object FirstServer:

  val runServer: ZIO[Any, Throwable, Nothing] =
    val app = Routes(Method.GET / "hello" -> handler(Response.text("Hello, ZIO HTTP world")))
    Server.serve(app).provide(Server.default)
