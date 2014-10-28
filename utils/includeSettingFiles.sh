#!/bin/bash
git update-index --no-assume-unchanged .project
git update-index --no-assume-unchanged .classpath
git update-index --no-assume-unchanged .settings/*
git update-index --no-assume-unchanged .externalToolBuilders/*

