# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.19.png'
set dummy u,v
set parametric
set view 60, 30, 0.85, 1.1
set samples 25, 25
set isosamples 26, 26
set contour base
set cntrparam bspline
set cntrparam levels auto 10
set style data lines
set title "3D gnuplot demo - contour of data grid plotting" 
set xlabel "X axis" 
set ylabel "Y axis" 
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
set zrange [ -1.00000 : 1.00000 ] noreverse nowriteback
splot "glass.dat"
