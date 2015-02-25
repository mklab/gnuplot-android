# set terminal png transparent nocrop enhanced font arial 8 size 660,320 
# set output 'finance.2.png'
set ytics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 
set ytics   (80.0000, 85.0000, 90.0000, 95.0000, 100.000, 105.000)
set title "Demo of plotting financial data" 
set xrange [ 50.0000 : 253.000 ] noreverse nowriteback
set yrange [ 75.0000 : 105.000 ] noreverse nowriteback
set lmargin  9
set rmargin  2
plot 'finance.dat' using 0:5 notitle with lines
