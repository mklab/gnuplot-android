# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'candlesticks.6.png'
set boxwidth 0.2 absolute
set title "box-and-whisker with median bar and whiskerbars" 
set xrange [ 0.00000 : 11.0000 ] noreverse nowriteback
set yrange [ 0.00000 : 10.0000 ] noreverse nowriteback
plot 'candlesticks.dat' using 1:3:2:6:5 with candlesticks lt 3 lw 2 title 'Quartiles' whiskerbars,      ''                 using 1:4:4:4:4 with candlesticks lt -1 lw 2 notitle
