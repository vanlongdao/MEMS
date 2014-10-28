#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import sys
import subprocess
import os
import BlueArrow

from BlueArrow import bluearrow
def generateLabels():
    #root dir
    curFile = os.path.realpath(__file__)

    baseDir = os.path.abspath(os.path.join(curFile, "../.."))

    # Schema dir
    OUTPUT_DIR=baseDir
    
    #File path
    LABEL_FILE_NAME="02_Label & Messages.xlsx"

    command=["--generateLabel", "--filePath=" + OUTPUT_DIR + "/" + LABEL_FILE_NAME, "--outputDir=" + OUTPUT_DIR, "--projectName=mems"]
    print(command)
    return bluearrow(command)

generateLabels()