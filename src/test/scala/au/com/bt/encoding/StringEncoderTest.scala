package au.com.bt.encoding

import org.scalatest.FunSuite

import au.com.bt.encoding.exception.InputValidationException

class StringEncoderTest extends FunSuite {

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

  test("ABBBCCDDD with 3 must return A3BCC3D") {
    assert( StringEncoder.compressStr("ABBBCCDDD",3).get == "A3BCC3D")

  }
  test("ABBBBCCCDDDDD with 4 must return A4BCCC5D") {
    assert( StringEncoder.compressStr("ABBBBCCCDDDDD",3).get != "A3BCC3D")

  }

  test("ABCDEFG with 3 must return same string without compression") {
    assert( StringEncoder.compressStr("ABCDEFG",3).get == "ABCDEFG")

  }
}
