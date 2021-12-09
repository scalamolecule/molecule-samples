## Molecule sample projects

This repo contains 3 sample projects with minimal setups to use [Molecule](http://scalamolecule.org).


### molecule-basic

Basic jvm project. 
```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-basic
sbt clean compile -Dmolecule=true
sbt run # prints "Bob's age: 42" in console
```


### molecule-rpc-api

Minimal Play app making RPC calls from the client to a shared api with server implementation to demonstrate traditional "manual RPC" calls.
```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-rpc-transparent
sbt clean compile -Dmolecule=true
sbt run
```
Open [http://localhost:9000](http://localhost:9000)


### molecule-rpc-transparent

Minimal Play app making molecule calls from the client to demonstrate transparent RPC calls.
```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-rpc-transparent
sbt clean compile -Dmolecule=true
sbt run
```
Open [http://localhost:9000](http://localhost:9000)



_See [Molecule](http://scalamolecule.org) website for more info._
