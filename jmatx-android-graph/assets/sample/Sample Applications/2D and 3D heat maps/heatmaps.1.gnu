# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'heatmaps.1.png'
unset key
set view map
set style data linespoints
set xtics border in scale 0,0 mirror norotate  offset character 0, 0, 0
set ytics border in scale 0,0 mirror norotate  offset character 0, 0, 0
set ztics border in scale 0,0 nomirror norotate  offset character 0, 0, 0
set nocbtics
set title "Heat Map generated from a file containing Z values only" 
set urange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set vrange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ -0.500000 : 4.50000 ] noreverse nowriteback
set yrange [ -0.500000 : 4.50000 ] noreverse nowriteback
set zrange [ * : * ] noreverse nowriteback  # (currently [0.00000:5.00000] )
set cblabel "Score" 
set cbrange [ 0.00000 : 5.00000 ] noreverse nowriteback
set palette rgbformulae -7, 2, -7
splot '-' matrix with image
