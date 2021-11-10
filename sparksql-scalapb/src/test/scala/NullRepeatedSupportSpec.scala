import com.example.protos.demo.{Person, SimplePerson}
import org.apache.spark.sql.{SparkSession, types}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scalapb.spark.{EnabledNullRepeatedSupport, NoWrapperTypes, ProtoSQL, Udfs}

class NullRepeatedSupportSpec
    extends AnyFlatSpec
    with Matchers
    with BeforeAndAfterAll {
  val spark: SparkSession = SparkSession
    .builder()
    .appName("ScalaPB Demo")
    .master("local[2]")
    .getOrCreate()

  import spark.implicits.StringToColumn

  "parsing from json" should "work" in {
    val protoSQL = new ProtoSQL
      with Udfs
      with NoWrapperTypes
      with EnabledNullRepeatedSupport
    import protoSQL.implicits._
    val df = spark.read
      .schema(protoSQL.schemaFor[SimplePerson].asInstanceOf[types.StructType])
      .json("./sparksql-scalapb/src/test/assets/simple_person_null_repeated.json")
      .as[SimplePerson]
    df.show(false)
    df.collect() must contain theSameElementsAs Seq(
      SimplePerson().addTags("tag1").addNums(42),
      SimplePerson(),
      SimplePerson(),
      SimplePerson()
    )
  }
}
