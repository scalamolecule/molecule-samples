#!/bin/bash

# All abort with ctrl-c
trap "exit" INT

echo "Compiling and testing each molecule-sample project"
echo "Abort with ctrl-c"

sh molecule-basic/test.sh

echo "======================================================"
echo "DONE testing all Molecule sample projects"
echo "======================================================"
