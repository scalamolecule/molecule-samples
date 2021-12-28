# Molecule sample projects

This repo contains 3 sample Play projects with minimal setups to demonstrate how to use [molecules](http://scalamolecule.org) to fetch data from the [Datomic](https://www.datomic.com) database. Examples of Akka-Http and Http4s setups is shown below too.

(The commands shown for each sample can be copied as a whole and pasted into a terminal window to get each sample up and running.)

## 1. molecule-basic

Basic Play project on jvm only. 
```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-basic
sbt clean compile -Dmolecule=true
sbt run # prints "Bob's age: 42" in console
```


## 2. molecule-rpc-api (Play)

Minimal Play/Scala.js app making [RPC](https://en.wikipedia.org/wiki/Remote_procedure_call) calls from the client to a shared api with server implementation to demonstrate traditional "manual RPC" calls.
```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-rpc-api
sbt clean compile -Dmolecule=true
sbt run
```
Open [http://localhost:9000](http://localhost:9000) - will show "Bob's age: 42" in the browser.

See implementation variations for Akka-Http and Http4s in the AppController file comments.


## 3. molecule-rpc-transparent (Play)

Minimal Play/Scala.js app making molecule calls from the client to demonstrate transparent RPC calls where no shared api and server implementation is needed.
```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-rpc-transparent
sbt clean compile -Dmolecule=true
sbt run
```
Open [http://localhost:9000](http://localhost:9000) - will show "Bob's age: 42" in the browser.


## Router definition and response

Molecule uses the type safe [Sloth](https://github.com/cornerman/sloth) RPC library and [Boopickle](https://boopickle.suzaku.io) for fast binary serialization (aka pickling/marshalling) between client and server.

Use your own RPC api by defining a custom Sloth route:

```scala
val router = Router[ByteBuffer, Future].route[PersonApi](PersonImpl)
```

Use Molecule's generic `MoleculeRpc` api and `DatomicRpc()` implementation for transparent RPC. This allows you to simply use a molecule on the client side and have Molecule transparently marshall data back and forth:

```scala
val router = Router[ByteBuffer, Future].route[MoleculeRpc](DatomicRpc())
```

You can even add both generic and custom apis in multiple routes to be free to mix manual and transparent RPC calls:

```scala
val router = Router[ByteBuffer, Future]
  .route[MoleculeRpc](DatomicRpc())
  .route[PersonApi](PersonImpl)
  // more api's...
```
                                                               
The `router` definition together with an api method name string and argument data (like query string and arguments or transaction data) is used to create a response with data that is encoded as binary data and sent back to the client by using Molecule's `moleculeRpcResponse` method:
```scala
val response: Future[Array[Byte]] = 
  moleculeRpcResponse(router, pathStr, argsData.asByteBuffer)
```

`AppController` in the two Play sample projects show how the router definition and response is set up. Let's see too how this can look with Akka-Http and Http4s:


### RPC with Akka-Http

When setting up the Http server in Akka-Http, a `post` Directive can be setup like this:
```scala
path("ajax" / "MoleculeRpc" / Remaining) { method =>
  val pathStr = s"MoleculeRpc/$method"
  post {
    extractRequest { req =>
      req.entity match {
        case HttpEntity.Strict(_, argsData) =>
          complete(moleculeRpcResponse(router, pathStr, argsData.asByteBuffer))

        // When args data is big (think lots of transaction data), the Default 
        // HttpEntity is used and we reduce the chunks to one response.
        case HttpEntity.Default(_, _, chunks) =>
          complete(
            chunks.reduce(_ ++ _)
              .runFoldAsync(Array.empty[Byte]) {
                case (_, argsData) => 
                  moleculeRpcResponse(router, pathStr, argsData.asByteBuffer)
              }
          )
      }
    }
  }
}
```
See [an example](https://github.com/scalamolecule/molecule/blob/master/moleculeTests/jvm/src/main/scala/moleculeTests/MoleculeRpcServer.scala) of an Akka-Http server setup in MoleculeTests.


### RPC with Http4s

In Http4s we can set up a POST route match to handle our molecule RPC calls:
```scala
def routes: ReaderT[IO, Request[IO], Response[IO]] = HttpRoutes.of[IO] {
  case ajaxRequest@POST -> Root / "ajax" / "MoleculeRpc" / method =>
    Ok(ajaxRequest.as[Array[Byte]].flatMap(argsData =>
      IO.fromFuture(IO(
        moleculeRpcResponse(router, s"MoleculeRpc/$method", ByteBuffer.wrap(argsData))
      ))
    ))
}
```

_Go to the [Molecule](http://scalamolecule.org) website for more info._
