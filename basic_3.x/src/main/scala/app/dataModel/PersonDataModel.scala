package app.dataModel

import molecule.DataModel

object PersonDataModel extends DataModel(3) {

  trait Person {
    val name = oneString
    val age  = oneInt
  }
}