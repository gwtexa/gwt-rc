package org.scalaquery.examples

// Import the session management, including the implicit threadLocalSession
import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql._
import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}
import com.google.rc.shared.ShUtil

object MysqlTest {
  def main(args: Array[String]) {


    val AbuseIncident = new Table[(Int, String, Int, Int)]("abuseIncident") {
      def I = column[Int]("I", O.PrimaryKey)
      def abuseTypeName = column[String]("abuseTypeName")
      def I_vbox = column[Int]("I_vbox")
      def cn = column[Int]("cn")
      def * = I ~ abuseTypeName ~ I_vbox ~ cn
    }

    Database.forURL("jdbc:mysql://127.0.0.1:3306/" + ShUtil.fromHexS("73656375726974796b6973735f736b31") + "?user=root&password=" + ShUtil.fromHexS("6b6924246149307462734f21"), 
        driver = "com.mysql.jdbc.Driver") withSession {

      // Iterate through all coffees and output them
//      println("\n\n\nIncidents:")
//      Query(AbuseIncident) foreach { case (i, abuseTypeName, i_vbox, cn) =>
//        println("  " + i + "\t" + cn)
//      }
      
      println("Abuses (concatenated by DB):")
      val q1 = for(c <- AbuseIncident if c.I_vbox === 141)
        yield ConstColumn("  ") ++ "\t" ++ c.abuseTypeName ++ "\t"
//        yield ConstColumn("  ") ++ "\t" ++ c.I.asColumnOf[String] ++ "\t" ++ c.I_vbox.asColumnOf[String] 
      q1 foreach println

      // Check the SELECT statement for that query
      println(q1.selectStatement)

      // Compute the number of coffees by each supplier
      println("Incidents per vbox:")
      val q4 = for {
        c <- AbuseIncident
        _ <- Query groupBy c.I_vbox
      } yield c.I_vbox ~ c.I_vbox.count
      // .get is needed because ScalaQuery cannot enforce statically that
      // the supplier is always available (being a non-nullable foreign key),
      // thus wrapping it in an Option
      q4 foreach { case (i_vbox, count) =>
        println("  " + i_vbox + ": " + count)
      }
    }
  }
}