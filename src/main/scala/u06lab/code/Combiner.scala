package u06lab.code

/** 1) Implement trait Functions with an object FunctionsImpl such that the code in TryFunctions works correctly. */

object GivenCombiners:
  given Combiner[Double] = new Combiner[Double]:
    def unit: Double = 0.0
    def combine(a: Double, b: Double): Double = a + b
  given Combiner[String] = new Combiner[String]:
    def unit: String = ""
    def combine(a: String, b: String): String = a + b
  given Combiner[Int] = new Combiner[Int]:
    def unit: Int = Int.MinValue
    def combine(a: Int, b: Int): Int = if a > b then a else b


trait Functions:
  def sum(a: List[Double]): Double
  def concat(a: Seq[String]): String
  def max(a: List[Int]): Int // gives Int.MinValue if a is empty
  def combiner[A: Combiner](a: Seq[A]): A
object FunctionsImpl extends Functions:
  import GivenCombiners.given
  override def sum(a: List[Double]): Double = combiner(a)
  override def concat(a: Seq[String]): String = combiner(a)
  override def max(a: List[Int]): Int = combiner(a)
  override def combiner[A: Combiner](a: Seq[A]): A =
    a.foldLeft(summon[Combiner[A]].unit)((acc, x) => summon[Combiner[A]].combine(acc, x))
/*
 * 2) To apply DRY principle at the best,
 * note the three methods in Functions do something similar.
 * Use the following approach:
 * - find three implementations of Combiner that tell (for sum,concat and max) how
 *   to combine two elements, and what to return when the input list is empty
 * - implement in FunctionsImpl a single method combiner that, other than
 *   the collection of A, takes a Combiner as input
 * - implement the three methods by simply calling combiner
 *
 * When all works, note we completely avoided duplications..
 */

trait Combiner[A]:
  def unit: A
  def combine(a: A, b: A): A

@main def checkFunctions(): Unit =
  import GivenCombiners.given

  val f: Functions = FunctionsImpl
  println(f.sum(List(10.0, 20.0, 30.1))) // 60.1
  println(f.sum(List())) // 0.0
  println(f.concat(Seq("a", "b", "c"))) // abc
  println(f.concat(Seq())) // ""
  println(f.max(List(-10, 3, -5, 0))) // 3
  println(f.max(List())) // -2147483648
