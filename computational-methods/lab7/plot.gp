set terminal png size 1000,600
set output 'times_plot.png'

set datafile separator ','

set datafile columnheaders

set title "Zależność czasu od rozmiaru układu równań"
set xlabel "Rozmiar układu równań (n)"
set ylabel "Czas (s)"
set grid

set style data linespoints
set pointsize 1.5

plot 'results.csv' using 1:2 title "Czas dekompozycji LU" with linespoints pt 7 lc rgb "blue", \
     'results.csv' using 1:3 title "Czas rozwiązywania układu" with linespoints pt 5 lc rgb "red"