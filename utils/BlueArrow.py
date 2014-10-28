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
    
def bluearrow(args):
    curFile = os.path.realpath(__file__)
    baseDir = os.path.abspath(os.path.join(curFile, ".."))
    BLUEARROW_JAR_PATH = os.path.abspath(os.path.join(baseDir, "BlueArrow.jar"))
    print (BLUEARROW_JAR_PATH)
    if(os.path.isfile(BLUEARROW_JAR_PATH) == False):
        BLUEARROW_JAR_PATH = readconfig("bluearrow.path")
        if(BLUEARROW_JAR_PATH == ""):
            exit(1)
    
    JAVA_PATH = git(["config", "bluearrow.javapath"])
    if(JAVA_PATH == ""):
        JAVA_PATH = "/usr/bin/java"

    if(os.path.isfile(JAVA_PATH) == False):
        print("Cannot find java program, please setup path: git config bluearrow.javapath <path to java>")
        exit(1)
        
    args = [JAVA_PATH, "-jar", BLUEARROW_JAR_PATH] + args
    bluearrow = subprocess.Popen(args, stdout = subprocess.PIPE)
    details = bluearrow.stdout.read()
    details = details.strip()
    return details
    
def readconfig(configname):
    result = git(["config", configname])
    if(result == ""):
        print("You have to set up " + configname + " by command: git config " + configname + " <value>")
    return result