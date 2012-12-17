package com.google.rc.server

import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.simple.StaticQuery

import com.google.gwt.user.server.rpc.RemoteServiceServlet
import com.google.rc.client.GreetingService
import com.google.rc.server.db.SkDb
import com.google.rc.server.db.SkDbQueries.PaymentsForVat
import com.google.rc.server.db.SkDbQueries.PaymentsForVat.Q
import com.google.rc.server.db.SkDbQueries.PaymentsForVat.R
import com.google.rc.server.db.SkDbQueries.PaymentsForVat.tupleToResult

class GreetingServiceImpl extends RemoteServiceServlet with GreetingService {

  def greetServer(input: String): String = {
    var list: List[PaymentsForVat.T] = null
    var listR: List[PaymentsForVat.R] = null
    SkDb() withSession {
      //		    var d: (Int, String, String, String) = null;
      //		    for (d <- SkDbQueries.paymentsForVat("SK")) 
      //		      println(d._1 + "\t" + d._2)

      {
        import PaymentsForVat._
        for (r <- Q("SK"))
          println(r.iPayment + "\t" + r.payerName)
          
        for (r <- Q("SK")) 
          println(r._1 + "\t" + r._2)
          
        val l = Q("SI").mapResult(R(_)).list
        
        //for (p <- v) yield p.country
        
        list = Q("SI").list
      }
      
      //Alternatively use full select query returning list of VO
      listR = PaymentsForVat.q("CZ")
 

      
      //		    for (p <- PaymentsForVat.Q("SK")) 
      //		      println(p._1 + "\t" + p._2)
      //		    list = PaymentsForVat.Q("SI").list

//      		    for (p <- SkDbQueries.PaymentsForVat.Q("AR"))
//      		      yield "Row " + p._1 + "\t" + p._2 + "\t" + p._3 + "\t" + p._4;
    }
    "Payments for VAT accounting:\n" + listR.mkString("\n")
    
  }

}