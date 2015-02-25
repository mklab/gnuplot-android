# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'arrowstyle.9.png'
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "Plot 'file' with vectors <arrowstyle>" 
set yrange [ * : 10.0000 ] noreverse nowriteback  # (currently [-10.0000:] )
plot '1.dat' using 1:2:(+1):(+1) with vectors lt 4 filled title 'filled',      '2.dat' using 1:2:(+1):(+1) with vectors lt 1 heads title 'double-headed',      '2.dat' using ($1):(2-$2/3):(+1):(+.5) with vectors lt -1 lw 3 nohead title 'no head'
