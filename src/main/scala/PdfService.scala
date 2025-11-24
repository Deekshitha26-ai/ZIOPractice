import org.apache.pdfbox.pdmodel.PDDocument //Lib required for loading the pdf 
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import java.io.ByteArrayOutputStream
import java.nio.file.{Files, Paths} // Lib for getting the path of the file 

// Define/Case class for the parameters involved in the pdf doc
case class UserDetails(
    fullName : String ,
    email : String,
    phoneNumber : String,
    address : String 
)

// Creating a pdf service which can load the pdf 
object PdfService {

    private val templateResourcePath  = "templates/details_form.pdf"

    // function to fill denerate the pdf document filled 
    def genFilledPdf(details:UserDetails): Array[Byte]= {
        // Loading the pdf document from the path it is saved in 
        // PDDocument.load is the library 
        val is = Option(
            getClass.getClassLoader.getResourceAsStream(templateResourcePath)
            ).getOrElse {
            throw new RuntimeException(
                s"Template PDF not found on classpath: $templateResourcePath"
            )
            }

        val document = PDDocument.load(is)
        
        try {
            //PDAcroForm is the interative fields of the pdf 
            //The AcroForm maintains default:Font, Font size, Resources (fonts, colors), Form behavior (need appearances flag)
            val acroform: PDAcroForm = 
                Option(document.getDocumentCatalog.getAcroForm)
                    .getOrElse(throw new IllegalStateException("No AcroForm in template"))
                    
             Option(acroform.getField("Full Name")).foreach(_.setValue(details.fullName))
             Option(acroform.getField("Email")).foreach(_.setValue(details.email))
             Option(acroform.getField("Phone Number")).foreach(_.setValue(details.phoneNumber))
             Option(acroform.getField("Address")).foreach(_.setValue(details.address))  

            // This object stores the data in the memory not on the disk 
            // So here this code is preparing a place to store the generated PDF bytes 
             val baos = new ByteArrayOutputStream()
             document.save(baos)
             baos.toByteArray
        } finally {
            document.close
        }
             
        }



    }





