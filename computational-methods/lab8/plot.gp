set terminal pngcairo font "Arial,12"
set output 'errors.png'
set datafile separator ","
set title "Integration Errors"
set xlabel "Number of Samples"
set ylabel "Error"
set logscale x
set grid
plot "results.csv" using 1:2 with linespoints lw 2 title "Plain Error", \
     "results.csv" using 1:3 with linespoints lw 2 title "Miser Error", \
     "results.csv" using 1:4 with linespoints lw 2 title "Vegas Error"
