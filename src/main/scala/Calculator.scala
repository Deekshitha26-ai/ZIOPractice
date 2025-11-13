

import zio._

sealed trait CalcError
case object DivisionByZero extends CalcError
case object InvalidOperation extends CalcError

object Calculator {
  def add(a: Int, b: Int): IO[CalcError, Int] =
    ZIO.succeed(a + b)

  def subtract(a: Int, b: Int): IO[CalcError, Int] =
    ZIO.succeed(a - b)

  def multiply(a: Int, b: Int): IO[CalcError, Int] =
    ZIO.succeed(a * b)

  def divide(a: Int, b: Int): IO[CalcError, Int] =
    if (b == 0) ZIO.fail(DivisionByZero)
    else ZIO.succeed(a / b)


  val calculation: IO[CalcError, Int] = for {
  sum  <- Calculator.add(4, 5)
  diff <- Calculator.subtract(sum, 2)
  prod <- Calculator.multiply(diff, 3)
               
  }
 yield sum


  def runCalc = calculation.flatMap(result => Console.printLine(s"Result: $result"))
}