

import zio._
import zio.http._
import zio.http.Method
import zio.http.Handler
import zio.http.Method.GET

object PathCodec{
    val app = 
        Routes(

        GET / "items" / int("itemId") ->
        handler { (itemId: Int, req: Request) =>
            Response.text(s"Requested item id is: $itemId")
  })
    val PathCodecExec = Server.serve(app).provide(Server.default)

}