package sourceCode.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
    

object PoC2{
  
  case class Person(ID:Int, name:String)
  case class IdCost(ID:Int, cost:Double)
  
  def namemapper(line:String): Person = {
    val fields = line.split(',')  
    
    val person:Person = Person(fields(0).toInt, fields(1))
    return person
  }
  
  def costmapper(line:String): IdCost = {
    val fields = line.split(',')  

    val idCost:IdCost = IdCost(fields(0).toInt, fields(1).toDouble)
    return idCost
  }
  
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession
      .builder
      .appName("PoC2")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()
    
    val IdCostLines = spark.sparkContext.textFile("../IdCost.csv")
    val IdNameLines = spark.sparkContext.textFile("../IdName.csv")
    //println(IdCostLines.collect().sorted.foreach(println))
    //println(IdNameLines.collect().sorted.foreach(println))
    
    val People = IdNameLines.map(namemapper)
    val PeopleCost = IdCostLines.map(costmapper)
    
     import spark.implicits._
    
     val schemaPeople = People.toDS
     val schemaCost = PeopleCost.toDS
    
    schemaPeople.printSchema()
    schemaCost.printSchema()
    
    schemaPeople.createOrReplaceTempView("people")
    schemaCost.createOrReplaceTempView("peopleCost")
    
    // SQL can be run over DataFrames that have been registered as a table
    val NameCost = spark.sql("SELECT name,sum(cost) FROM people JOIN peopleCost ON people.ID = peopleCost.ID group by name order by 2")
    
    val results = NameCost.collect()
    
    results.foreach(println)
     spark.stop()
    }
}