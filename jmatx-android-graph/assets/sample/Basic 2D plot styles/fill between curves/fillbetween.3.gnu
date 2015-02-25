# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillbetween.3.png'
set style fill   solid 1.00 noborder
set style data lines
set title "Fill area between two curves (above/below)" 
set xrange [ 250.000 : 500.000 ] noreverse nowriteback
set yrange [ * : * ] noreverse nowriteback  # (currently [0.00000:175.000] )
plot 'silver.dat' u 1:2:($3+$1/50.) w filledcurves above title 'Above',                '' u 1:2:($3+$1/50.) w filledcurves below title 'Below', 	       '' u 1:2 lt -1 lw 2 title 'curve 1', 	       '' u 1:($3+$1/50.) lt 3 lw 2 title 'curve 2'
