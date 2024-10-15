# Sprawozdanie

Laboratorium 1, Arytmetyka komputerowa, Artem Melnyk, 2024-10-15

[Link do repozytorium z kodem](https://github.com/MellKam/wszib/tree/main/computational-methods/lab1/)

## Co było do zrobienia

1. Napisac program liczacy kolejne wyrazy ciagu:

`x{n+1}= x{n} + 3.0 _ x{n}_ (1 - x{n})`

startujac z punktu x{0} = 0.01. Wykonac to zadanie dla roznych reprezentacji liczb (float, double). Dlaczego wyniki sie rozbiegaja?

2. Napisac program liczacy ciag z wczesniejszego zadania, ale wg wzoru

`x{n+1} = 4.0 _ x{n} - 3.0 _ x{n} \* x{n}`

porownac z wynikami z wczesniejszego zadania.

3. Znalezc "maszynowe epsilon", czyli najmniejsza liczbe a, taka ze a+1>1

## Moje podejście do rozwiązania problemu

Wykorzystałem język Golang, aby napisać program do obliczeń. Program obliczał wartości ciągu dla różnych reprezentacji liczb (float32, float64) oraz porównywał wyniki. Następnie obliczał wartości ciągu dla drugiego wzoru. Na końcu program obliczał "maszynowe epsilon" dla obu reprezentacji liczb.

Kod programu znajduje się w pliku `main.go`.

```go
func sequence1[T constraints.Float](x T, n int) {
	fmt.Printf("%T sequence 1:\n", x)
	for i := 0; i < n; i++ {
		fmt.Printf("x[%d] = %.15f\n", i, x)
		x = x + 3.0 * x * (1.0 - x)
	}
}

func sequence2[T constraints.Float](x T, n int) {
	fmt.Printf("%T sequence 2:\n", x)
	for i := 0; i < n; i++ {
		fmt.Printf("x[%d] = %.15f\n", i, x)
		x = 4.0 * x - 3.0 * x * x
	}
}

func epsilon[T constraints.Float]() T {
	var eps T = 1.0
	for (1.0 + eps) != 1.0 {
		eps /= 2.0
	}
	return eps * 2.0
}
```

## Wyniki

```
float32 sequence 1:
x[0] = 0.009999999776
x[1] = 0.039700001478
x[2] = 0.154071733356
x[3] = 0.545072615147
x[4] = 1.288978099823
x[5] = 0.171518802643
x[6] = 0.597819089890
x[7] = 1.319113373756
x[8] = 0.056273221970
x[9] = 0.215592861176

float64 sequence 1:
x[0] = 0.010000000000
x[1] = 0.039700000000
x[2] = 0.154071730000
x[3] = 0.545072626044
x[4] = 1.288978001189
x[5] = 0.171519142109
x[6] = 0.597820120107
x[7] = 1.319113792414
x[8] = 0.056271577646
x[9] = 0.215586839233

float32 sequence 2:
x[0] = 0.009999999776
x[1] = 0.039699997753
x[2] = 0.154071718454
x[3] = 0.545072615147
x[4] = 1.288977980614
x[5] = 0.171519279480
x[6] = 0.597820520401
x[7] = 1.319113969803
x[8] = 0.056271076202
x[9] = 0.215585008264

float64 sequence 2:
x[0] = 0.010000000000
x[1] = 0.039700000000
x[2] = 0.154071730000
x[3] = 0.545072626044
x[4] = 1.288978001189
x[5] = 0.171519142109
x[6] = 0.597820120107
x[7] = 1.319113792414
x[8] = 0.056271577646
x[9] = 0.215586839233

Machine epsilon for float32: 1.1920929e-07
Machine epsilon for float64: 2.220446049250313e-16
```

## Wnioski

Rozbieżność wyników zadania 1: W miarę postępu sekwencji, ograniczona precyzja float32 wprowadza błędy zaokrąglania, które kumulują się w czasie, prowadząc do rozbieżności między wynikami float32 i float64. float64 jest bardziej precyzyjny niż float32, więc jego wyniki pozostają dokładniejsze przez dłuższe iteracje.

Rozbieżność wyników w zadaniu 2: Podobna rozbieżność występuje w tym zadaniu z powodu utraty precyzji przez float32. Wyniki mogą się znacznie różnić, zwłaszcza po wielu iteracjach, ze względu na zaokrąglanie zmiennoprzecinkowe.

Maszynowe Epsilon: Machine epsilon jest najmniejszą wartością taką, że 1+ϵ>1. Jest inna dla float32 i float64, przy czym float32 jest mniej dokładna ze względu na mniejszy rozmiar. Wartość ta pomaga wyjaśnić, dlaczego float32 akumuluje błędy szybciej niż float64.
