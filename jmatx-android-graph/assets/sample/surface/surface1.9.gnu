# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'surface1.9.png'
set samples 21, 21
set isosamples 11, 11
set noxtics
set noytics
set title "Surfaces with no grid or tics" 
set xlabel "X axis" 
set xlabel  offset character -3, -2, 0 font "" textcolor lt -1 norotate
set xrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set ylabel "Y axis" 
set ylabel  offset character 3, -2, 0 font "" textcolor lt -1 rotate by -270
set yrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set zlabel "Z axis" 
set zlabel  offset character -5, 0, 0 font "" textcolor lt -1 norotate
splot x*y with lines, x**2*y**3 with dots, x**3*y*2 with points
