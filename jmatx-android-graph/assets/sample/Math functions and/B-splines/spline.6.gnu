# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'spline.6.png'
set dummy t,y
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set arrow 1 from 0.33, 0.2, 0 to 0.23, 0.366667, 0 head back nofilled linetype -1 linewidth 1.000
set arrow 2 from 0.66, 0.8, 0 to 0.526667, 0.866667, 0 head back nofilled linetype -1 linewidth 1.000
set arrow 3 from 0.33, 0.2, 0 to 0.03, 0.7, 0 head back nofilled linetype -1 linewidth 1.000
set arrow 4 from 0.66, 0.8, 0 to 0.26, 1, 0 head back nofilled linetype -1 linewidth 1.000
set parametric
set title "The cubic Hermite basis functions in use" 
set trange [ 0.00000 : 1.00000 ] noreverse nowriteback
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
bez0(x) = (1 - x)**3
bez1(x) = 3 * (1 - x)**2 * x
bez2(x) = 3 * (1 - x) * x**2
bez3(x) = x**3
bsp0(x) = ( 1 - 3 * x + 3 * x**2 - x**3 ) / 6
bsp1(x) = ( 4 - 6 * x**2 + 3 * x**3 ) / 6
bsp2(x) = ( 1 + 3 * x + 3 * x**2 - 3 * x**3 ) / 6
bsp3(x) = x**3 / 6
cub_bezier_x(t) = bez0(t) * x0 + bez1(t) * x1 + bez2(t) * x2 + bez3(t) * x3
cub_bezier_y(t) = bez0(t) * y0 + bez1(t) * y1 + bez2(t) * y2 + bez3(t) * y3
cub_bsplin_x(t) = bsp0(t) * x0 + bsp1(t) * x1 + bsp2(t) * x2 + bsp3(t) * x3
cub_bsplin_y(t) = bsp0(t) * y0 + bsp1(t) * y1 + bsp2(t) * y2 + bsp3(t) * y3
cub_hermit_x1(t) = h00(t) * x1 + h01(t) * x2 + h10(t) * xv0 + h11(t) * xv1
cub_hermit_y1(t) = h00(t) * y1 + h01(t) * y2 + h10(t) * yv0 + h11(t) * yv1
cub_hermit_x2(t) = h00(t) * x1 + h01(t) * x2 + h10(t) * xv0*3 + h11(t) * xv1*3
cub_hermit_y2(t) = h00(t) * y1 + h01(t) * y2 + h10(t) * yv0*3 + h11(t) * yv1*3
y0 = 1
y1 = 0.2
y2 = 0.8
y3 = 0
x0 = 0
x1 = 0.33
x2 = 0.66
x3 = 1
xv0 = -0.3
yv0 = 0.5
xv1 = -0.4
yv1 = 0.2
plot cub_hermit_x1(t), cub_hermit_y1(t) with lines lt 2,     cub_hermit_x2(t), cub_hermit_y2(t) with lines lt 3
