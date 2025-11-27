import zio.* 
import zio.http
import zio.http.Method.GET
import zio.http.Handler
import zio.http.Response
import zio.http.Routes
import zio.http.Method
// import scala.compiletime.ops.string
import zio.http.Request
import zio.http.codec.PathCodec.{string, int}
import zio.http.Method.POST
// import scala.compiletime.ops.int


object Routing{

    val routes = Routes(
        GET / "declarativeRoute" -> Handler.text("test passed"),
        
        GET / "typesafeRoute" / string("name") / int("age")->
            Handler.fromFunction[(String,Int, Request)] { 
                // case is used incase of pattern matching 
                case(a, x, b) =>
                    Response.text(s"Hello $a your age is $x")
                    
        },

        POST/"echo" -> Handler.fromFunctionZIO[Request] {
            (req: Request) =>
                req.body.asString.map(Response.text(_))
        }.sandbox,
    )
    
}