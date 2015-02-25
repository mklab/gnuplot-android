# set terminal png transparent nocrop enhanced font arial 8 size 660,320 
# set output 'finance.13.png'
set format y "%1.0f"
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set size ratio 0 1,0.3
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 
set xtics   ("6/03" 66.0000, "7/03" 87.0000, "8/03" 109.000, "9/03" 130.000, "10/03" 151.000, "11/03" 174.000, "12/03" 193.000, "1/04" 215.000, "2/04" 235.000)
set ytics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 500
set xrange [ 50.0000 : 253.000 ] noreverse nowriteback
set ylabel "volume (0000)" 
set ylabel  offset character 1, 0, 0 font "" textcolor lt -1 rotate by 90
set yrange [ * : * ] noreverse nowriteback  # (currently [75.0000:105.000] )
set lmargin  9
set rmargin  2
set tmargin  0
plot 'finance.dat' using 0:($6/10000) notitle with impulses lt 3, 'finance.dat' using 0:($7/10000) notitle with lines lt 1
