# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'poldat.2.png'
set clip points
unset border
set dummy t,y
set angles degrees
set grid polar 15.000000
set grid xtics nomxtics noytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
unset key
set polar
set size ratio 1 1,1
set style data lines
set xtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set title "Antenna Pattern" 
set timestamp "%a %b %d %H:%M:%S %Y" 
set rrange [ -200.000 : 60.0000 ] noreverse nowriteback
set trange [ -3.14159 : 3.14159 ] noreverse nowriteback
set xlabel "Azimuth" 
set xrange [ -220.000 : 220.000 ] noreverse nowriteback
set ylabel "dBSM" 
set yrange [ -220.000 : 220.000 ] noreverse nowriteback
tv(t,r) = sin(t)/(1+r)
tu(t,r) = (cos(t) +r)/(1+r)
cu(t,x) = 1 + cos(t)/x
cv(t,x) = (1+ sin(t))/x
plot "-" using 1:5,"-"
