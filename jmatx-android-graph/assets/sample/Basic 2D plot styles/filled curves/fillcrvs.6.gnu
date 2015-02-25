# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillcrvs.6.png'
unset border
set dummy t,y
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
unset key
set label 1 "gnuplot" at 0, 1.2, 0 centre norotate front nopoint offset character 0, 0, 0
set label 2 "gnuplot" at 0.02, -0.6, 0 centre norotate front nopoint offset character 0, 0, 0
set arrow 1 from -0.1, 0.26, 0 to 0.18, -0.17, 0 head front nofilled linetype 5 linewidth 4.000 size first 0.100,40.000,90.000
set parametric
set size ratio 1 1,1
set noxtics
set noytics
set title "Let's smile with parametric filled curves" 
set xrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set yrange [ -1.00000 : 1.60000 ] noreverse nowriteback
plot [t=-pi:pi] 	sin(t),cos(t) with filledcurve xy=0,0 lt 15,		sin(t)/8-0.5,cos(t)/8+0.4 with filledcurve lt 3,		sin(t)/8+0.5,cos(t)/8+0.4 with filledcurve lt 3,		t/5,abs(t/5)-0.8 with filledcurve xy=0.1,-0.5 lt 1, 	t/3,1.52-abs(t/pi) with filledcurve xy=0,1.8 lt -1
