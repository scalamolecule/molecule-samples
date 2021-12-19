## Minimal Molecule Scala.js project with transparent RPC

Minimal Play project showing how to set up using transparent RPC calls with molecules on the client side.

```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-rpc-transparent
sbt compile -Dmolecule=true
sbt run
```
Open [http://localhost:9000](http://localhost:9000)

Define more attributes in `PersonDataModel` and run `sbt clean compile -Dmolecule=true` to re-generate boilerplate code. Try using your new attributes to add and query data and refresh the browser to see the changes.


_See [ScalaMolecule.org](http://scalamolecule.org) for more info._