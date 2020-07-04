
object LearningScala3 {
  // Functions
  
  // Format is def <function name>(parameter name: type...) : return type = { expression }
  // Don't forget the = before the expression!
  implicit def squareIt(x: Int) : Int = {
  	x * x
  }                                               //> squareIt: (x: Int)Int
  
     
     def returnOne(): Int = { return 1}           //> returnOne: ()Int
     
          
  def cubeIt(x: Int): Int = {x * x * x}           //> cubeIt: (x: Int)Int
  
  println(squareIt(2))                            //> 4
  
  println(cubeIt(2))                              //> 8
  
  // Functions can take other functions as parameters
  
  def transformInt(x: Char, f: Int => Int) : Int = {
  	f(x)
  }                                               //> transformInt: (x: Char, f: Int => Int)Int
  
  val result = transformInt(2, cubeIt)            //> result  : Int = 8
  println (result)                                //> 8
  
  // "Lambda functions", "anonymous functions", "function literals"
  // You can declare functions inline without even giving them a name
  // This happens a lot in Spark.
  transformInt('a', x => x*x*x)                   //> res0: Int = 912673
  
  transformInt(10, x => x / 2)                    //> res1: Int = 5
  
  transformInt(2, x => {val y = x * 2; y * y})    //> res2: Int = 16
  
  // This is really important!
  
  // EXERCISE
  // Strings have a built-in .toUpperCase method. For example, "foo".toUpperCase gives you back FOO.
  // Write a function that converts a string to upper-case, and use that function of a few test strings.
  // Then, do the same thing using a function literal instead of a separate, named function.
  def toUpperCaseF(s: String): String = { s.toUpperCase }
                                                  //> toUpperCaseF: (s: String)String

toUpperCaseF("star")                              //> res3: String = STAR
"star".toUpperCase                                //> res4: String = STAR


case class Message(value: String){

}

case class Endpoint(prompt: String){
 def send(m: Message) {
   println(this.prompt + " " + m.value)
 }
}

def routeTo(m:Message, e:Endpoint) = {
  e.send(m)
}                                                 //> routeTo: (m: LearningScala3.Message, e: LearningScala3.Endpoint)Unit

routeTo(Message("hola"),Endpoint("sending"))      //> sending hola
routeTo(Message("hola"),Endpoint("sendDifferent"))//> sendDifferent hola

def route(m:Message) = { (e: Endpoint) => e.send(m) }
                                                  //> route: (m: LearningScala3.Message)LearningScala3.Endpoint => Unit
def routeN(m:Message)(e:Endpoint) = {
   e.send(m)
}                                                 //> routeN: (m: LearningScala3.Message)(e: LearningScala3.Endpoint)Unit

val routeFrench = route(Message("Bonjour"))       //> routeFrench  : LearningScala3.Endpoint => Unit = LearningScala3$$$Lambda$12
                                                  //| /1435804085@6a5fc7f7

route(Message("hi"))(Endpoint("sender"))          //> sender hi
}