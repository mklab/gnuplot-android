# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.18.png'
set view 60, 30, 0.85, 1.1
set samples 25, 25
set isosamples 26, 26
set contour base
set cntrparam bspline
set title "3D gnuplot demo - contour of Sinc function" 
set xlabel "X axis" 
set ylabel "Y axis" 
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.00000:1.00000] )
splot [-12:12.01] [-12:12.01] sin(sqrt(x**2+y**2)) / sqrt(x**2+y**2)
