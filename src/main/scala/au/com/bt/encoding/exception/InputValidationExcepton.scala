package au.com.bt.encoding.exception

/**
  * Throws in case if the input string doesn't meet the entry criteria.
  * @param msg the error message
  */
case class InputValidationException(msg: String)  extends Exception(msg)