package com.google.rc.server.db

import org.scalaquery.session.Database
import com.google.rc.shared.ShUtil

object SkDb {

  def apply(): Database =
      Database.forURL("jdbc:mysql://127.0.0.1:3306/" + ShUtil.fromHexS("73656375726974796b6973735f736b31") 
          + "?user=root&password=" + ShUtil.fromHexS("6b6924246149307462734f21"), driver = "com.mysql.jdbc.Driver")
}