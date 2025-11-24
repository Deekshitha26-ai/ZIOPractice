import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font

object DocumentCreation {

  def main(args: Array[String]): Unit = {
    // Create a new empty PDF document
    val document = new PDDocument()

    val page = new PDPage()
    document.addPage(page)

    val content = new PDPageContentStream(document, page)

    //Content stream acts like the canvas for writing in the pdf 
    content.beginText()
    content.setFont(PDType1Font.HELVETICA_BOLD,18)
    content.newLineAtOffset(100,700) // these are the (x,y) corrdinates
    content.showText("Hello PDF")
    content.endText()
    content.close()
    try {
      // Save the PDF to a file
      document.save("my_doc.pdf")
      println("PDF created")
    } finally {
      // Always close the document
      document.close()
    }
  }

}
