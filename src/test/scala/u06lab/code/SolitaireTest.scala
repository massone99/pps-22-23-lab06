package u06lab.code

import org.junit.Assert.*
import org.junit.Test
import u06lab.code.Solitaire.*
import u06lab.code.Solitaire.SolitaireExtensionMethods.*


class SolitaireTest:
  @Test
  def testSetStartingPositionAndCurrentPosition(): Unit =
    given size: Size = (2, 2)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((1, 1))
    assertEquals((1, 1), board.findCurrentPosition._2)

  @Test
  def testGetValueOfCellFromPosition(): Unit =
    given size: Size = (2, 2)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((1, 1))
    assertEquals(1, board.getValueOfCellFromPosition((1, 1)))

  @Test
  def testGetDiagonalJumps(): Unit =
    given size: Size = (2, 2)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((1, 1))
    val jumps = board.getDiagonalJumps
    println(jumps)
    assertEquals(List((0, 0)), jumps)

  @Test
  def testGetDiagonalJumpsWithSmallGrid(): Unit =
    given size: Size = (2, 2)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((1, 1))
    val jumps = board.getDiagonalJumps
    assertEquals(1, jumps.length)

  @Test
  def testGetCardinalJumps(): Unit =
    given size: Size = (5, 5)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((2, 2))
    val jumps = board.getCardinalJumps
    println(jumps)
    assertEquals(List((0, 2), (4, 2), (2, 0), (2, 4)), jumps)

  @Test
  def testGetCardinalJumpsWithSmallGrid(): Unit =
    given size: Size = (2, 2)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((2, 2))
    val jumps = board.getCardinalJumps
    println(jumps)
    assertEquals(List.empty, jumps)

  @Test
  def testSetCurrentPosition(): Unit =
    given size: Size = (3, 3)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((size._1 / 2, size._2 / 2))
    // Get available jumps
    val jumps: Seq[Position] = board.getDiagonalJumps
    board = board.setCurrentPosition(jumps.head)
    println(board)
    assertEquals(jumps.head, board.findCurrentPosition._2)


  @Test
  def testSetCurrentPositionIntensive(): Unit =
    given size: Size = (3, 3)

    var board: Board = for i <- 0 until size._1; j <- 0 until size._2 yield (0, (i, j))
    board = board.setStartingPosition((size._1 / 2, size._2 / 2))
    println(board)

    var jumps = board.getDiagonalJumps ++ board.getCardinalJumps
    while
      jumps.nonEmpty
    do
      val head = jumps.head
      board = board.setCurrentPosition(head)
      jumps = board.getDiagonalJumps ++ board.getCardinalJumps
      println("Jumps size: " + jumps.size)
      println(board)

    assertTrue(board.isSolved)