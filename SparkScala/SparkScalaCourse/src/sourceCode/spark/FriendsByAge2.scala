package sourceCode.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._

object FriendsByAge2{
  
  def readData(sc: SparkContext) = {
     val lines = sc.textFile("../fakefriends.csv")
     (lines)
  }
  
  final case class Friend(id: Int, Name: String, Age: Int, FriendsCount: Int)
  
  def parseData(line: String): Friend = {
   val fields = line.toString().split(",")
   val friend:Friend = Friend(fields(0).toInt, fields(1).toString(), fields(2).toInt, fields(3).toInt)
   return friend   
  }
  
   def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
        
    val spark = SparkSession
      .builder
      .appName("FriendsByAge2")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()
      
    val lines = readData(spark.sparkContext)
    
    val friends = lines.map(parseData)
    
    import spark.implicits._
    val schemaFriends = friends.toDS()
    
    
    //schemaFriends.printSchema()
    
    //schemaFriends.show()
    
    schemaFriends.createOrReplaceTempView("friends")
    
    val friendsByAge = spark.sql("SELECT Age, SUM(FriendsCount) FROM  friends GROUP BY Age")
    
    friendsByAge.collect().foreach(println)
    
    spark.stop()
    
   }
}