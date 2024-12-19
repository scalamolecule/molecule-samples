#!/bin/bash

# All abort with ctrl-c
trap "exit" INT

echo "Compiling and testing each molecule-sample project"
echo "Abort with ctrl-c"

sh molecule-basic-2-12/test.sh
sh molecule-basic-2-13/test.sh
sh molecule-basic-3/test.sh

echo "======================================================"
echo "DONE testing all MoleculePlugin projects"
echo "======================================================"
