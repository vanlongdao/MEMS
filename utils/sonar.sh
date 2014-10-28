#!/bin/bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.skip=false  -Dmaven.test.failure.ignore=true sonar:sonar -Darquillian.profile=jboss_local
