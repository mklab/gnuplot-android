# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'using.2.png'
set key bmargin center horizontal Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "Convex     November 1-7 1989" 
set xrange [ 1.00000 : 8.00000 ] noreverse nowriteback
plot 'using.dat' using 3:4 title "Logged in" with impulses,     'using.dat' using 3:5 t "Load average" with points,     'using.dat' using 3:6 t "%CPU used" with lines
