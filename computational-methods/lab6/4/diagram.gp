set terminal png size 800,600 font "Arial,12"
set output 'newton_errors.png'
set title "Error of Newton's Method"
set xlabel "Iteration"
set ylabel "Error"
plot "./newton_errors.txt" using 1:2 with lines \
    linecolor rgb "#0066CC" \
    linewidth 2 \
    title "Newton's Error"

set output 'bisection_errors.png'
set title "Error of Bisection Method"
set xlabel "Iteration"
set ylabel "Error"
plot "./bisection_errors.txt" using 1:2 with lines \
    linecolor rgb "#0066CC" \
    linewidth 2 \
    title "Bisection's Error"