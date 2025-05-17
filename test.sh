#!/bin/bash

# Exit immediately on any error
set -e
trap "exit" INT # All abort with ctrl-c

echo "Compiling and testing each molecule-sample project"
echo "Abort with ctrl-c"

projects=(
  "molecule-basic"
)

for project in "${projects[@]}"; do
  echo "##########################################################################"
  echo "$project"
  echo "##########################################################################"

  (
    cd "$project"
    sbt "clean; moleculeGen; test"
  )
done

echo "======================================================"
echo "✅ DONE testing all Molecule sample projects"
echo "======================================================"
