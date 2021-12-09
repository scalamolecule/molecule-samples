## Basic Molecule project using in-mem Datomic Peer

Minimal project showing how to set up Molecule with Datomic Peer. 

```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-basic
sbt clean compile -Dmolecule=true
sbt run # Bob's age: 42
```

Define more attributes in `PersonDataModel` and run `sbt clean compile -Dmolecule=true` to re-generate boilerplate code. Try using your new attributes to add and query data.


_See [ScalaMolecule.org](http://scalamolecule.org) for more info._