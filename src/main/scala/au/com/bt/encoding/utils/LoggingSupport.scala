package au.com.bt.encoding.utils


import org.apache.log4j.Logger

/**
  * LOG4j support
  */
trait LoggingSupport {
  val logger = Logger.getLogger(getClass)
}
