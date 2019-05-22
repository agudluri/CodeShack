import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Connection
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.spark.SparkContext
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.client.Put
import org.apache.spark.SparkConf
import org.apache.yetus.audience.InterfaceAudience


object poc {

  def main(args: Array[String]): Unit = {

    if (args.length < 1) {
      println("The program is missing the HDFS input file path")
      return
    }

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "poc")

    try {

      val input = sc.textFile("args(0)")

      val tableName = ""

      //    var connections: ArrayBuffer[Int] = ArrayBuffer()
      //    for ( connection <- 1 to (fields.length - 1)) {
      //        connections += fields(connection).toInt
      //      }

      System.setProperty("java.security.krb5.conf", "/etc/krb5.conf")
      System.setProperty("sun.security.krb5.debug", "true")

      val conf = HBaseConfiguration.create()

      conf.addResource(new Path("/hbase-site.xml"))
      conf.set("hadoop.security.authentication", "kerberos")
      conf.set("hbase.security.authentication", "kerberos")
      //conf.set(TableInputFormat.INPUT_TABLE, tableName)

      val hbaseContext = new HBaseContext(sc, conf)

      val userGroupInformation = UserGroupInformation.loginUserFromKeytabAndReturnUGI("XXX@abc.COM", "/u/xxxxx/XXXX.keytab")
      UserGroupInformation.setLoginUser(userGroupInformation)
      val connection: Connection = ConnectionFactory.createConnection(conf)
      println(connection)
      val admin = connection.getAdmin
      val tabl = connection.getTable(TableName.valueOf(tableName))


      val timeStamp = System.currentTimeMillis()

      hbaseContext.bulkPut[String](rdd, tabl,
        (putRecord) => {
          val put = new Put(Bytes.toBytes(putRecord))
          put.addColumn(Bytes.toBytes("CL"), Bytes.toBytes("PREPRO"), timeStamp)
          put

        })
    } finally {
      sc.stop()
    }




//    conf.set(TableInputFormat.INPUT_TABLE, tableName)
//    conf.set(TableInputFormat.INPUT_TABLE, tableName)


//    conf.set("hbase.zookeeper.quorum", "xxxxx1@abc.com,xxxxx2@abc.com,xxxxx3@abc.com")
//    conf.set("zookeeper.znode.parent", "/hbase-secure")
//    conf.setInt("hbase.client.scanner.caching", 10000)
//    conf.set("hbase.rpc.controllerfactory.class","org.apache.hadoop.hbase.ipc.RpcControllerFactory")
//    conf.set("hbase.rpc.controllerfactory.class","org.apache.hadoop.hbase.ipc.RpcControllerFactory")





//    spark-submit --master yarn-client \
//      --files "/etc/spark/conf/log4j.properties#yarn-log4j.properties" \
//      --principal XX@Z.NET --keytab /a/b/XX.keytab \
//      --conf spark.yarn.tokens.hbase.enabled=true \
//      --conf spark.driver.extraClassPath=$EXTRA_HBASE_CP \
//      --conf spark.executor.extraClassPath=$EXTRA_HBASE_CP \
//      --conf "spark.executor.extraJavaOptions=$KRB_DEBUG_OPTS -Dlog4j.configuration=yarn-log4j.properties" \
//      --conf spark.executorEnv.HADOOP_JAAS_DEBUG=true \
//      --class TestSparkHBase  TestSparkHBase.jar
//
//    spark-submit --master yarn-cluster --conf spark.yarn.report.interval=4000 \
//      --files "/etc/spark/conf/log4j.properties#yarn-log4j.properties" \
//      --principal XX@Z.NET --keytab /a/b/XX.keytab \
//      --conf spark.yarn.tokens.hbase.enabled=true \
//      --conf spark.driver.extraClassPath=$EXTRA_HBASE_CP \
//      --conf "spark.driver.extraJavaOptions=$KRB_DEBUG_OPTS -Dlog4j.configuration=yarn-log4j.properties" \
//      --conf spark.driverEnv.HADOOP_JAAS_DEBUG=true \
//      --conf spark.executor.extraClassPath=$EXTRA_HBASE_CP \
//      --conf "spark.executor.extraJavaOptions=$KRB_DEBUG_OPTS -Dlog4j.configuration=yarn-log4j.properties" \
//      --conf spark.executorEnv.HADOOP_JAAS_DEBUG=true \
//      --class TestSparkHBase  TestSparkHBase.jar


    lines.foreachRDD(rdd => {

      rdd.foreachPartition(iter => {

        val hConf = new HBaseConfiguration()
        val hTable = new HTable(hConf, "test")

        iter.foreach(record => {
          val i = +1
          val thePut = new Put(Bytes.toBytes(i))
          thePut.add(Bytes.toBytes("cf"), Bytes.toBytes("a"), Bytes.toBytes(record))

          //missing part in your code
          hTable.put(thePut);
        })
      })
    })







    def main(args: Array[String]): Unit = {

      val spark = SparkSession.builder().appName("sparkToHive").enableHiveSupport().getOrCreate()
      import spark.implicits._

      val config = HBaseConfiguration.create()
      config.set("hbase.zookeeper.quorum", "ip's")
      config.set("hbase.zookeeper.property.clientPort","2181")
      config.set(TableInputFormat.INPUT_TABLE, "tableName")

      val newAPIJobConfiguration1 = Job.getInstance(config)
      newAPIJobConfiguration1.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "tableName")
      newAPIJobConfiguration1.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])

      val df: DataFrame  = Seq(("foo", "1", "foo1"), ("bar", "2", "bar1")).toDF("key", "value1", "value2")

      val hbasePuts= df.rdd.map((row: Row) => {
        val  put = new Put(Bytes.toBytes(row.getString(0)))
        put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("value1"), Bytes.toBytes(row.getString(1)))
        put.addColumn(Bytes.toBytes("cf2"), Bytes.toBytes("value2"), Bytes.toBytes(row.getString(2)))
        (new ImmutableBytesWritable(), put)
      })

      hbasePuts.saveAsNewAPIHadoopDataset(newAPIJobConfiguration1.getConfiguration())
    }












    connection.close()

  }

}

