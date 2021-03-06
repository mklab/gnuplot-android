# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'surface1.8.png'
set samples 21, 21
set isosamples 11, 11
set title "3D gnuplot demo" 
set xlabel "X axis" 
set xlabel  offset character -3, -2, 0 font "" textcolor lt -1 norotate
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set ylabel "Y axis" 
set ylabel  offset character 3, -2, 0 font "" textcolor lt -1 rotate by -270
set yrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set zlabel "Z axis" 
set zlabel  offset character -5, 0, 0 font "" textcolor lt -1 norotate
splot x*y with points
