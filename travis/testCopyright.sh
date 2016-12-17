#!/usr/bin/env bash
echo "Testing for missing license headers"
cmd=$(grep -Lr --include "*.java" "The MIT License (MIT)" ../*);
echo "$cmd";
if [ -z "$cmd" ]
then
    echo "[PASS] Found license headers in all files.";
else
    echo "[FAIL] Missing license headers in: "
    echo "$cmd";
fi
exit 1;