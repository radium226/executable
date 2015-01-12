#!/bin/sh
set -e
mvn --settings "settings.xml" --batch-mode release:clean
mvn --settings "settings.xml" --batch-mode release:prepare
mvn --settings "settings.xml" --batch-mode release:perform
