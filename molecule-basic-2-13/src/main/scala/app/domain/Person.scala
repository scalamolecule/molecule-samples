package app.domain

import molecule.DomainStructure

object Person extends DomainStructure(3) {

  trait Person {
    val name = oneString
    val age  = oneInt
  }
}