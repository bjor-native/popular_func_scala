object Main extends App {

  /** FoldLeft
    */

  val fooList = Foo("Hugh Jass", 25, 'male) ::
    Foo("Biggus Dickus", 43, 'male) ::
    Foo("Incontinentia Buttocks", 37, 'female) ::
    Nil

  val stringList = fooList.foldLeft(List[String]()) { (z, f) =>
    val title = f.sex match {
      case 'male   => "Mr."
      case 'female => "Ms."
    }
    z :+ s"$title ${f.name}, ${f.age}"
  }
  println(stringList(1))

  /** flatten
    */

  val lol = List(List(1, 2), List(3, 4))
  val res = lol.flatten
  println(res)

  /** groupMap groupMapReduce
    */

  val fruits = List("apple", "apple", "orange", "pear", "pear", "pear")

  println(fruits.groupMap(identity)(identity))

  case class Pet(species: String, name: String, age: Int)

  val pets = List(
    Pet("cat", "sassy", 2),
    Pet("cat", "bella", 3),
    Pet("dog", "poppy", 3),
    Pet("dog", "bodie", 4),
    Pet("dog", "poppy", 2),
    Pet("bird", "coco", 2),
    Pet("bird", "kiwi", 1)
  )

  println(pets.groupMapReduce(_.species)(_ => 1)(_ + _))

  import java.time.LocalDate
  case class Product(
      id: String,
      saleDate: LocalDate,
      listPrice: Double,
      discPrice: Double
  )

  val products = List(
    Product("p001", LocalDate.of(2019, 9, 11), 10, 8.5),
    Product("p002", LocalDate.of(2019, 9, 18), 12, 10),
    Product("p003", LocalDate.of(2019, 9, 27), 10, 9),
    Product("p004", LocalDate.of(2019, 10, 6), 15, 12.5),
    Product("p005", LocalDate.of(2019, 10, 20), 12, 8),
    Product("p006", LocalDate.of(2019, 11, 8), 15, 12),
    Product("p007", LocalDate.of(2019, 11, 16), 10, 8.5),
    Product("p008", LocalDate.of(2019, 11, 25), 10, 9)
  )

  val r = products.groupMapReduce(_.saleDate.getMonth)(p =>
    (p.listPrice, p.discPrice)
  )((total, prc) => (total._1 + prc._1, total._2 + prc._2))

  println(r)

}

class Foo(val name: String, val age: Int, val sex: Symbol)

object Foo {
  def apply(name: String, age: Int, sex: Symbol) = new Foo(name, age, sex)
}
