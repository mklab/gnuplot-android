# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'spline.1.png'
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "The cubic Monomial basis functions" 
set xrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set yrange [ -0.200000 : 1.40000 ] noreverse nowriteback
m0(x) = 1
m1(x) = x
m2(x) = x**2
m3(x) = x**3
plot m0(x), m1(x), m2(x), m3(x)
