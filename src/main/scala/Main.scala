import zio._
import zio.http.Server


object Main extends ZIOAppDefault:
  override def run =
    Server.serve(PdfRoutes.routes)
      .provide(
        Server.default
      )
    // for {
    //   // _ <- Calculator.output
    //   // _ <- Effects.outrunCalcput
    //   // _ <- FirstServer.runServer
    //   // _ <- MyRoutes.runServ
    //   // _<- PathCodec.PathCodecExec
    //   // _<- BasicRestApiWithService.run
      
    // } yield ()