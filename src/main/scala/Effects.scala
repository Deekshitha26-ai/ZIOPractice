


import zio.*
import zio.Console.*


object Effects{
  val a = 3;
  val b = 3;

  val run : ZIO[Any, String, Unit]=
  if (a==b)
     ZIO.attempt(println("Numbers are equal")).mapError(_.getMessage)

  else
    ZIO.fail("Numbers are not equal")
  def output= run
  

}



