package sourceCode.spark

import org.apache.spark._
import org.apache.log4j._

object PoC{
  
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "PoC")
    val Names = sc.parallelize(List("John", "Joe", "Jack", "Joe"))
    val DistinctNames = Names.distinct()
    val countOfNames = DistinctNames.count()
      println("Number of Persons: " + countOfNames)
    val listOfNames = DistinctNames.collect()
      println("Persons List: ")
      listOfNames.sorted.foreach(println) 
    var x = 1
    while(x <= countOfNames)
    {
      println(x, listOfNames(x-1))
      x = x+1
    }
  }
}