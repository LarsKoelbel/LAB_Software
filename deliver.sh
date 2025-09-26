#!/bin/bash
# Fail immediately on errors
set -e

# Example: zip specific files or directories inside the cloned repo
# Replace with your actual files/folders

tar -czvf archive.tar.gz ./*

# Absolute path to target directory
TARGET_DIR="/var/lib/jenkins/hochschule(lab-software/"

mkdir -p $TARGET_DIR

# Copy the zip file
cp "$WORKSPACE/archive.tar.gz" "$TARGET_DIR/"

echo "✅ output.zip copied to $TARGET_DIR"
