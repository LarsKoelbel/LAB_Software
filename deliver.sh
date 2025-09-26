#!/bin/bash
# Fail immediately on errors
set -e

# Absolute path to target directory
TARGET_DIR="./delivery/"

# Remove and recreate delivery directory
rm -rf "$TARGET_DIR" || true
mkdir -p "$TARGET_DIR"

# Loop through each ./Worksheet* directory
for worksheet_dir in ./Worksheet*/; do
    # Ensure it's a directory
    [ -d "$worksheet_dir" ] || continue

    # Find the workspace directory inside it
    workspace_dir=$(find "$worksheet_dir" -maxdepth 1 -type d -iname "*workspace*" | head -n 1)

    if [ -z "$workspace_dir" ]; then
        echo "⚠️ No workspace directory found in $worksheet_dir. Skipping."
        continue
    fi

    # Extract the name from the Worksheet directory (e.g., Worksheet1)
    base_name=$(basename "$worksheet_dir")

    # Create the tar.gz archive
    tarball_name="${base_name}.tar.gz"
    tar -czvf "$tarball_name" -C "$worksheet_dir" "$(basename "$workspace_dir")"

    # Copy it into the delivery directory
    cp "$tarball_name" "$TARGET_DIR/"
    echo "✅ $tarball_name copied to $TARGET_DIR"
done
