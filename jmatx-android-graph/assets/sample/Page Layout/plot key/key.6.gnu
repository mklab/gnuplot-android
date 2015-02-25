# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'key.6.png'
set key 0, 0, 0 right bottom horizontal Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set arrow 1 from -3, 0, 0 to 3, 0, 0 nohead back nofilled linetype -1 linewidth 1.000
set arrow 2 from 0, -3, 0 to 0, 3, 0 nohead back nofilled linetype -1 linewidth 1.000
set title "Key (<manual> horiz bot right)" 
set xrange [ -15.0000 : 5.00000 ] noreverse nowriteback
plot x,-x
