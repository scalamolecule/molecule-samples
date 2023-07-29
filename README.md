# Molecule sample projects

This repo contains sample molecule projects to get you started with [molecule](https://github.com/scalamolecule/molecule), the library to write Scala code to databases.

For now there's only a single sample project that targets Scala 3 or 2.13/2.12. The code is the same but the setup differs slightly. The Scala 3.x version generates source code while the 2.x generates code and packs it in jars placed in the lib folder. 

There seems to be some binary issue with generating jars for Scala 3.3 that needs to be solved. In the meanwhile you can just generate source code as is done in the basic-3.x project. Only difference is a single flag in the build file and whether you can modify the generated code (which you don't need to anyway).


### basic-3.x

    cd basic-3.x
    sbt clean compile -Dmolecule=ture

Open in your IDE, compile and run test from there.


### basic-2.x

    cd basic-2.x
    sbt clean compile -Dmolecule=ture
    sbt test


### Speed

For better compilation performance (in general):

- Use java 20 as your default java version and for your Scala projects in your IDE.
- Give sbt lots of memory.

In your ~/.bashrc file (on mac) or similar you can for instance set the following options (adjust to your system):

    export JAVA_HOME=`/usr/libexec/java_home -v 20.0.1`
    export SBT_OPTS='-Xmx50G -Xms48G -Xss8M'
