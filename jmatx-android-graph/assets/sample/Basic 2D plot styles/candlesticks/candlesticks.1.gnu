# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'candlesticks.1.png'
set title "candlesticks with open boxes (default)" 
set xrange [ 0.00000 : 11.0000 ] noreverse nowriteback
set yrange [ 0.00000 : 10.0000 ] noreverse nowriteback
plot 'candlesticks.dat' using 1:3:2:6:5 with candlesticks
