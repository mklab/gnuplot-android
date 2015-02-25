# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillbetween.2.png'
set style fill   pattern 2 border
set style data lines
set title "Fill area between two curves (pattern fill)" 
set xrange [ 10.0000 : * ] noreverse nowriteback  # (currently [:10.0000] )
set yrange [ 0.00000 : 175.000 ] noreverse nowriteback
plot 'silver.dat' u 1:2:3 w filledcu,       '' u 1:2 lt -1 notitle, '' u 1:3 lt -1 notitle
