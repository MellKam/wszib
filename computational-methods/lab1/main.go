package main

import (
	"encoding/csv"
	"fmt"
	"os"

	"golang.org/x/exp/constraints"
)

func sequence1[T constraints.Float](x T, n int, filename string) {
	file, err := os.Create(filename)
	if err != nil {
		fmt.Println("Error creating file:", err)
		return
	}
	defer file.Close()

	writer := csv.NewWriter(file)
	defer writer.Flush()

	fmt.Printf("%T sequence 1:\n", x)
	for i := 0; i < n; i++ {
		fmt.Printf("x[%d] = %.15f\n", i, x)
		writer.Write([]string{fmt.Sprintf("%d", i), fmt.Sprintf("%.15f", x)})
		x = x + 3.0*x*(1.0-x)
	}
}

func sequence2[T constraints.Float](x T, n int, filename string) {
	file, err := os.Create(filename)
	if err != nil {
		fmt.Println("Error creating file:", err)
		return
	}
	defer file.Close()

	writer := csv.NewWriter(file)
	defer writer.Flush()

	fmt.Printf("%T sequence 2:\n", x)
	for i := 0; i < n; i++ {
		fmt.Printf("x[%d] = %.15f\n", i, x)
		writer.Write([]string{fmt.Sprintf("%d", i), fmt.Sprintf("%.15f", x)})
		x = 4.0*x - 3.0*x*x
	}
}

func epsilon[T constraints.Float]() T {
	var eps T = 1.0
	for (1.0 + eps) != 1.0 {
		eps /= 2.0
	}
	return eps * 2.0
}

func main() {
	n := 100

	sequence1(float32(0.01), n, "sequence1_float32.csv")
	fmt.Println()
	sequence1(float64(0.01), n, "sequence1_float64.csv")
	fmt.Println()

	sequence2(float32(0.01), n, "sequence2_float32.csv")
	fmt.Println()
	sequence2(float64(0.01), n, "sequence2_float64.csv")
	fmt.Println()

	fmt.Printf("Machine epsilon for float32: %g\n", epsilon[float32]())
	fmt.Printf("Machine epsilon for float64: %g\n", epsilon[float64]())
}
