#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import sys
import subprocess
import os
import BlueArrow

from BlueArrow import bluearrow
def generateJavaSrc():
    #root dir
    curFile = os.path.realpath(__file__)

    baseDir = os.path.abspath(os.path.join(curFile, "../.."))

    # Schema dir
    SCHEMA_DIR=os.path.abspath(os.path.join(baseDir, "schema"))

    #outputDir
    OUTPUT_DIR = baseDir

    #DEFAULT PACKAGE
    DEFAULT_PACKAGE = "arrow.mems.persistence"

    command=["--generateJavaSrc", "--schemaDir=" + SCHEMA_DIR, "--outputDir=" + OUTPUT_DIR, "--defaultPackage=" + DEFAULT_PACKAGE]
    print(command)
    return bluearrow(command)
    
generateJavaSrc()