package com.google.rc.server.db

import org.scalaquery.ql._
import org.scalaquery.ql.TypeMapper._
import com.google.rc.server.db.SkDbObjects._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ ExtendedTable => Table }
import org.scalaquery.session.Session
import org.scalaquery.simple.StaticQuery

object SkDbQueries {

  object PaymentsForVat {
    //Type of result
    type T = (Int, String, String, String, Boolean)
    //If needed we can create value object for storing result records
    case class R(iPayment: Int, payerName: String, payMethodName: String, country: String, eu: Boolean)
    object R { def apply(t: T) = new R(t._1, t._2, t._3, t._4, t._5) }

    implicit def tupleToResult(t: T): R = R(t)

    val Q = for (
      cc <- Parameters[String];
      ap <- AggregatePayment;
      pm <- ap.payMethod;
      gc <- ap.geoCountry if gc.cc === cc
    ) yield ap.i ~ ap.payerName ~ pm.name ~ gc.country ~ gc.eu

    def q(cc: String)(implicit session: Session): List[R] = Q(cc).mapResult(tupleToResult(_)).list
  }

  //Static query examples
  object StaticQueries {

    def examples(implicit session: Session): Unit = {
      val staticQ = StaticQuery.query[String, (Int, String)]("SELECT I, caption FROM payMethod WHERE name=?")
      println("\n\nStatic query\n" + staticQ.list("PayPal") + "\n\n\n")
      for (rr <- staticQ("PayPal")) println(rr._1 + "\t" + rr._2)
      
      val counts = StaticQuery.queryNA[Int]("SELECT count(*) FROM payMethod").list()
      println("No Arg static query result: " + counts(0))
    }

  }

}