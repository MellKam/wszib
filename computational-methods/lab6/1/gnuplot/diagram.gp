set terminal png enhanced font 'Arial,12'
set output 'wykres1.png'

set title "Funkcja f(x) = x^2 − 5" font 'Arial,14'
set xlabel "x" font 'Arial,12'
set ylabel "f(x)" font 'Arial,12'

set grid linestyle 1 linecolor rgb "#E0E0E0" linewidth 0.5

set xrange [-3:3]
set yrange [-6:6]

a = 1
b = 0
c = -5

plot (a * x + b) * x + c with lines lw 2 linecolor rgb "#0000FF" title "f(x)=x2−5"