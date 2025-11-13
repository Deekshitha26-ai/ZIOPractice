// package com.axiommd.webview.ZIO

// import zio.* 
// import zio.test._
// import zio.test.Assertion.equalTo
// import zio.http._

// object ExampleSpec extends ZIOSpecDefault {

//   def spec = suite("http")(
//     test("should be ok") {
//       val app = Handler.ok.toRoutes
//       val req = Request.get(URL(Path.root))
//       assertZIO(app.runZIO(req))(equalTo(Response.ok))
//     }
//   )
// }
