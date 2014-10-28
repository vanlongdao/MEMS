1. To Enable JREBEL

* open your jrebel.properties, add a new line with below content:

<code> mdms.root=PATH_TO_SOURCE_CODE_DIR </code>


3. About CSS

There are 2 files:

- main.css: for all user-defined classes
- customize.css: for override pre-defined classes of Primefaces.

NEVER put styles directly in style attribute, HAVE TO create a class

4. How to test with Arquillian

- Edit arquillian.xml file

Find jboss_local section

+ change <code>jbossHome</code> to your path

- Add user to login Jboss Console

+ Open <JbossHome>/bin folder, open terminal
+ type ./addUser.sh
+ create new user with username=wildfly, password=wildfly
+ DONE. you can access Jboss console at localhost:9990

5. Jboss log configuration

- refer to: <a href="http://blog.arrow-tech.vn/2014/05/26/hibernate-logging-with-wildfly/">Our Blog</a>

6. Working on Local

To create local database, use create_db.sql script

and to drop all tables, use drop_db.sql script


7. Setting include/exclude setting files before commit.

Currently, we put most of settings: code clean up, formatter, ... into Project Settings, so any changes must be confirm first.

To avoid accidently commit changes on these files, I added 2 bash files: excludeSettingFiles.sh and includeSettingFiles.sh

by default, you will not need to commit setting files, so run excludeSettingFiles.sh from Project folder

Only when you need to commit Setting files, run includeSettingFiles.sh from Project folder, then commit them.
after that, run excludeSettingFiles.sh again.


8. Code review

- First setup RBTools on your computer:

<a href="https://www.reviewboard.org/docs/rbtools/dev/#linux"> How to setup RBTools </a>

We will follow "Post-commit" strategy in code review. Basically, you will push your code to repository first, then create review request. This approach is not really good, as your code actually pushed to repository already before the review request is approved. But because we're using feature branches, so it's acceptable. In additional, without push changes to repository first, we cannot update a Review request. => This strategy is good enough.

To start, we need to do configuration on gitlab server, so on local you don't need to do anything.

Below will be guide for setup on gitlab server only:

on gitlab server (192.168.1.3), go to the repository's directory: /home/git/repositories/<groupname>/<projectname>.git

setup some configuration:

git config reviewboard.url http://192.168.1.3:5678
git config reviewboard.repo <repo name on reviewboard>
git config reviewboard.reviewgroup <group name on reviewboard>


move to hook folder: cd hook

create a post-receive hook file: nano post-receive
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
#post-receive

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
	
# 1. Read STDIN (Format: "from_commit to_commit branch_name")
(old, new, branch) = sys.stdin.read().split()

RB_REPO=git(["config", "reviewboard.repo"])

RB_GROUP=git(["config", "reviewboard.reviewgroup"])

# Have to get from config
# git(["config", "reviewboard.url", "http://192.168.1.3:5678"])
# print(RB_GROUP)

MAP_USERS = {
"ducva@arrow-tech.vn":"ducva",
"taind@arrow-tech.vn":"taind",
"cuongpd@arrow-tech.vn":"cuongp",
"longdv@arrow-tech.vn":"longdv",
"haind@arrow-tech.vn":"haind"
}

# print(MAP_USERS)

# get author email
# have to get base on branch
email=git(["log", "-1", "--pretty=%ce", branch])
author=MAP_USERS[email]
comment=git(["log", "-1", "--pretty=%B",branch])

#command to post review
command=["/usr/local/bin/rbt","post","--username=dummy", "--password=dummy", "--submit-as=" + author, "--repository-type=git", "--publish", "--repository=" + RB_REPO, "--target-groups=" + RB_GROUP]

# Bypass code review
match=re.search(r"@BYPASS@", comment)
if match:
	exit()

#find if found RR=43
match=re.search(r"@ReviewRequest=(\d+)",comment)
if match:
	rrid=match.group(1)
	command.append("--review-request-id=" + rrid)

#find target user
match=re.search(r"@TargetPeople=(\w+)", comment)
if match:
	person=match.group(1)
	command.append("--target-people=" + person)
	
command.extend([old, new])

p=subprocess.Popen(command)


* save file,
* change own to git: chowm git:git post-receive
* allow to execute: chmod a+x post-receive

* To update a Review Request, in your comment, add #ReviewRequest=ABC
* To specify person who will review, add #TargetPeople=<username on reviewboard>
* to bypass review process, add #BYPASS# in your comment


9. Hibernate Validator with JRebel

Currently, enable Hibernate Validator plugin of JRebel causes an issue, which make all Constraints in CDI Beans will be ignored.
So, we have to disable this plugin

# Generate Java Source from schema
1. Allow execute utils: chmod -R a+x utils

2. Generate Java source from schema: ./utils/generateJavaSrc.py

3. Generate SQL from Schema: ./utils/generateSQL.py

4. Generate Labels and Messages

* all labels and messages will be update to https://docs.google.com/a/arrow-tech.vn/spreadsheets/d/1evcFbYGM9UcIOKMxofE9qa90rRHmVQWQgtQRwSDDQ_Q/edit#gid=0

* before generate, download it, and save to 02_LabelsMessages.xlsx file

* run command: ./utils/generateLabels.py


10. Setup Max Post Size of Wildfly

* Connect to server through console : $WILDFLY_HOME/bin/jboss-cli.sh
* enter "connect", Enter
* enter "/subsystem=undertow/server=default-server/http-listener=default:write-attribute(name=max-post-size,value=204800000)", Enter => set max upload file for http protocal to 200 MB
* enter "/subsystem=undertow/server=default-server/https-listener=test:write-attribute(name=max-post-size,value=20480000)", Enter => set max upload file for https protocol to 200 MB


11. Enable UTF8 for Servlets in Wildfly

* connect through console
* enter "/subsystem=undertow/servlet-container=default:write-attribute(name=use-listener-encoding, value=true)"
* enter "/subsystem=undertow/servlet-container=default:write-attribute(name=default-encoding,value==UTF8)"