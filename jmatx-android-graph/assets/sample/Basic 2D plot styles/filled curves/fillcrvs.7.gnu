# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillcrvs.7.png'
set format x ""
set format y ""
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 0.500,  linetype 0 linewidth 0.500
set object  1 rect from graph 0, 0, 0 to graph 1, 1, 0 behind lw 1.0 fc  rgb "#afffff"  fillstyle   solid 1.00 border -1
set title "world.dat plotted with filledcurves" 
set xrange [ -180.000 : 180.000 ] noreverse nowriteback
set yrange [ -70.0000 : 80.0000 ] noreverse nowriteback
set lmargin  1
plot 'world.dat' with filledcurve notitle fs solid 1.0 lc rgb 'dark-goldenrod'
