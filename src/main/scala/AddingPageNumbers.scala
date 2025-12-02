import org.apache.pdfbox.pdmodel.PDDocument
import PaginationTest.Layout
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode


private def addPageNumbers(document: PDDocument,layout: Layout): Unit = {
  val totalPages = document.getNumberOfPages

  for (i <- 0 until totalPages) {
    val page     = document.getPage(i)
    val mediaBox = page.getMediaBox

    val cs = new PDPageContentStream(document,page,AppendMode.APPEND,true,true)

    val pageNumberText = s"Page ${i + 1}"

    cs.beginText()
    cs.setFont(PDType1Font.HELVETICA, 10)
    cs.setNonStrokingColor(0, 0, 0)

    // Center horizontally
    val fontSize    = 10f
    val textWidth   = PDType1Font.HELVETICA.getStringWidth(pageNumberText) / 1000 * fontSize
    val x           = (mediaBox.getWidth - textWidth) / 2

    // below a fixed margin from bottom
    val y           =  mediaBox.getLowerLeftY + 20f

    cs.newLineAtOffset(x, y)
    cs.showText(pageNumberText)
    cs.endText()

    cs.close()
  }
}
