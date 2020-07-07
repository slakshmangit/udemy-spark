package sourceCode.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object MostPopularSuperHero {

  def readFields(input: String) ={

    val fields = input.split("\\s+")

    (fields(0).toInt, fields.length - 1)
  }

  def mapNames(name: String) : Option[(Int, String)] = {
    val mappedNames = name.toString.split('\"')
    if(mappedNames.length > 1)
      Some(mappedNames(0).trim().toInt, mappedNames(1))
    else
      None
  }

  case class HeroLookUp (id: Int, name: String)

  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "MostPopularSuperHero")

    val input = sc.textFile("../Marvel-graph.txt")

//    input.foreach(println)

    val mappedInput = input.map(readFields)

    val pairings = mappedInput.reduceByKey((x,y) => x+y)

//    mappedInput.foreach(println) //5639 - 3

    val names = sc.textFile("../marvel-names.txt")

//    names.foreach(println)

    val mappedNames = names.flatMap(mapNames)

    val topHero = pairings.map( (x) => (x._2, x._1)).max()

//    mappedNames.filter(x => x._1 == 501).foreach(println)

    println(mappedNames.lookup(topHero._2)(0))

//    mappedInput.sortByKey().foreach(println)




  }
}
