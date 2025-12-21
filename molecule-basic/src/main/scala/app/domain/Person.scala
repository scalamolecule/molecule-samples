package app.domain

import molecule.DomainStructure

trait Person extends DomainStructure {
  trait Person:
    val name = oneString
    val age  = oneInt
}