#!/usr/bin/env bash
echo "Profile: $PROFILE"
java -Dspring.profiles.active=$PROFILE -jar tracing.jar