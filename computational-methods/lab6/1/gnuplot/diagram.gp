# Wybór terminala wyjściowego
set terminal png enhanced font 'Arial,12'
set output 'wykres1.png'

# Ustawienia tytułu i etykiet osi z lepszym formatowaniem
set title "Funkcja f(x) = x^2 - 5" font 'Arial,14'
set xlabel "x" font 'Arial,12'
set ylabel "f(x)" font 'Arial,12'

# Włączenie siatki na wykresie z delikatniejszym wyglądem
set grid linestyle 1 linecolor rgb "#E0E0E0" linewidth 0.5

# Ustawienia zakresu osi x i y
set xrange [-1:3]
set yrange [-6:6]

# Rysowanie wykresu funkcji f(x) = x^2 - 5 w kolorze niebieskim
plot x**2 - 5 with lines lw 2 linecolor rgb "#0000FF" title "f(x) = x^2 - 5"