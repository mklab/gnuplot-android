# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.15.png'
set view 60, 30, 0.85, 1.1
set samples 6, 6
set isosamples 6, 6
set contour base
set cntrparam order 8
set title "3D gnuplot demo - low resolution (6x6) using linear contours." 
set xlabel "X axis" 
set ylabel "Y axis" 
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.00000:1.00000] )
splot x*y
