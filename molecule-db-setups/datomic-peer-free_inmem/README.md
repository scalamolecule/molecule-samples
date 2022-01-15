## Molecule sample project using a free Datomic Peer in-mem database (protocol: mem)

Minimal project setup to test using [Molecule](http://scalamolecule.org) with a free [Datomic Peer](https://docs.datomic.com/on-prem/peer-getting-started.html) in-memory database (protocol: mem).


### 1. Connect to Peer

Connect, recreate in-memory database and get database connection

    implicit val conn = Datomic_Peer.recreateDbFrom(SampleSchema) 

Since we are not persisting the database, we let Molecule create a random database name. We don't need to supply the default "mem" protocol either. So this is really simple.

If you need persisting data, please see [other storage options](https://docs.datomic.com/on-prem/storage.html) or have a look at one of the other sample projects.


### 2. Make molecules

Having an implicit connection in scope, we can start transacting and querying `sampledb` with molecules:

    // Transact
    Person.name("John").age(27).save.eid
    
    // Query
    assert(Person.name.age.get.head == ("John", 27))


Add/change definitions in the SampleDataModel and run `sbt clean compile -Dmolecule=true` in your project root to have Molecule re-generate boilerplate code. Then you can try out using your new attributes in new molecules in `SampleApp`.

For more info, visit the [Molecule website](http://scalamolecule.org)