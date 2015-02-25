# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillcrvs.5.png'
set tics front
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid front   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key outside right top vertical Right noreverse enhanced autotitles nobox
set title "Some sqrt stripes on filled graph background" 
plot [0:10] [-8:6] 	-8 with filledcurve x2 lt 15, 	sqrt(x) with filledcurves y1=-0.5, 	sqrt(10-x)-4.5 with filledcurves y1=-5.5
