# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'surface1.10.png'
set logscale z 10
set samples 21, 21
set isosamples 11, 11
set xtics  norangelimit
set xtics   ("low" -3.00000, "mid" 0.00000, "high" 3.00000)
set ytics -2.00000,0.5,2.00000 norangelimit
set title "Surfaces with z log scale" 
set xlabel "X axis" 
set xlabel  offset character -3, -2, 0 font "" textcolor lt -1 norotate
set xrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set ylabel "Y axis" 
set ylabel  offset character 3, -2, 0 font "" textcolor lt -1 rotate by -270
set yrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set zlabel "Z axis" 
set zlabel  offset character -5, 0, 0 font "" textcolor lt -1 norotate
splot x**2*y**2 + 2, x**2*y**4 + 2, x**4*y**2 + 2
