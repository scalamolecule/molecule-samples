## Molecule sample project using a starter/pro Datomic Peer Server in-mem database

Minimal project setup to test using [Molecule](http://scalamolecule.org) with a [Datomic Peer Server](https://docs.datomic.com/on-prem/peer-server.html) in-memory database (protocol: mem).

### 1. Start Peer Server

First, you need to start the Peer Server with an automatically created in-memory database:

    cd <your-datomic-distribution (starter/pro)>
    bin/run -m datomic.peer-server -h localhost -p 8998 -a k,s -d sampleDb,datomic:mem://sampleDb

In this in-mem setup we use a [database connection URI](https://docs.datomic.com/on-prem/javadoc/datomic/Peer.html#connect-java.lang.Object-) with the "mem" protocol which is intended for in-memory databases. 

The other connection options explained:

    -m datomic.peer-server              // the peer-server command
    -h localhost                        // host name
    -p 8998                             // port number
    -a k,s                              // access-key,secret
    -d sampleDb,datomic:mem://sampleDb  // dbName-alias,URI

There can be no space after comma in the pairs of options!

For simplicity, we just chose to write "k,s" for access-key,secret. The important thing is that you need to supply the same pair when you connect to the Peer Server in your code (as with host/port names).

If successful, it will show something like "Serving datomic:mem://sampleDb as sampleDb".

If you need persisting data, please see [other storage options](https://docs.datomic.com/on-prem/storage.html) or have a look at the persisted-peerserver sample project.


### 2. Connect to Peer Server

Presuming the Peer Server is running and serving `sampleDb` we can connect to it:

    implicit val futConn = Datomic_PeerServer("k", "s", "localhost:8998")
      .connect(PersonSchema, "sampleDb")

We use the same coordinates (k, s, host, port) here as when we started the Peer Server.

In order to make molecules, the schema needs to be transacted from the connection:

    for {
      conn <- futConn
      _ <- conn.transact(PersonSchema.datomicClient.head)
      ...

### 3. Make molecules

From here on, we can start transacting and querying `sampleDb` with molecules:

    // Transact
    Person.name("John").age(27).save.eid
    
    // Query
    assert(Person.name.age.get.head == ("John", 27))


Add/change definitions in `SampleDataModel` and run `sbt clean compile -Dmolecule=true` in your project root to have Molecule re-generate boilerplate code. Then you can try out using your new attributes in new molecules in `SampleApp`.

For more info, visit the [Molecule website](http://scalamolecule.org)
