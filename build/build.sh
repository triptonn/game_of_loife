#!/usr/bin/env bash
set -euo pipefail
mkdir -p bin
find "$PWD/app" "$PWD/core" -type f -name "*.java" -print > sources.txt
javac -d bin '@sources.txt'