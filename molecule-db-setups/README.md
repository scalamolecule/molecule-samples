# Molecule database setups

To ease setting up various Molecule/Datomic configurations, 7 sample projects are provided here to get your started with the setup that best suits your combination of requirements.

For each system (except dev-local), two protocols are set up: an in-memory version and a persisting-to-disk dev/free version. 

If you're looking into using other [storage services](https://docs.datomic.com/on-prem/storage.html), the dev versions will be good starting points.

The projects are organized according to the following matrix of system combinations:

| &nbsp;                | Free       | Starter/Pro | Dev-Tools |   
| :---                  | :---       | :---        | :---      |   
| **Peer**              | mem / free | mem / dev   |           |   
| **Peer Server**       |            | mem / dev   |           |   
| **Dev Local (Cloud)** |            |             | dev-local |


Each project has 3 resources of interest:

- The `build.sbt` file that shows a minimal sbt project setup,
- A readme file describing the steps necessary to start using this setup, and
- A `SampleApp` that you can run to confirm that everything works.

Have fun!

_See [Molecule](http://scalamolecule.org) website for more info._