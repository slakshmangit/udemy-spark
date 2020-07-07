package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object TotalSpentByCustomer {

  /* Mapper for the file and split fields */
  def splitFields(input: String) =
  {
    val fields = input.split(",")
    (fields(0).toInt, fields(2).toFloat)
  }

    def main(args: Array[String]): Unit =
  {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "TotalSpentByCustomer")

    val input = sc.textFile("../customer-orders.csv")

//    input.foreach(println)
    val mappedInput = input.map(splitFields)

    val results = mappedInput.reduceByKey((x,y) => x+y)

    val flipped = results.map(x => (x._2, x._1))

    val sortedResults = flipped.sortByKey().collect()

    sortedResults.foreach(println)



  }
}
