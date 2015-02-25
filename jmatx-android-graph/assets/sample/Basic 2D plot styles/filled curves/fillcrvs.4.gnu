# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillcrvs.4.png'
set tics front
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid front   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key outside right top vertical Right noreverse enhanced autotitles nobox
set title "The red bat: abs(x) with filledcurve xy=2,5" 
plot abs(x) with filledcurve xy=2,5
