package au.com.bt.encoding


import org.rogach.scallop._

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

import au.com.bt.encoding.exception.InputValidationException
import au.com.bt.encoding.utils._

/**
  * Compress a given input String.
  */
object StringCompressor extends LoggingSupport{

  def main(args: Array[String]): Unit = {
    val conf = new CliConf(args)
    logger.info(s"Input :${conf.input()}, MinOccurence : ${conf.minOccurrence()}")
    action(conf.input(), conf.minOccurrence())
  }

  /**
    * Initiate the String Compression.
    *
    * @param input        the string to be compressed.
    * @param minOccurrence the minimum occurrence required for a continues character sequence eligible for compression.
    * @return 0 -> Success , 1-> Failure.
    */
  def action(input: String, minOccurrence: Int): Int = {
    try {
      validate(input, minOccurrence)
      StringEncoder.compressStr(input, minOccurrence) match {
        case Success(encodedStr) => {
          generateReport(input, encodedStr)
          ExeStatus.SUCCESS.id
        }
        case Failure(f) => ExeStatus.FAILURE.id
      }
    } catch {
      case ive: InputValidationException => {
        logger.error(ive.msg)
        ExeStatus.FAILURE.id
      }
    }
  }

  /**
    * Validates the input arguments.
    *
    * @param input        the string to be compressed.
    * @param minOccurrence the minimum occurrence required for a continues character sequence eligible for compression.
    */
  def validate(input: String, minOccurrence: Int): Unit = {

    val numPattern = "[[0-9]+]".r
    numPattern.findFirstMatchIn(input) match {
      case Some(s) => throw new InputValidationException(s"Numeric values are not allowed in input. Given " +
        s"input:$input ")
      case None => {
        if (minOccurrence < 2) throw new InputValidationException(s"Minimum Occurance should be 2.")
        if (minOccurrence > input.length) throw new InputValidationException(s"Invalid minOccurancy. It shoud not be " +
          s"more than input string's lenth.")
      }
    }
  }

  /**
    * Generate the Report.
    *
    * @param input  the input String to be compressed.
    * @param output the encoded String.
    */
  def generateReport(input: String, output: String): Unit = {
    logger.info(s"Input :$input, Compressed String :$output")
  }

  /**
    * The configuration class to hold the input arguments.
    *
    * @param args
    * the command line arguments array.
    */
  class CliConf(args: Seq[String]) extends
    ScallopConf(args) {
    banner(s"""Usage: ${getClass.getSimpleName} [OPTIONS] (all options required )\n\tOptions:""")
    val input = opt[String](name = "ip", descr = "Input String to be Compressed", required = true)
    val minOccurrence = opt[Int](name = "mo", descr = "Minimum Occurrence of character", required = true)
    verify()
  }

  /**
    * Holds the exit values when application terminates.
    */
  object ExeStatus extends Enumeration {
    val SUCCESS = Value(0)
    val FAILURE = Value(1)
  }
}

/**
  * Implements the the alogirithm to compress a given non-numeric String by encoding a continues substring in series
  * with its occurrence.
  */
object StringEncoder {

  /**
    * Compress a given input string for some characters, which are not less than 'minOccurrence'
    * in serial as a substring in the string
    *
    * @param input        the string to be compressed.
    * @param minOccurrence the minimum occurrence required for a continues character sequence eligible for compression.
    * @return the compressed String
    */
  def compressStr(input: String, minOccurrence: Int = 2): Try[String] = Try {

    // Returns the character on the head of the list.
    val secondChar = (rest: String) => rest.head

    def encode(firstChar: Char, occurrence: Int, rest: String, encodedStr: ListBuffer[String]): String = {
      if (rest.isEmpty) {
        if (occurrence >= minOccurrence)
          encodedStr += s"$occurrence$firstChar"
        else encodedStr += List.fill(occurrence)(firstChar).mkString
      }
      else {
        if (firstChar == secondChar(rest))
          encode(firstChar, occurrence + 1, rest.tail, encodedStr)
        else {
          if (occurrence >= minOccurrence)
            encode(rest.head, 1, rest.tail, encodedStr += s"$occurrence$firstChar")
          else
            encode(rest.head, 1, rest.tail, encodedStr += List.fill(occurrence)(firstChar).mkString)
        }
      }
      encodedStr.mkString
    }

    encode(input.head, 1, input.tail, ListBuffer())
  }
}
