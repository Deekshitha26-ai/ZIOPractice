import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField
import org.apache.pdfbox.pdmodel.common.PDRectangle

object DocumentCreation {
  // Create a new empty PDF document
  val document = new PDDocument()
  // Add a blank page
  val page = new PDPage()
  document.addPage(page)
  //Adding Text box
  //In PDFBox, a PDF document is structured like this:
  // PDDocument → DocumentCatalog → AcroForm → Form Fields
  val acroForm = new PDAcroForm(document)
  document.getDocumentCatalog().setAcroForm(acroForm)

  def main(args: Array[String]): Unit = {

    val content = new PDPageContentStream(document, page)

    //Content stream acts like the canvas for writing in the pdf 
    content.beginText()
    content.setFont(PDType1Font.HELVETICA_BOLD,18)
    content.newLineAtOffset(100,700) // these are the (x,y) corrdinates
    content.showText("Hello PDF")
    content.endText()
    content.close()

    // createTextField("myTextBox",100,720,200,20, "/F1 10 Tf 0 g")
    // createTextField("myTextBox3",320,720,200,20, "/F1 10 Tf 0 g")
    // createTextField("myTextBox2",100,750,200,20, "/F1 10 Tf 0 g")

    val total_textFields = (1 to 20).toList
    val height = 20
    val gap = 2
    total_textFields.map{t =>
      createTextField(s"myTextBox$t",5, (height+gap)*(t), 200, height, "/F1 10 Tf 1 0 1 rg")
    }

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
  private def createTextField(name:String, xcoord:Int, ycoord:Int, width:Int, height:Int, daValue: String): Boolean = 
    // Creating a text field
    val textField = new PDTextField(acroForm)
    textField.setPartialName(name)

    //Postiton of the box(x,y,width,height)
    val rect = new PDRectangle(xcoord,ycoord,width,height)

    val widget = textField.getWidgets.get(0)
    widget.setRectangle(rect)
    // TEXT SIZE
    textField.setDefaultAppearance(daValue) // font size = 14
    widget.setPage(page)

    // Add widget to page 
    page.getAnnotations.add(widget)
}
