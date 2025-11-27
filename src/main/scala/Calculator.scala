

import zio._

sealed trait CalcError
case object DivisionByZero extends CalcError
case object InvalidOperation extends CalcError

object Calculator {
  def add(a: Int, b: Int):  Task[Int]=
    ZIO.attempt(a+b)

  def subtract(a: Int, b: Int): Task[Int] =
    ZIO.attempt(a - b)

  def multiply(a: Int, b: Int): Task[Int] =
    ZIO.attempt(a * b)

  def divide(a: Int, b: Int): Task[Int]=
    ZIO.attempt(a / b)


  val calculation: Task[Int] = for {
  ans <- Calculator.add(4,0)

  }
yield ans

  def runCalc = calculation.flatMap(result => Console.printLine(s"Result: $result"))
}