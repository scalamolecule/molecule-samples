package app.domain

import molecule.Domain

object Person extends Domain(3) {

  trait Person {
    val name = oneString
    val age  = oneInt
  }
}