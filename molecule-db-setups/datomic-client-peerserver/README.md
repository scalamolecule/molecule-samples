## Molecule sample project using a starter/pro Datomic Peer Server persisted database (protocol: dev)

Minimal project setup to persist data to disk with [Molecule](http://scalamolecule.org) and a [Datomic Peer Server](https://docs.datomic.com/on-prem/peer-server.html) database (protocol: dev).

           
### 1. Start transactor

First, you need to [start a Datomic transactor](https://docs.datomic.com/on-prem/storage.html#start-transactor) in its own process:

    cd <your-datomic-distribution (starter/pro)>
    bin/transactor config/samples/dev-transactor-template.properties

Note that although the Peer in your application code in this project setup is the pro version of Peer (dev protocol), the transactor can be of any type - a free or a starter/pro (dev protocol). Your Peer Server is communicating with the transactor via network by host:port. 

### 2. Create database

Peer Servers do not own databases. As such, the Peer Server cannot create or destroy a database.

You can instead manage databases with the Peer library by using the Datomic shell (in a separate process from the transactor):

    bin/shell
    datomic % Peer.createDatabase("datomic:dev://localhost:4334/sampleDb");

Exit the datomic shell with ctrl-c or similar. 
            

### 3. Start Peer Server

A Peer Server os started from a Datomic pro distribution directory with for instance this command:

    bin/run -m datomic.peer-server -h localhost -p 8998 -a k,s -d sampleDb,datomic:dev://localhost:4334/sampleDb

In this setup we use a [database connection URI](https://docs.datomic.com/on-prem/javadoc/datomic/Peer.html#connect-java.lang.Object-) with the "dev" protocol which is intended for development databases that are persisted on local disk. See [other storage options](https://docs.datomic.com/on-prem/storage.html) for alternative storage options.

The other connection options explained:

    -m datomic.peer-server                             // the peer-server command
    -h localhost                                       // host name
    -p 8998                                            // port number
    -a k,s                                             // access-key,secret
    -d sampleDb,datomic:dev://localhost:4334/sampleDb  // dbName-alias,URI

There can be no space after comma in the pairs of options!

For simplicity, we just chose to write "k,s" for access-key,secret. The important thing is that you need to supply the same pair when you connect to the Peer Server in your code (as with host/port names).

If successful, it will show something like "Serving datomic:mem://sampleDb as sampleDb".


### 4. Connect to Peer Server

Presuming the transactor is running and the Peer Server is serving `sampleDb` we can connect to it: 

    implicit val conn = 
      Datomic_PeerServer("k", "s", "localhost:8998")
       .connect("sampleDb")

We use the same coordinates here as when we started the Peer Server.

For the purpose of testing, we want to make sure that our schema is up-to-date and therefore transact it on every run: 

    implicit val conn = 
      Datomic_PeerServer("k", "s", "localhost:8998")
        .transactSchema(SampleSchema, "sampleDb")


### 5. Make molecules

From here on, we can start transacting and querying `sampleDb` with molecules:

    // Transact
    Person.name("John").age(27).save.eid
    
    // Query
    assert(Person.name.age.get.head == ("John", 27))


Add/change definitions in the SampleDataModel and run `sbt clean compile -Dmolecule=true` in your project root to have Molecule re-generate boilerplate code. Then you can try out using your new attributes in new molecules in `SampleApp`.

For more info, visit the [Molecule website](http://scalamolecule.org)