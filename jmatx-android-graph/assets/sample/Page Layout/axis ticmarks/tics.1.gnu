# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'tics.1.png'
set mxtics
set title "Default tics settings" 
set xlabel "x" 
set xrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set ylabel "y" 
set yrange [ -0.250000 : 1.00000 ] noreverse nowriteback
plot sin(sqrt(x**2))/sqrt(x**2) notitle
