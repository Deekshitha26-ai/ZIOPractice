import zio._


object Main extends ZIOAppDefault:
  override def run =
    for {
      // _ <- Calculator.output
      // _ <- Effects.outrunCalcput
      // _ <- FirstServer.runServer
      // _ <- MyRoutes.runServ
      _<- PathCodec.PathCodecExec
    } yield ()