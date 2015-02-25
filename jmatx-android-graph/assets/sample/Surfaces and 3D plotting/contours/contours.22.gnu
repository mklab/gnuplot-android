# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.22.png'
set logscale z 10
set view 70, 335, 1, 1
set isosamples 20, 20
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set style data lines
set title "Rosenbrock Function" 
set xlabel "x" 
set xrange [ * : * ] noreverse nowriteback  # (currently [0.00000:15.0000] )
set ylabel "y" 
set yrange [ * : * ] noreverse nowriteback  # (currently [0.00000:15.0000] )
set zlabel "Z axis" 
set zlabel  offset character 1, 0, 0 font "" textcolor lt -1 norotate
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.20000:1.20000] )
splot [0.9:1.1] [0.9:1.1] (1-x)**2 + 100*(y - x**2)**2
