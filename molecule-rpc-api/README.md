## Minimal Molecule Scala.js project with manual RPC

Minimal Play project showing how to set up using manual RPC calls with molecules on the client side. A shared interface is called from the client side and implemented on the server side.

```
git clone https://github.com/scalamolecule/molecule-samples.git
cd molecule-samples/molecule-rpc-transparent
sbt clean compile -Dmolecule=true
sbt
run
```
Open [http://localhost:9000](http://localhost:9000)

In your IDE, you can
- define more attributes in the `PersonDataModel`
- `sbt compile -Dmolecule=true` your project
- create molecules with your new attributes


_See [ScalaMolecule.org](http://scalamolecule.org) for more info._