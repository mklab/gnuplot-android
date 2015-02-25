# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.4.png'
set view 60, 30, 0.85, 1.1
set samples 20, 20
set isosamples 21, 21
set contour base
set cntrparam levels discrete -75,-50 ,0 
set title "3D gnuplot demo - contour plot (at -75, -50, 0)" 
set xlabel "X axis" 
set ylabel "Y axis" 
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
splot x*y
