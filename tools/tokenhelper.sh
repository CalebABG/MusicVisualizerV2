#!/bin/bash

if [[ ! -d "$MVSCTOKEN" ]]; then 
    export MVSCTOKEN=$1
    echo "export MVSCTOKEN='$MVSCTOKEN'" >> ~/.bashrc
    echo "Success: Set Music-VisualizerV2 Environment Variable!"
fi