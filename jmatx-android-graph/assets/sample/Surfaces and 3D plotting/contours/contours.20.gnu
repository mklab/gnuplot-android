# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.20.png'
set view 60, 30, 0.85, 1.1
set samples 25, 25
set isosamples 26, 26
set contour base
set cntrparam bspline
set cntrparam levels auto 10
set style data lines
set title "3D gnuplot demo - contour of data grid plotting" 
set xlabel "X axis" 
set xrange [ 0.00000 : 15.0000 ] noreverse nowriteback
set ylabel "Y axis" 
set yrange [ 0.00000 : 15.0000 ] noreverse nowriteback
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
set zrange [ -1.20000 : 1.20000 ] noreverse nowriteback
splot "glass.dat" using 1
