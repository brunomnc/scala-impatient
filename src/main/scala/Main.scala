import scala.math.BigInt._
import scala.util.Random
import scala.collection.immutable.TreeMap
import java.util.TimeZone.getAvailableIDs
import scala.io._
import java.util.Calendar._


object Main {
  def main(args: Array[String]): Unit = {
    chapter4()
  }

  def chapter2() : Unit = {
    val hello = "Hello"
    var cnt = 1
    for(i<-0 until hello.length()) {
      cnt *= hello(i).toByte
    }
    println(cnt)
    def sum(x: Int, y: Int): Int = x + y
    val total = hello.map(c => c.toInt).foldLeft(1)(_ * _)
    println(total)
  }

  //Arrays
  def chapter3() : Unit = {
    val a = Array(1,2,3,4,5)
    val result = for(e<-a) yield 2*e
    println(a.toList, result.toList)

//    for(e<-a if a%2==0) yield e*2
    a.filter(_%2 == 0).map(2*_)

    //--Exercises--
    /*
    Write a code snippet that sets a to an array of n random integers between 0
    (inclusive) and n (exclusive).
    */
    val rd = Array(for(i<- 0 until 10) yield Random.nextInt(60))
    println(rd.toList)
    /*
    Write a loop that swaps adjacent elements of an array of integers. For example,
    Array(1, 2, 3, 4, 5) becomes Array(2, 1, 4, 3, 5)
    */
    val sw = for(i <- 0 until a.length) yield if(i % 2 == 1) a(i - 1) else {if(i == a.length - 1) a(i) else a(i + 1)}

    println(sw.toList)

    /*
    Given an array of integers, produce a new array that contains all positive
    values of the original array, in their original order, followed by all values that
    are zero or negative, in their original order.
     */
    val b = Array(1,-1,2,3,-4,-5,9,-8,0)
    val c = b.filter(_>0) ++ b.filter(_<=0)
    println(c.toList)

    /*
     How do you compute the average of an Array[Double]?
     */
    val d = Array(-5, 1.1, 2.2, 5)
    val avgd = d.sum/d.length
    println(avgd)

    /*
    How do you rearrange the elements of an Array[Int] so that they appear in
    reverse sorted order? How do you do the same with an ArrayBuffer[Int]?
     */
    val e = b.sortWith(_<_)
    println(e.toList)

    /*
    Make a collection of all time zones returned by java.util.TimeZone.getAvailableIDs
    that are in America. Strip off the "America/" prefix and sort the result.
     */
    val tzs = getAvailableIDs.filter(_.contains("America")).toList
    println(tzs)
  }
  //Mapping
  def chapter4() = {
    /*
    Set up a map of prices for a number of gizmos that you covet. Then produce
    a second map with the same keys and the prices at a 10 percent discount.
     */
    val a = Map("potato" -> 10, "banana"-> 15, "onion" -> 5, "corncob" -> 3)
    val dsct = for((n, v) <- a) yield (n,v*0.9)
    println(a, dsct)

    /*
    Write a program that reads words from a file. Use a mutable map to count
    how often each word appears.
    At the end, print out all words and their counts
     */
    val in = Source.fromFile("randomtext_gibberish_p.txt").getLines()
    val words = for(line <- in) yield line
    val mapped = words.map(w => (w.length, w))
//    for(w <- mapped) println(w)

    /*
    Repeat the preceding exercise with a sorted map, so that the words are
    printed in sorted order.
     */
    val swords = TreeMap(mapped.toArray:_*)
    for(w <- swords) println(w)

    /*
    Define a linked hash map that maps "Monday" to java.util.Calendar.MONDAY, and
    similarly for the other weekdays. Demonstrate that the elements are visited
    in insertion order
     */
    val week = Map("Monday" -> MONDAY, "Tuesday" -> TUESDAY, "Wednesday" -> WEDNESDAY, "Thursday" -> THURSDAY, "Friday" -> FRIDAY, "Saturday" -> SATURDAY, "Sunday" -> SUNDAY)
    for(w <- week) println(w)

    /*
    Write a function minmax(values: Array[Int]) that returns a pair containing the
    smallest and largest values in the array.
     */
    val rndvals = Seq.fill(10)(Random.nextInt(50))
    def minmax(arr : Seq[Int]): (Int,Int) = {
      (arr.min, arr.max)
    }
    println(minmax(rndvals))

  }
}
