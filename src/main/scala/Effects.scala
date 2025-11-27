
import zio.*
import zio.Console.*


object Effects{

// Testing succeed type and the effects that goes with it 
def succEffect(a: String) : UIO [String]=
  ZIO.succeed("always succeeds") 

// Testing fail type and the effcts that go with it 

def failEffect(age:Int): IO[String, Int] =
  if(age < 18 ) ZIO.fail("Underage")
  else ZIO.succeed(age)

// testing attempt type 
/** ZIO.attempt is used when the method might throw an exception 
 It catches the the exception and turns into a failure of your effect **/

def attemptEffect(x:Int, y: Int): Task [Int] =
  ZIO.attempt(x/y)
  
// Zipping ==> Combining two effects 
def zipEffect(a: Int) : UIO[(String, Int)] =
  val zipped: ZIO[Any,Nothing,(String,Int)]= ZIO.succeed("4").zip(ZIO.succeed(2))
  zipped


// Printing the results

 def succResult: Task[Unit] =
    for {
     a <- succEffect("hello ")
    _<- Console.printLine(a)
    }yield()
    
// failure= string
// success = int 

/**but here while printing the value in console, console return IOException as return type.
So we cannot simply use console.printLine to print the value 
We should conver the error to string and .mapError does that **/

 def failResult: IO[String, Unit] =
    for {
    //  a <- succEffect("hello ")
    a <- failEffect(12)
    _<- Console.printLine(a).mapError(e=> e.getMessage)
    } yield()

 def attemptResult: Task[Unit]=
  for {
    a <- attemptEffect(2,0)
    _<- Console.printLine(a) // failure= arithemetic error, success = 
  }yield()

 def zippResult : Task[Unit]=
  for {
    a <- zipEffect(1)
    _<-Console.printLine(a)
  }yield()
}

//TODO: Zipright and ZipLeft functions  
// TODO: Fibers
// TODO: Parallelism
// TODO: Timeout





