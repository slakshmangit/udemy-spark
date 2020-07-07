//package sourceCode.spark
//
//import com.sundogsoftware.spark.DegreesOfSeparation.{createStartingRdd, hitCounter}
//import org.apache.log4j._
//import org.apache.spark._
//import org.apache.spark.rdd._
//import org.apache.spark.util.LongAccumulator
//
//import scala.collection.mutable.ArrayBuffer
//
///** Finds the degrees of separation between two Marvel comic book characters, based
//  *  on co-appearances in a comic.
//  */
//object DegreesOfSeparation2 {
//
//  // The characters we want to find the separation between.
//  val startCharacterID = 5306 //SpiderMan
//  val targetCharacterID = 14 //ADAM 3,031 (who?)
//
//  var hitCounter: Option[LongAccumulator] = None
//
//  type bfsData = (Array[Int], Int, String)
//  type bfsNode = (Int, bfsData)
//
//  def convertToBfs(line: String): bfsNode = {
//    val fields = line.split("\\s+")
//
//    val heroId = fields(0).toInt
//
//    var connections: ArrayBuffer[Int] = ArrayBuffer()
//
//    for(connection <- 1 to (fields.length - 1)){
//      connections += fields(connection).toInt)}
//
//    var color:String ="WHITE"
//
//    var distance:Int = 9999
//
//    if(heroId === startCharacterID)
//      {
//        color = "GRAY"
//        distance = 0
//      }
//
//    return (heroId, (connections.toArray, distance, color))
//  }
//
//  def createStartingRdd(sc:SparkContext): RDD[bfsNode] = {
//    val inputFile = sc.textFile("../marvel-graph.txt")
//    inputFile.map(convertToBfs)
//  }
//
//  def main(args: Array[String]): Unit ={
//
//    Logger.getLogger("org").setLevel(Level.ERROR)
//
//    // Create a SparkContext using every core of the local machine
//    val sc = new SparkContext("local[*]", "DegreesOfSeparation")
//
//    hitCounter = Some(sc.longAccumulator("Hit Counter"))
//
//    var iterationRdd = createStartingRdd(sc)
//
//    var iteration:Int = 0
//    for (iteration <- 1 to 10) {
//
//
//    }
//
//
//  }
//}