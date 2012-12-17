package com.google.rc.server.db

import org.scalaquery.ql.extended.{ExtendedTable => Table}
import java.sql.Date
import org.scalaquery.ql.PrimaryKey

object SkDbObjects {
  
  object AggregatePayment extends Table[(Int, Date, String, String, String, String, String, Boolean, String, String, String, Int)]("aggregatePayment") {
    def i = column[Int]("I", O.PrimaryKey, O.AutoInc)
    def created = column[Date]("created")
    def clientNo = column[String]("clientNo")
    def payerName = column[String]("payer_name")
    def txnId = column[String]("txn_id")
    def amtGross = column[String]("amt_gross")
    def amtFee = column[String]("amt_fee")
    def invalid = column[Boolean]("invalid")
    def cc = column[String]("cc")
    def planName = column[String]("planName")
    def periodCode = column[String]("periodCode")
    def iPayMethod = column[Int]("I_payMethod")
    def * = i ~ created ~ clientNo ~ payerName ~ txnId ~ amtGross ~ amtFee ~ invalid ~ cc ~ planName ~ periodCode ~ iPayMethod
    def payMethod = foreignKey("payMethod_fk", iPayMethod, PayMethod)(_.i)
    def geoCountry = foreignKey("geoCountry_fk", cc, GeoCountry)(_.cc)
  }
		  
  object PayMethod extends Table[(Int, String, String, String)]("payMethod") {
    def i = column[Int]("I", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email")
    def caption = column[String]("caption")
    def * = i ~ name ~ email ~ caption
  }
  
  object GeoCountry extends Table[(String, String, String, Boolean)]("geocountry") {
    def cc = column[String]("cc", O.PrimaryKey)
    def country = column[String]("country")
    def lang = column[String]("lang")
    def eu = column[Boolean]("eu")
    def * = cc ~ country ~ lang ~ eu
  }
  
  
  
}