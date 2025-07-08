package app.domain

import molecule.DomainStructure

object Person extends DomainStructure {
  trait Person:
    val name = oneString
    val age  = oneInt
}