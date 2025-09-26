#!/bin/bash
# Fail immediately on errors
set -e

# Absolute path to target directory
TARGET_DIR="./delivery/"

# Remove and recreate delivery directory
rm -rf "$TARGET_DIR" || true
mkdir -p "$TARGET_DIR"

LOGFILE="jenkins.log"

# Loop through each ./Worksheet* directory
for worksheet_dir in ./Worksheet*/; do
    # Ensure it's a directory
    [ -d "$worksheet_dir" ] || continue

    # Find the workspace directory inside it
    workspace_dir=$(find "$worksheet_dir" -maxdepth 1 -type d -iname "*workspace*" | head -n 1)

    if [ -z "$workspace_dir" ]; then
        echo "⚠️ No workspace directory found in $worksheet_dir. Skipping." >> "$LOGFILE"
        continue
    fi

    # Extract the name from the workspace directory
    base_name=$workspace_dir"

    # Create the tar.gz archive
    tarball_name="${base_name}.tar.gz"
    tar -czvf "$tarball_name" "$workspace_dir"

    # Copy it into the delivery directory
    mv "$tarball_name" "$TARGET_DIR/"
    mv "$LOGFILE" "$TARGET_DIR/" || true
    echo "✅ $tarball_name copied to $TARGET_DIR"
done
