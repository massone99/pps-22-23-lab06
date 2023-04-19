package u06lab.code

import org.scalatest.funsuite.AnyFunSuite

class SolitaireTest extends AnyFunSuite:
  // Test the method getPossiblePositions
  test("getPossiblePositions") {
    val positions = Solitaire.getPossiblePositions(3, 3)
    println(positions)
  }
