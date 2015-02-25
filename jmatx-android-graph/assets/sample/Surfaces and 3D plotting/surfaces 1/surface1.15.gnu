# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'surface1.15.png'
set dummy u,v
set label 1 "This is equal to 1" at -5, -2, 1.5 centre norotate back nopoint offset character 0, 0, 0
set arrow 1 from -5, -2.1, 1.4 to 0, 0, 1 head back nofilled linetype -1 linewidth 1.000
set view 70, 20, 1, 1
set samples 51, 51
set isosamples 21, 21
set ztics -1.00000,0.25,1.00000 norangelimit
set title "Sinc function" 
set xlabel "X axis" 
set xlabel  offset character -3, -2, 0 font "" textcolor lt -1 norotate
set xrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set ylabel "Y axis" 
set ylabel  offset character 3, -2, 0 font "" textcolor lt -1 rotate by -270
set yrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set zlabel "Z axis" 
set zlabel  offset character -5, 0, 0 font "" textcolor lt -1 norotate
set zrange [ -0.500000 : 1.00000 ] noreverse nowriteback
sinc(u,v) = sin(sqrt(u**2+v**2)) / sqrt(u**2+v**2)
GPFUN_sinc = "sinc(u,v) = sin(sqrt(u**2+v**2)) / sqrt(u**2+v**2)"
splot [-12:12.01] [-12:12.01] sinc(u,v)
