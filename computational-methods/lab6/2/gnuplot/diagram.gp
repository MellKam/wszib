# Terminal settings
set terminal png size 800,600 enhanced font 'Arial,12'
set output 'wykres1.png'

# Plot settings
set title "Funkcja f(x) = x^2 - 2x + 1" font 'Arial,14'
set xlabel "x" font 'Arial,12'
set ylabel "f(x)" font 'Arial,12'

# Enhanced grid
set grid linecolor rgb "#E0E0E0" lw 0.5

# Plot range
set xrange [-2:4]
set yrange [-1:8]

# Plot data with improved styling
plot "quadratic.dat" using 1:2 with lines \
    linecolor rgb "#0066CC" \
    linewidth 2 \
    title "f(x) = x^2 - 2x + 1"

# Alternative direct function plot:
# plot x**2 - 2*x + 1 linecolor rgb "#0066CC" lw 2 title "f(x) = x^2 - 2x + 1"