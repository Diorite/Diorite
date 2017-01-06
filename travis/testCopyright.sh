#!/usr/bin/env bash
echo "Testing for missing license headers..."
cmd=$(grep -Lr --include "*.java" --exclude-dir="snakeyaml" "The MIT License (MIT)" ../*);
if [ -z "$cmd" ]
then
    echo "[PASS] Found license headers in all files.";
    exit 0;
else
    echo "[FAIL] Missing license headers in: "
    echo "$cmd";
    exit 1;
fi