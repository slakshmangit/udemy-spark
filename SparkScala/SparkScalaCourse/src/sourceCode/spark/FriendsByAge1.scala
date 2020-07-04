package sourceCode.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object FriendsByAge1{
  
  def readData(sc: SparkContext) = {
     val lines = sc.textFile("../fakefriends.csv")
     (lines)
  }
  
  
  def parseData(line: String) = {
    val fields = line.toString().split(",")
    val age = fields(2).toInt
    val friendsCount = fields(3).toInt
    (age, friendsCount)
  }
  
   def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
        
    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "FriendsByAge1")
    
    //Call readData function by passing Spark Context
    val lines = readData(sc)
    
    //print the data
    //lines.foreach(println)
    
    //lines.map(parseData).foreach(println)
    
    val parsedLines = lines.map(parseData)
    
    //parsedLines.filter(_._1==68).foreach(println)
    
        
    //parsedLines.foreach(println)
    
    //parsedLines.reduceByKey(_+_).foreach(println)
    parsedLines.aggregateByKey(0)(_+_,_+_).foreach(println)
    
    
   }
}