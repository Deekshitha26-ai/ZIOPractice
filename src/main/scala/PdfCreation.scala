import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField
import org.apache.pdfbox.pdmodel.common.PDRectangle

object DocumentCreation {

  def main(args: Array[String]): Unit = {
    // Create a new empty PDF document
    val document = new PDDocument()

    // Add a blank page
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

    //Adding Text box
    //In PDFBox, a PDF document is structured like this:
    // PDDocument → DocumentCatalog → AcroForm → Form Fields
    val acroForm = new PDAcroForm(document)
    document.getDocumentCatalog().setAcroForm(acroForm)

    // Creating a text field
    val textField = new PDTextField(acroForm)
    textField.setPartialName("inputBox")

    //Postiton of the box(x,y,width,height)
    val rect = new PDRectangle(100, 650,500,50)

    val widget = textField.getWidgets.get(0)
    widget.setRectangle(rect)
    // TEXT SIZE
    textField.setDefaultAppearance("/Helv 20 Tf 0 g") // font size = 14
    widget.setPage(page)


    // Add widget to page 
    page.getAnnotations.add(widget)

    //Add field to form 
    acroForm.getFields()

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
