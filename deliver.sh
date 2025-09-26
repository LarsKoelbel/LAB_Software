#!/bin/bash
# Fail immediately on errors
set -e

# Example: zip specific files or directories inside the cloned repo
# Replace with your actual files/folders

tar -czvf archive.tar.gz ./Worksheet*

# Absolute path to target directory
TARGET_DIR="./delivery/"

rm -rf $TARGET_DIR || true

mkdir -p $TARGET_DIR

# Copy the zip file
cp "$WORKSPACE/archive.tar.gz" "$TARGET_DIR/"

echo "✅ output.zip copied to $TARGET_DIR"
