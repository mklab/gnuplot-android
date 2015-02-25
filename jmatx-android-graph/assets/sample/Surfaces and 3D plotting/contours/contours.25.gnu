# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'contours.25.png'
set border 15 front linetype -1 linewidth 1.000
set logscale z 10
set view 20, 340, 1, 1
set isosamples 60, 60
unset surface
set contour base
unset clabel
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set style data lines
set ticslevel 0
set noztics
set title "All contours drawn in a single color" 
set xlabel "x" 
set xrange [ * : * ] noreverse nowriteback  # (currently [0.00000:15.0000] )
set ylabel "y" 
set yrange [ * : * ] noreverse nowriteback  # (currently [0.00000:15.0000] )
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.20000:1.20000] )
splot [-1.5:1.5] [-0.5:1.5] (1-x)**2 + 100*(y - x**2)**2 with lines lc rgb "#007700"
