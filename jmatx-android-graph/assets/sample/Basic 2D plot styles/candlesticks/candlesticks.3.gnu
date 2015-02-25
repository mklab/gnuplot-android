# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'candlesticks.3.png'
set boxwidth 0.2 absolute
set style fill   solid 1.00 border
set title "candlesticks with style fill solid" 
set xrange [ 0.00000 : 11.0000 ] noreverse nowriteback
set yrange [ 0.00000 : 10.0000 ] noreverse nowriteback
plot 'candlesticks.dat' using 1:3:2:6:5 with candlesticks
