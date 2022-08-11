package org.example

import org.apache.sedona.core.serde.SedonaKryoRegistrator
import org.apache.sedona.sql.utils.SedonaSQLRegistrator
import org.apache.sedona.viz.core.Serde.SedonaVizKryoRegistrator
import org.apache.spark.SparkConf
import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.sql.SparkSession

object Test {
  def main(args: Array[String]): Unit = {
    var sparkSession = SparkSession.builder()
      .master("local[*]") // Delete this if run in cluster mode
      .appName("readTestScala") // Change this to a proper name
      // Enable Sedona custom Kryo serializer
      .config("spark.serializer", classOf[KryoSerializer].getName) // org.apache.spark.serializer.KryoSerializer
      .config("spark.kryo.registrator", classOf[SedonaVizKryoRegistrator].getName) // org.apache.sedona.viz.core.Serde.SedonaVizKryoRegistrator

      .getOrCreate() // org.apache.sedona.core.serde.SedonaKryoRegistrator

    SedonaSQLRegistrator.registerAll(sparkSession)

    var rawDf = sparkSession.read.format("csv").option("delimiter", "\t").option("header", "false")
      .load("file:///E:\\学习\\samples\\geo\\usa-county.csv")
    rawDf.createOrReplaceTempView("rawdf")
    rawDf.show()
    rawDf.printSchema()

    var spatialDf = sparkSession.sql(
      """
        |SELECT ST_GeomFromWKT(_c1) AS countyshape, _c2, _c3
        |FROM rawdf
  """.stripMargin)
    spatialDf.show()
    spatialDf.printSchema()

  }

}
