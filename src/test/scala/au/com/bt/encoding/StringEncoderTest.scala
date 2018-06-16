package au.com.bt.encoding

import org.scalatest.FunSuite

import au.com.bt.encoding.exception.InputValidationException

/**
  * Tests functions of @StringEncoder
  */
class StringEncoderTest extends FunSuite {

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
