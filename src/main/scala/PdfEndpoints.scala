import zio.* 
import sttp.tapir.ztapir.*
import sttp.tapir.EndpointIO.annotations.endpointInput
import sttp.tapir.json.zio.* 
import PdfFormData.*
import PdfService2.fillPdf 
import zio.http.Routes
import zio.http.Response
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
// import sttp.tapir.server.ziohttp.ZioHttpInterpreter

object PdfEndpoints{
    val fillPdfEndpoint = endpoint
        .post
        .in("pdf" / "fill")
        .in(jsonBody[PdfFormData])          
        .out(
            header[String]("Content-Type")  // we will set "application/pdf"
                .and(fileBody))          // PDF bytes in response
        .zServerLogic[Any] { data =>
            fillPdf(data).map { file =>
            ("application/pdf", file)     
            }
      }
    
    val tapirRoutes: Routes[Any, Response] =
        ZioHttpInterpreter().toHttp(fillPdfEndpoint)
}