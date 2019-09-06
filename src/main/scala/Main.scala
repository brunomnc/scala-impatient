import scala.util.Random
import scala.collection.immutable.TreeMap
import java.util.TimeZone.getAvailableIDs
import scala.io._
import java.util.Calendar._
import scala.beans.BeanProperty


object Main {
  def main(args: Array[String]): Unit = {
    chapter6()
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

    /*
    Write a function lteqgt(values: Array[Int], v: Int) that returns a triple containing
    the counts of values less than v, equal to v, and greater than v.
     */
    val r = Array.fill(20)(Random.nextInt(50))
    def lteqgt(values: Array[Int], v: Int): (Int, Int, Int) = {
      val lst= values.map(m => (m < v, m == v, m > v))
      (lst.count(_._1 == true), lst.count(_._2 == true), lst.count(_._3 == true))
    }
    println(lteqgt(r, 25))
  }

  //Classes
  def chapter5() = {
    /*
    Improve the Counter class in Section 5.1, “Simple Classes and Parameterless
    Methods,” on page 51 so that it doesn't turn negative at Int.MaxValue.
     */
    class Counter_ {
      private var value = 0
      def increment() { if(value == Integer.MAX_VALUE) value=0 else value+=1 }
      def current() = value
    }

    /*
    Write a class BankAccount with methods deposit and withdraw, and a read-only
    property balance
     */
    class BankAccount {
      var balance = 0.0
      def deposit(value: Double) { balance += value}
      def withdraw(value: Double) {balance -= value}
    }

    /*
    Write a class Time with read-only properties hours and minutes and a method
    before(other: Time): Boolean that checks whether this time comes before the
    other. A Time object should be constructed as new Time(hrs, min), where hrs is in
    military time format (between 0 and 23).
     */
    class Time(_hrs:Int, _min:Int){
      var hrs = if(_hrs < 0 || _hrs > 23) 0 else _hrs
      var min = if(_min < 0 || _min > 60) 0 else _min
      def before(h: Int, m: Int): Boolean = {
        if(h < hrs)
          true
        else
          if(h == hrs)
            if(m < min)
              true else false
        else
          false
      }
    }

    /*
    Make a class Student with read-write JavaBeans properties name (of type String)
    and id (of type Long). What methods are generated? (Use javap to check.) Can
    you call the JavaBeans getters and setters in Scala? Should you?
     */
    class Student(@BeanProperty name: String, @BeanProperty id:Long){}

    /*
    Make a class Car with read-only properties for manufacturer, model name,
    and model year, and a read-write property for the license plate. Supply four
    constructors. All require the manufacturer and model name. Optionally,
    model year and license plate can also be specified in the constructor. If not,
    the model year is set to -1 and the license plate to the empty string. Which
    constructor are you choosing as the primary constructor? Why?
     */
    class Car(val manufacturer: String, val model: String, val year: Int, var license : String){
      def this(manufacturer: String, model: String, year: Int){
        this(manufacturer, model, year, "")
      }

      def this(manufacturer: String, model: String, license: String){
        this(manufacturer, model, -1, license)
      }

      def this(manufacturer: String, model: String){
        this(manufacturer, model, -1, "")
      }
    }
    /*
     Consider the class
      class Employee(val name: String, var salary: Double) {
       def this() { this("John Q. Public", 0.0) }
      }
      Rewrite it to use explicit fields and a default primary constructor. Which form
      do you prefer? Why?
     */
    class Employee {
      private var name = ""
      var salary = 0.0
      def this(name: String, salary: Double) {
        this()
        this.name = "John Q"
        this.salary = 0.0
      }
    }
  }
  //Objects
  def chapter6(): Unit =
  {
    /*
    Write an object Conversions with methods inchesToCentimeters, gallonsToLiters, and
    milesToKilometers.
     */
    object Conversions{
      def inchesToCentimeters(in:Double) = in*2.54

      def gallonsToLiters(gallons: Double) = gallons*3.78

      def milesToKilometers(miles:Double) = miles*1.6
    }

    /*
    The preceding problem wasn’t very object-oriented.
    Provide a general superclass UnitConversion and define objects InchesToCentimeters, GallonsToLiters, and
    MilesToKilometers that extend it.
     */
    abstract class UnitConversion {
      def inchesToCentimeters(value: Double) : Double
      def gallonsToLiters(value: Double) : Double
      def milesToKilometers(value: Double): Double
    }

    object Converter extends UnitConversion{
      override def inchesToCentimeters(value: Double)= value*2.54
      override def gallonsToLiters(value: Double)=value*3.78
      override def milesToKilometers(value: Double)=value*1.60
    }

    /*
    Define a Point class with a companion object so that you can construct Point
    instances as Point(3, 4), without using new.
     */
    class Point(val x: Int,val y: Int) {}
    object Point {
      def apply(x: Int, y: Int): Point = new Point(x, y)
    }
    /*
    Write an enumeration describing the four playing card suits so that the toString
    method returns ♣, ♦, ♥, or ♠.
     */
    object Cards extends Enumeration {
      val hearts = "♥"
      val tiles = "♦"
      val clovers = "♣"
      val pikes = "♠"
    }
  }
}
