#!/bin/bash

# All abort with ctrl-c
trap "exit" INT

echo "##########################################################################"
echo "molecule-basic-3"
echo "##########################################################################"

cd molecule-basic-3
if [ -d "lib"  ]; then
    rm -r lib
fi
if [ -d "target"  ]; then
    rm -r target
fi

# Create jars with boilerplate code and delete generated sources
sbt compile -Dmolecule=true

# Test with boilerplate code in jars
sbt test

cd ..

