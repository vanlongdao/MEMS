#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import sys
import subprocess
import os
import BlueArrow

from BlueArrow import bluearrow
def syncDb():
    #root dir
    curFile = os.path.realpath(__file__)

    baseDir = os.path.abspath(os.path.join(curFile, "../.."))

    # Schema dir
    SCHEMA_DIR=os.path.abspath(os.path.join(baseDir, "schema"))

    #outputDir
    OUTPUT_DIR = baseDir

    #DEFAULT PACKAGE
    DATASOURCE_FILE = os.path.abspath(os.path.join(baseDir, "webapp/WEB-INF/production-ds.xml"))

    command=["--syncDb", "--schemaDir=" + SCHEMA_DIR, "--outputDir=" + OUTPUT_DIR, "--dbConfig=" + DATASOURCE_FILE, "--removeRemovedStuffs"]
    print(command)
    return bluearrow(command)
    
syncDb()
