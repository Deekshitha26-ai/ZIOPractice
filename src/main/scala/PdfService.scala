import zio.* 
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import java.io.ByteArrayOutputStream
import PdfFormData._ 
import java.io.File

object PdfService2{
    // This function loads the pdf and fill the details and return the pdf as bytes
    
        def fillPdf(data:PdfFormData): ZIO[Any, Unit, File]={
            //First get the pdf and load it 
         ZIO.attempt{
            val templatePath= "src/main/resources/templates/details_form.pdf"
            val document = PDDocument.load(java.io.File(templatePath))

            try{
                val acroform: PDAcroForm=
                    Option(document.getDocumentCatalog.getAcroForm)
                    .getOrElse(throw new IllegalStateException("No Acroform in the pdf"))

                def assignVal(name: String, value: String ): Unit =
                    Option(acroform.getField(name))
                        .getOrElse(throw new IllegalStateException(s"Field '$name' not found"))
                        .setValue(value)

                //Assigning the values to the fields by calling the above helper functions
                assignVal("Text1", data.fullName)
                assignVal("Text2", data.email)
                assignVal("Text3", data.phoneNumber)
                assignVal("Text4", data.address)

                acroform.flatten()

                val tmp = File.createTempFile("details_form_", ".pdf")
                document.save(tmp)
                tmp
                } finally {
                document.close()
                }
            }.mapError(_ => ()) 
    }

    }







