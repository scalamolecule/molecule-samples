package db.dataModel

import molecule.core.data.model._

@InOut(0, 2)
object PersonDataModel {

  trait Person {
    val name = oneString
    val age  = oneInt
  }
}