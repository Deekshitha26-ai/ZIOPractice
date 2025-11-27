import zio.*
import zio.http.Server
import Routing.routes
// import TapirTesting.tapirRoutes
import zio.http.Routes
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio.http.Response
import PdfService2.* 
import PdfEndpoints.* 
// import TapirTesting


object Main extends ZIOAppDefault:
   
    override def run =
     
    Server.serve(tapirRoutes).provide(Server.default)
    // for {
    //   _<- Effects.zippResult
    //   // _ <- Calculator.runCalc
    //   // _ <- Effects.succResult
    //   //  _<- Effects.attemptResult
    //   // _<- Effects.failResult
     
    //   // _ <- FirstServer.runServer
    //   // _ <- MyRoutes.runServ
    //   // _<- PathCodec.PathCodecExec
    //   // _<- BasicRestApiWithService.run
      
    // } yield ()