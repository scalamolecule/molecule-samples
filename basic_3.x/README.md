## Test project using molecule with Scala 3.x

Compile, generate boilerplate code in jars and run tests:

    cd basic-3.x
    sbt clean compile -Dmolecule=ture

Open in your IDE, compile and run test from there. 

(Can't run tests in sbt since sbt doesn't pick up the classpath of generated code for some reason)