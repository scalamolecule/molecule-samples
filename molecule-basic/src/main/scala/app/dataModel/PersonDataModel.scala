package app.dataModel

import molecule.core.data.model._


@InOut(0, 6)
object PersonDataModel {

  trait Person {
    val name   = oneString
    val age    = oneInt
  }
}