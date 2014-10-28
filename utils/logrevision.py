#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import sys
import subprocess
import re
import os
# method to call git command
def git(args):
    args = ['git'] + args
    git = subprocess.Popen(args, stdout = subprocess.PIPE)
    details = git.stdout.read()
    details = details.strip()
    return details
    
def logrecord():
    curFile = os.path.realpath(__file__)
    baseDir = os.path.abspath(os.path.join(curFile, "../.."))
    revFile = os.path.abspath(os.path.join(baseDir, "src/main/resources/rev.txt"))
    rev = git(['rev-parse', 'HEAD'])
    print(rev)
    f = open(revFile,'w')
    f.write(rev)
    f.close()
logrecord()