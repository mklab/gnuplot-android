# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'surface1.25.png'
set samples 51, 51
set isosamples 60, 60
set hidden3d back offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set style data lines
set title "Mandelbrot function" 
set xlabel "X axis" 
set xlabel  offset character -3, -2, 0 font "" textcolor lt -1 norotate
set ylabel "Y axis" 
set ylabel  offset character 3, -2, 0 font "" textcolor lt -1 rotate by -270
set zlabel "Z axis" 
set zlabel  offset character -5, 0, 0 font "" textcolor lt -1 norotate
sinc(u,v) = sin(sqrt(u**2+v**2)) / sqrt(u**2+v**2)
compl(a,b)=a*{1,0}+b*{0,1}
mand(z,a,n) = n<=0 || abs(z)>100 ? 1:mand(z*z+a,a,n-1)+1
GPFUN_sinc = "sinc(u,v) = sin(sqrt(u**2+v**2)) / sqrt(u**2+v**2)"
xx = 6.08888888888889
dx = 1.11
x0 = -5
x1 = -3.89111111111111
x2 = -2.78222222222222
x3 = -1.67333333333333
x4 = -0.564444444444444
x5 = 0.544444444444445
x6 = 1.65333333333333
x7 = 2.76222222222222
x8 = 3.87111111111111
x9 = 4.98
xmin = -4.99
xmax = 5
n = 10
zbase = -1
GPFUN_compl = "compl(a,b)=a*{1,0}+b*{0,1}"
GPFUN_mand = "mand(z,a,n) = n<=0 || abs(z)>100 ? 1:mand(z*z+a,a,n-1)+1"
splot [-2:1][-1.5:1.5] mand({0,0},compl(x,y),30)
