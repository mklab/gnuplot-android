# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.10.png'
set view 60, 30, 0.85, 1.1
set samples 20, 20
set isosamples 21, 21
set contour base
set cntrparam levels auto 10
set title "3D gnuplot demo - some more interesting contours" 
set xlabel "X axis" 
set ylabel "Y axis" 
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
splot [x=-3:3] [y=-3:3] sin(x) * cos(y)
