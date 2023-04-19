package u06lab.code

import org.scalactic.source.Position

/**
 * An object modelling a Solitaire game.
 */
object Solitaire extends App:

  type Position = (Int, (Int, Int))
  type Board = (Int, Int)
  type Solution = List[Position]

  def placeMarks(board: Board) =
    val width = board._1
    val height = board._2

    val startPos: Position = (1, (width / 2, height / 2))
    getPossiblePositions(width, height)


  def getPossiblePositions(width: Int, height: Int): Seq[(Int, Int)] =
    for
      i <- 0 until width
      j <- 0 until height
    yield (i, j)

  def render(solution: Seq[(Int, Int)], width: Int, height: Int): String =
    val reversed = solution.reverse
    val rows =
      for y <- 0 until height
          row = for x <- 0 until width
                    number = reversed.indexOf((x, y)) + 1
          yield if number > 0 then "%-2d ".format(number) else "X  "
      yield row.mkString
    rows.mkString("\n")

  extension (p: Position)
    private def isJumpPossible(dest: Position): Boolean =
      // Get coordinates of the current position
      val (x, y) = p._2
      def isDiagonalJumpPossible: Boolean =
        (for
          x1 <- (x-2) to (x+2) by 4
          y1 <- (y-2) to (y+2) by 4
        yield
          println(s"($x1, $y1)")
          (0, (x1 + 2, y1 + 2))).contains(dest)
      isDiagonalJumpPossible

  val testPos: Position = (0,(1,1))
  val testDest: Position = (0,(4,4))
  val value = testPos.isJumpPossible(testDest)

  println(render(solution = Seq((0, 0), (2, 1)), width = 3, height = 3))
