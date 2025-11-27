import zio._
import zio.http.Server
import Routing.routes


object Main extends ZIOAppDefault:
  override def run =
    Server.serve(routes).provide(Server.default)
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