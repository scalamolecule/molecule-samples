#!/bin/bash

# All abort with ctrl-c
trap "exit" INT

echo "##########################################################################"
echo "molecule-basic"
echo "##########################################################################"

cd molecule-basic
if [ -d "lib"  ]; then
    rm -r lib
fi
if [ -d "target"  ]; then
    rm -r target
fi

# Create jars with Molecule boilerplate code
sbt compile -Dmolecule=true

# Test with boilerplate code in jars
sbt test

cd ..

