# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'data_feedback.1.png'
set border 3 front linetype -1 linewidth 1.000
set key inside right center vertical Left reverse enhanced autotitles box linetype -1 linewidth 1.000
set key invert samplen 4 spacing 1 width 0 height 0 
set style data linespoints
set xtics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0
set ytics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0
set title "Demonstrate use of assignment and serial evaluation operators\nto accumulate statistics as successive data lines are read in\n" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set urange [ * : * ] noreverse nowriteback  # (currently [-10.0000:10.0000] )
set vrange [ * : * ] noreverse nowriteback  # (currently [-10.0000:10.0000] )
set xrange [ 0.00000 : 57.0000 ] noreverse nowriteback
set x2range [ * : * ] noreverse nowriteback  # (currently [0.00000:57.0000] )
set yrange [ * : * ] noreverse nowriteback  # (currently [0.00000:300.000] )
set y2range [ * : * ] noreverse nowriteback  # (currently [4.00000:280.000] )
samples(x) = $0 > 4 ? 5 : ($0+1)
avg5(x) = (shift5(x), (back1+back2+back3+back4+back5)/samples($0))
shift5(x) = (back5 = back4, back4 = back3, back3 = back2, back2 = back1, back1 = x)
init(x) = (back1 = back2 = back3 = back4 = back5 = sum = 0)
back1 = 5.0
back2 = 9.0
back3 = 8.0
back4 = 9.0
back5 = 10.0
datafile = "silver.dat"
sum = 1918.0
plot sum = init(0),      datafile using 0:2 title 'data' lw 2 lc rgb 'forest-green',      '' using 0:(avg5($2)) title "running mean over previous 5 points" pt 7 ps 0.5 lw 1 lc rgb "blue",      '' using 0:(sum = sum + $2, sum/($0+1)) title "cumulative mean" pt 1 lw 1 lc rgb "dark-red"
