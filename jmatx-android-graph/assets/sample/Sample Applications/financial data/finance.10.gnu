# set terminal png transparent nocrop enhanced font arial 8 size 660,320 
# set output 'finance.10.png'
set format y "%1.0f"
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set size ratio 0 1,0.3
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 
set xtics   (66.0000, 87.0000, 109.000, 130.000, 151.000, 174.000, 193.000, 215.000, 235.000)
set ytics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 500
set xrange [ 50.0000 : 253.000 ] noreverse nowriteback
set yrange [ * : * ] noreverse nowriteback  # (currently [75.0000:105.000] )
set lmargin  9
set rmargin  2
set tmargin  0
plot 'finance.dat' using 0:($6/10000) notitle with impulses lt 3, 'finance.dat' using 0:($7/10000) notitle with lines lt 1
