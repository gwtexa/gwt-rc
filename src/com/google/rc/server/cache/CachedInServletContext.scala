package com.google.rc.server.cache

import javax.servlet.ServletContext
import java.util.Date

trait CachedInServletContext {

  def getServletContext(): ServletContext
  lazy val ctx = getServletContext()

  
  //http://ehcache.org/documentation/recipes/thunderingherd
  
  def getCached[T](key: String, code: => T)(implicit cet: CacheExpiryTimeout = CacheExpiryTimeout(60)): T = {
    val keyLastUpdated = key + "_lastUpdatedCache"
    val cached = ctx.getAttribute(key)
    if (cached != null) {
      val lastUpdated = ctx.getAttribute(keyLastUpdated).asInstanceOf[Date]
      if (new Date().getTime() - lastUpdated.getTime() > cet.seconds) {
        val result = code
        ctx.setAttribute(key, result)
        ctx.setAttribute(keyLastUpdated, new Date)
        result
      } else {
    	cached.asInstanceOf[T]
      }
      
    } else {
      ctx.synchronized {
        val cached = ctx.getAttribute(key)
        if (cached == null) {
          ctx.setAttribute(key, code)
        } else {
          ctx.setAttribute(key, cached)
        }
        ctx.setAttribute(keyLastUpdated, new Date)
      }
      ctx.getAttribute(key).asInstanceOf[T]
    }
  }

}

/**
 * Singleton storing cache keys used for synchronizing access 
 */
object CacheKeyRegister {
  val map = collection.mutable.Map[String, AnyRef]()
  def lock(key: String): AnyRef = map.getOrElseUpdate(key, new AnyRef())
}

case class CacheExpiryTimeout(seconds: Int) {
}