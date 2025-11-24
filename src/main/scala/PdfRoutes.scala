import zio.*
import zio.http.*

object PdfRoutes {

  private val headers: Headers =
    Headers.empty
      .addHeader("Content-Type", "application/pdf")
      .addHeader("Content-Disposition", """attachment; filename="my-details.pdf"""")

  val routes: Routes[Any, Response] =
    Routes(
      Method.GET / "pdf" -> handler {
        val details = UserDetails(
          fullName    = "Deekshitha",
          email       = "sdsdf@gmail.com",
          phoneNumber = "324521323",
          address     = "thunderbay"
        )

        val pdfBytes: Array[Byte] = PdfService.genFilledPdf(details)

        Response(
          status  = Status.Ok,
          headers = headers,
          body    = Body.fromChunk(Chunk.fromArray(pdfBytes))
        )
      }
    )
}
