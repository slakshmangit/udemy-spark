package com.sundogsoftware.spark

import org.apache.spark.sql._
import org.apache.log4j._
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.{DecisionTreeRegressor}

object PredictHousePrice {

  case class RegressionSchema(No: Integer, TransactionDate: Double,
                              HouseAge: Double,
                              DistanceToMRT: Double,
                              NumberConvenienceStores: Integer,
                              Latitude: Double,
                              Longitude: Double,
                              PriceOfUnitArea: Double)

  /** Our main function where the action happens */
  def main(args: Array[String]) {
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Use new SparkSession interface in Spark 2.0
    val spark = SparkSession
      .builder
      .appName("LinearRegressionDF")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()

    import spark.implicits._
    val dsRaw = spark.read.option("sep", ",")
      .option("header", "true")
      .option("inferschema", "true")
      .csv("../realestate.csv")
      .as[RegressionSchema]

    val assembler = new VectorAssembler().
      setInputCols(Array("HouseAge", "DistanceToMRT", "NumberConvenienceStores")).
      setOutputCol("features")

    val df = assembler.transform(dsRaw).select("PriceOfUnitArea", "features")

    val trainTest = df.randomSplit(Array(0.5,0.5))
    val trainDF = trainTest(0)
    val testDF = trainTest(1)

    val dt = new DecisionTreeRegressor().
      setFeaturesCol("features").
      setLabelCol("PriceOfUnitArea")

    val model = dt.fit(trainDF)

    val fullPredictions = model.transform(testDF).cache()

    val predictionLabel = fullPredictions.select("prediction", "PriceOfUnitArea").collect()

    for(prediction <- predictionLabel)
      {
        println(prediction)
      }


    spark.stop()

  }
}