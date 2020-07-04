
object LearningScala3 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(235); 
  // Functions
  
  // Format is def <function name>(parameter name: type...) : return type = { expression }
  // Don't forget the = before the expression!
  implicit def squareIt(x: Int) : Int = {
  	x * x
  };System.out.println("""squareIt: (x: Int)Int""");$skip(49); 
  
     
     def returnOne(): Int = { return 1};System.out.println("""returnOne: ()Int""");$skip(57); 
     
          
  def cubeIt(x: Int): Int = {x * x * x};System.out.println("""cubeIt: (x: Int)Int""");$skip(26); 
  
  println(squareIt(2));$skip(24); 
  
  println(cubeIt(2));$skip(125); 
  
  // Functions can take other functions as parameters
  
  def transformInt(x: Char, f: Int => Int) : Int = {
  	f(x)
  };System.out.println("""transformInt: (x: Char, f: Int => Int)Int""");$skip(42); 
  
  val result = transformInt(2, cubeIt);System.out.println("""result  : Int = """ + $show(result ));$skip(19); 
  println (result);$skip(207); val res$0 = 
  
  // "Lambda functions", "anonymous functions", "function literals"
  // You can declare functions inline without even giving them a name
  // This happens a lot in Spark.
  transformInt('a', x => x*x*x);System.out.println("""res0: Int = """ + $show(res$0));$skip(34); val res$1 = 
  
  transformInt(10, x => x / 2);System.out.println("""res1: Int = """ + $show(res$1));$skip(50); val res$2 = 
  
  transformInt(2, x => {val y = x * 2; y * y});System.out.println("""res2: Int = """ + $show(res$2));$skip(408); 
  
  // This is really important!
  
  // EXERCISE
  // Strings have a built-in .toUpperCase method. For example, "foo".toUpperCase gives you back FOO.
  // Write a function that converts a string to upper-case, and use that function of a few test strings.
  // Then, do the same thing using a function literal instead of a separate, named function.
  def toUpperCaseF(s: String): String = { s.toUpperCase };System.out.println("""toUpperCaseF: (s: String)String""");$skip(22); val res$3 = 

toUpperCaseF("star");System.out.println("""res3: String = """ + $show(res$3));$skip(19); val res$4 = 
"star".toUpperCase


case class Message(value: String){

}

case class Endpoint(prompt: String){
 def send(m: Message) {
   println(this.prompt + " " + m.value)
 }
};System.out.println("""res4: String = """ + $show(res$4));$skip(201); 

def routeTo(m:Message, e:Endpoint) = {
  e.send(m)
};System.out.println("""routeTo: (m: LearningScala3.Message, e: LearningScala3.Endpoint)Unit""");$skip(46); 

routeTo(Message("hola"),Endpoint("sending"));$skip(51); 
routeTo(Message("hola"),Endpoint("sendDifferent"));$skip(55); 

def route(m:Message) = { (e: Endpoint) => e.send(m) };System.out.println("""route: (m: LearningScala3.Message)LearningScala3.Endpoint => Unit""");$skip(53); 
def routeN(m:Message)(e:Endpoint) = {
   e.send(m)
};System.out.println("""routeN: (m: LearningScala3.Message)(e: LearningScala3.Endpoint)Unit""");$skip(45); 

val routeFrench = route(Message("Bonjour"));System.out.println("""routeFrench  : LearningScala3.Endpoint => Unit = """ + $show(routeFrench ));$skip(42); 

route(Message("hi"))(Endpoint("sender"))}
}
