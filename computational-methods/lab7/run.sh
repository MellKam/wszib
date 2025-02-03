#!/bin/bash

OUTPUT_FILE="results.csv"

echo "n,lu_decomp_time,solve_time,error_norm" > "$OUTPUT_FILE"

for n in $(seq 10 100 1000); do
    ./main "$n" >> "$OUTPUT_FILE"
done