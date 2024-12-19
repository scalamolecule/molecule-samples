# Molecule sample projects

This repo contains sample projects to get you started with [molecule](https://github.com/scalamolecule/molecule).

## Quick test

Run tests in all 3 sample projects with `./test-all.sh`

Run `test.sh` in each sample project to test individually.

## Sbt

To compile and test with `sbt`, use the following commands for each project:

### Basic Scala 3.3

    cd molecule-basic-3
    sbt clean compile -Dmolecule=true
    sbt test

### Basic Scala 2.13

    cd molecule-basic-2-13
    sbt clean compile -Dmolecule=true
    sbt test

### Basic Scala 2.12

    cd molecule-basic-2-12
    sbt clean compile -Dmolecule=true
    sbt test

