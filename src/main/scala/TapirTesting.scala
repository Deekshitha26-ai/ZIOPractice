import zio.*
import sttp.tapir.ztapir.*       
import zio.http.Routes
import zio.http.Response
import sttp.tapir.server.ziohttp.ZioHttpInterpreter


object TapirTesting{
    val testEndpoint = endpoint
        .get 
        .in("hello"/"world")
        .out(stringBody)
        .zServerLogic[Any]{_=>ZIO.succeed(s"Tapir Test successful")}

// val tapirRoutes: Routes[Any, Response] =
//     ZioHttpInterpreter().toHttp(testEndpoint)
}

// package com.axiommd.webview.ZIO

// import zio._
// import sttp.client3._
// import sttp.client3.tofu.json._
// import sttp.tapir._
// import sttp.tapir.generic.auto._
// import sttp.tapir.json.zio._
// import zio.json._

// object tapirTest{
// type Limit = Int
// type AuthToken = String

// case class BooksQuery(genre: String, year: Int)
// object BooksQuery {
//   implicit val decoder: JsonDecoder[BooksQuery] = DeriveJsonDecoder.gen[BooksQuery]
//   implicit val encoder: JsonEncoder[BooksQuery] = DeriveJsonEncoder.gen[BooksQuery]
// }

// case class Book(title: String)
// object Book {
//   implicit val decoder: JsonDecoder[Book] = DeriveJsonDecoder.gen[Book]
//   implicit val encoder: JsonEncoder[Book] = DeriveJsonEncoder.gen[Book]
// }

// // Define an endpoint
// val booksListing: PublicEndpoint[(BooksQuery, Limit, AuthToken), String, List[Book], Any] =
//   endpoint.get
//     .in(
//       ("books" / path[String]("genre") / path[Int]("year"))
//         .tupled
//         .mapTo[BooksQuery]
//     )
//     .in(query[Limit]("limit").description("Maximum number of books to retrieve"))
//     .in(header[AuthToken]("X-Auth-Token"))
//     .errorOut(stringBody)
//     .out(jsonBody[List[Book]])
// }