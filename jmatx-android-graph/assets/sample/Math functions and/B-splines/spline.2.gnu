# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'spline.2.png'
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "The cubic Hermite basis functions" 
set xrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set yrange [ -0.200000 : 1.40000 ] noreverse nowriteback
m0(x) = 1
m1(x) = x
m2(x) = x**2
m3(x) = x**3
h00(x) = x**2 * ( 2 * x - 3) + 1
h01(x) = -x**2 * (2 * x - 3)
h10(x) = x * (x - 1)**2
h11(x) = x**2 * (x - 1)
plot h00(x), h01(x), h10(x), h11(x)
