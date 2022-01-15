## Molecule sample project using Datomic dev-local client database

Minimal project setup to test a [Cloud setup locally](https://docs.datomic.com/cloud/dev-local.html) locally against the Datomic Client api without connecting to a remote client.


### 1. Download dev-tools and setup sbt (once)

The [dev-local](https://docs.datomic.com/cloud/dev-local.html) library is part of [Cognitect dev-tools](https://cognitect.com/dev-tools) that needs to be downloaded first. After downloading, you `./install` the library in your local maven repository and let sbt know to look there too:

```scala
  .settings(
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "clojars" at "https://clojars.org/repo",
      Resolver.mavenLocal
    ),
  
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "1.1.0",
      "com.datomic" % "dev-local" % "1.0.238"
    )
  )
```


### 2. Create local client

On the first run we can create a database with the `recreateDbFrom` method that takes our schema and a database name:

```scala
implicit val conn = Datomic_DevLocal("datomic-samples").recreateDbFrom(SampleSchema, "sampleDb")
```

The parameter "datomic-samples" passed to `Datomic_DevLocal` tells the name of the folder where sample data will reside. A second parameter can override the absolute path to this directory that is saved in ~/.datomic/dev-local.edn. 

When the database has been created, we can simply connect to it:

```scala
implicit val conn = Datomic_DevLocal("datomic-samples").connect("sampleDb")
```

### 3. Make molecules

Having an implicit connection in scope, we can start transacting and querying `sampleDb` with molecules:

```scala
for {
  // Save data
  _ <- Person.name("Bob").age(42).save

  // Get data
  _ <- Person.name_("Bob").age.get.map(_.head ==> 42)
} yield ()
```

Add/change definitions in the SampleDataModel and run `sbt clean compile -Dmolecule=true` in your project root to have Molecule re-generate boilerplate code. Then you can try out using your new attributes in new molecules in `SampleApp`.

For more info, visit the [Molecule website](http://scalamolecule.org)