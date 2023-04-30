package u06lab.code

import org.scalactic.source.Position

import scala.collection.immutable.TreeSeqMap
import scala.math

/**
 * An object modelling a Solitaire game.
 */
object Solitaire extends App:

  import SolitaireExtensionMethods.*

  // e.g. (0, (0,0)), where _._1 is the value of the cell and _._2 is the position of the cell
  type Cell = (Int, Position)
  type Position = (Int, Int)
  type Size = (Int, Int)
  // It can also represent a partial solution
  type Board = Seq[Cell]

  private def solve(board: Board)(using size: Size): Seq[Board] =
    import SolitaireExtensionMethods.*
    if board.isSolved then Seq(board)
    else
      val possibleMoves: Seq[Position] = board.getDiagonalJumps ++ board.getCardinalJumps
      possibleMoves.flatMap(move => solve(board.setCurrentPosition(move)))

  private def drawBoard(board: Board)(using size: Size): String =
    val rows =
      for y <- 0 until size._2
          row = for x <- 0 until size._1
                    number = board.find(_._2 == (x, y)).get._1
          yield if number > 0 then "%-2d ".format(number) else "X  "
      yield row.mkString
    rows.mkString("\n")


  object SolitaireExtensionMethods:
    extension (board: Board)(using size: Size)
      private def isCellInBoard(pos: Position): Boolean = (pos._1 >= 0 && pos._1 < size._1) && (pos._2 >= 0 && pos._2 < size._2)
      def setStartingPosition(pos: Position): Board =
        if isCellInBoard(pos) then board.map(p => if p._2 == pos then (1, pos) else p) else throw IllegalArgumentException("Position is not in the board")
      def setCurrentPosition(pos: Position): Board =
        val currPos: Cell = board.findCurrentPosition
        board.map(p => if p._2 == pos then (currPos._1 + 1, pos) else p)
      def findCurrentPosition: Cell = board.maxBy(_._1)
      def getValueOfCellFromPosition(pos: Position): Int = board.find(_._2 == pos).get._1
      def getDiagonalJumps: Seq[Position] =
        val dist = 1
        val currPos: Cell = board.findCurrentPosition
        val (x, y) = currPos._2
        for
          i <- -dist to dist by 2
          j <- -dist to dist by 2
          jumpableCell = (x + i, y + j)
          if isCellInBoard(jumpableCell)
          if getValueOfCellFromPosition(jumpableCell) == 0
        yield
          jumpableCell

      def getCardinalJumps: Seq[Position] =
        val dist = 2
        val currPos: Cell = board.findCurrentPosition
        val (x, y) = currPos._2
        (for
          i <- -dist to dist by 2
          jumpableCell = (x + i, y)
          if isCellInBoard(jumpableCell)
          if getValueOfCellFromPosition(jumpableCell) == 0
        yield jumpableCell)
          .appendedAll(
            for
              j <- -dist to dist by 2
              jumpableCell = (x, y + j)
              if isCellInBoard(jumpableCell)
              if getValueOfCellFromPosition(jumpableCell) == 0
            yield jumpableCell)

      def isSolved: Boolean = getDiagonalJumps.isEmpty && getCardinalJumps.isEmpty


  //println(render(solution = Seq((0, 0), (2, 1)), width = 3, height = 3))

  given size: Size = (5, 5)

  val board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))

  private val solutions: Seq[Board] = solve(board.setStartingPosition((size._1 / 2, size._2 / 2)))

  // Filter all the solutions that have contain at least a cell with value 0
  private val solutionsWithoutZero: Seq[Board] = solutions.filter(_.forall(_._1 != 0))

  println("Number of possible solutions: " + solutions.size)
  println("Number of possible solutions without 0: " + solutionsWithoutZero.size)
  for
    elem <- solutionsWithoutZero.head
  do
    println(elem)
  println(drawBoard(solutionsWithoutZero.head))