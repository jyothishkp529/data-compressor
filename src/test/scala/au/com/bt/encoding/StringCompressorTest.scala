package au.com.bt.encoding

import au.com.bt.encoding.exception.InputValidationException
import org.scalatest.FunSuite

/**
  * Tests functions of @StringCompressor
  */
class StringCompressorTest extends FunSuite {

  test("Encoder input contains only Non-Numeric characters.") {
    assertThrows[InputValidationException] {
      StringCompressor.validate("ABB45CDD", 2)
    }
  }

  test("Minimum Occurancy must be 2.") {
    assertThrows[InputValidationException] {
      StringCompressor.validate("ABBCDD", 1)
    }
  }

  test("Minimum Occurancy value must not be greater than the length of input string.") {
    assertThrows[InputValidationException] {
      StringCompressor.validate("ABBCDD", 10)
    }
  }
}