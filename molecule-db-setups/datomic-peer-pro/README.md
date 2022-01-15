## Molecule sample project using a starter/pro Datomic Peer persisted database (protocol: dev)

Minimal project setup to persist data to disk with [Molecule](http://scalamolecule.org) and a starter/pro [Datomic Peer](https://docs.datomic.com/on-prem/peer-getting-started.html) database (protocol: dev).


### 1. Start transactor

First, you need to [start a Datomic transactor](https://docs.datomic.com/on-prem/storage.html#start-transactor) in its own process:

    cd <your-datomic-distribution (starter/pro)>
    bin/transactor config/samples/dev-transactor-template.properties

Note that although the Peer in your application code in this project setup is the pro version of Peer, the transactor can be of any type - a free or a starter/pro. As long as your application code can reach it via a matching host:port it will work. A datomic database is in other words interchangeable between free/pro.

### 2. Connect to Peer

Then connect to the database:

    implicit val conn = Datomic_Peer.connect("localhost:4334/sampledb", "dev")

Or, if we want to test a clean database each time, we could recreate the database and transact the schema on each run:

    implicit val conn = Datomic_Peer.recreateDbFrom(SampleSchema, "localhost:4334/sampledb", "dev")

In this setup we use the "dev" protocol which is intended for development databases that are persisted on local disk. See [other storage options](https://docs.datomic.com/on-prem/storage.html) for alternative storage options.


### 3. Make molecules

Having an implicit connection in scope, we can start transacting and querying `sampledb` with molecules:

    // Transact
    Person.name("John").age(27).save.eid
    
    // Query
    assert(Person.name.age.get.head == ("John", 27))


Add/change definitions in the SampleDataModel and run `sbt clean compile -Dmolecule=true` in your project root to have Molecule re-generate boilerplate code. Then you can try out using your new attributes in new molecules in `SampleApp`.

For more info, visit the [Molecule website](http://scalamolecule.org)