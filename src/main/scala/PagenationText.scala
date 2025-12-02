import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import org.apache.pdfbox.pdmodel.font.PDType1Font

object PaginationTest {

  case class Layout(
      leftMargin: Float,
      contentTopY: Float,
      bottomY: Float,
      lineHeight: Float
  )
  // Function to create a box around the text 
    private def drawBox(document: PDDocument,page: PDPage,layout: Layout): Unit = {

    val mediaBox = page.getMediaBox
    val boxHeight = (layout.contentTopY - layout.bottomY) + 14f
    val boxWidth  = mediaBox.getWidth - 100f

    val boxStream =
        new PDPageContentStream(document, page, AppendMode.APPEND, true, true)

    boxStream.addRect(layout.leftMargin, layout.bottomY, boxWidth, boxHeight)
    boxStream.stroke()
    boxStream.close()
    }

  def main(args: Array[String]): Unit = {
    val document = new PDDocument()
    val page     = new PDPage()
    document.addPage(page)

    try {
      val mediaBox   = page.getMediaBox
      val pageBottom = mediaBox.getLowerLeftY
      val pageTop    = mediaBox.getUpperRightY

      val layout = Layout(
        leftMargin  = 50f,
        contentTopY = pageTop - 50f,       // 50 pts down from top
        bottomY     = pageBottom + 50f,    // 50 pts up from bottom
        lineHeight  = 14f
      )
      // Create a box
        drawBox(document, page, layout)

      val lines: List[String] =
        List.tabulate(200)(i => s"Sample line $i in pagination test")
      // Add content which has pagenation feature 
      addPaginatedText(document, page, layout, lines)

      document.save("target/pagination_test.pdf")
    } finally {
      document.close()
    }
  }

  // Function to add content inside the box which can be pagenated
  private def addPaginatedText(document: PDDocument,firstPage: PDPage,layout: Layout,lines: List[String]): Unit = {

    var currentPage = firstPage
    var currentY    = layout.contentTopY

    var contentStream =
      new PDPageContentStream(document, currentPage, AppendMode.APPEND, true, true)

    try {
      for (line <- lines) {

        if (currentY < layout.bottomY + layout.lineHeight) {
          contentStream.close()

          val newPage = new PDPage(currentPage.getMediaBox)
          document.addPage(newPage)

          currentPage = newPage
          currentY    = layout.contentTopY
          
          drawBox(document, currentPage, layout)
          
          contentStream =
            new PDPageContentStream(document, currentPage, AppendMode.APPEND, true, true)
        }

        contentStream.beginText()
        contentStream.setFont(PDType1Font.HELVETICA, 10)
        contentStream.setNonStrokingColor(0, 0, 0)
        contentStream.newLineAtOffset(layout.leftMargin, currentY)
        contentStream.showText(line)
        contentStream.endText()

        currentY -= layout.lineHeight
      }
    } finally {
      contentStream.close()
    }
  }
}
