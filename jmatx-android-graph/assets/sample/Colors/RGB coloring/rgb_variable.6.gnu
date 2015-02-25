# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rgb_variable.6.png'
unset border
set angles degrees
set label 99 "" at 0, 0, 0 left norotate back nopoint offset character 0, 0, 0
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set xzeroaxis linetype -1 linecolor rgb "red"  linewidth 2.000
set yzeroaxis linetype -1 linecolor rgb "green"  linewidth 2.000
set zzeroaxis linetype -1 linecolor rgb "blue"  linewidth 2.000
set xyplane at 0
set xtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ztics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set title "Demo of hidden3d with points only (no surface)" 
set xlabel "Red" 
set xlabel  offset character 0, 0, 0 font "" textcolor rgb "red"  norotate
set xrange [ 0.00000 : 255.000 ] noreverse nowriteback
set ylabel "Green" 
set ylabel  offset character 0, 0, 0 font "" textcolor rgb "green"  rotate by 90
set yrange [ 0.00000 : 255.000 ] noreverse nowriteback
set zlabel "Blue" 
set zlabel  offset character 0, 0, 0 font "" textcolor rgb "blue"  norotate
set zrange [ 0.00000 : 255.000 ] noreverse nowriteback
set lmargin  5
set bmargin  2
set rmargin  5
rgb(r,g,b) = int(r)*65536 + int(g)*256 + int(b)
xrgb(r,g,b) = (g-b)/255. * cos(30.)
yrgb(r,g,b) = r/255. - (g+b)/255. * sin(30.)
splot 'rgb_variable.dat' using 1:2:3:(5*rand(0)):(rgb($1,$2,$3)) with points pt 7 ps variable lc rgb variable       title "variable pointsize and rgb color computed from coords"
