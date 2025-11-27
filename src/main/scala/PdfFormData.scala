import zio.*
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec
import sttp.tapir.Schema


final case class PdfFormData(
  fullName: String,
  email: String,
  phoneNumber: String,
  address: String
)

object PdfFormData:
    implicit val codec: JsonCodec[PdfFormData] =
        DeriveJsonCodec.gen[PdfFormData]

        // for Tapir schema 
    given Schema[PdfFormData] = Schema.derived