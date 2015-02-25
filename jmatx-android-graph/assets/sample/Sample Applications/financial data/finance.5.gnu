# set terminal png transparent nocrop enhanced font arial 8 size 660,320 
# set output 'finance.5.png'
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set logscale y 10
set ytics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 
set ytics   (80.0000, 85.0000, 90.0000, 95.0000, 100.000, 105.000)
set title "Finance bars" 
set xrange [ 50.0000 : 253.000 ] noreverse nowriteback
set yrange [ 75.0000 : 105.000 ] noreverse nowriteback
set lmargin  9
set rmargin  2
plot 'finance.dat' using 0:2:3:4:5 notitle with financebars lt 8
