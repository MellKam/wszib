set terminal png enhanced size 800,600
set output 'test2.png'

set title "f(x) = e^(-x) - sin(x) + sqrt(x)"
set xlabel "x"
set ylabel "f(x)"
set grid

# Rysowanie wykresu funkcji z pliku plot_data.txt
plot 'plot_data.txt' using 1:2 with lines title "f(x)", \
     4.7792612, exp(-4.7792612) - sin(4.7792612) + sqrt(4.7792612) with points pt 7 title "Minimum"