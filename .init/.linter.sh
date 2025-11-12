#!/bin/bash
cd /home/kavia/workspace/code-generation/sports-scoreboard-for-android-tv-41539-41548/android_tv_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

