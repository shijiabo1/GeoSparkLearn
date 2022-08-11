package org.example

import org.apache.sedona.sql.utils.SedonaSQLRegistrator
import org.apache.sedona.viz.core.Serde.SedonaVizKryoRegistrator
import org.apache.spark.serializer.KryoSerializer
import org.apache.spark.sql.SparkSession

object ReadPG {
  def main(args: Array[String]): Unit = {

    var spark = SparkSession.builder()
      .master("local[*]") // Delete this if run in cluster mode
      .appName("readTestScala")
      .config("spark.serializer", classOf[KryoSerializer].getName) // org.apache.spark.serializer.KryoSerializer
      .config("spark.kryo.registrator", classOf[SedonaVizKryoRegistrator].getName) // org.apache.sedona.viz.core.Serde.SedonaVizKryoRegistrator
      .getOrCreate()
    SedonaSQLRegistrator.registerAll(spark)
    spark.read
      .format("jdbc")
      .option("driver","org.postgresql.Driver")
      .option("url", "jdbc:postgresql://172.16.14.49:5432/gis")
      .option("dbtable", "basicdata.cities_84")
      .option("user", "giser")
      .option("password", "giser")
      .load()
      .createOrReplaceTempView("tmp")
    spark.sql(
      s"""
         |select ST_Contains(ST_GeomFromWKB(geom), ST_Point(117.857886,38.948589)),`省代码` from tmp
         |""".stripMargin).show(10000)
//    spark.sql(
//      s"""
//         |select ST_GeomFromWKB(geom) from tmp limit 1
//         |""".stripMargin).show(false)

  }

}
