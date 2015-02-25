# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'heatmaps.3.png'
set format cb "%4.1f"
set view 49, 28, 1, 1.48
set samples 25, 25
set isosamples 50, 50
set ticslevel 0
set cbtics border in scale 0,0 mirror norotate  offset character 0, 0, 0
set title "4D data (3D Heat Map)\nIndependent value color-mapped onto 3D surface" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set urange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set vrange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xlabel "x" 
set xlabel  offset character 3, 0, 0 font "" textcolor lt -1 norotate
set xrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set x2range [ * : * ] noreverse nowriteback  # (currently [-0.500000:4.50000] )
set ylabel "y" 
set ylabel  offset character -5, 0, 0 font "" textcolor lt -1 rotate by 90
set yrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set y2range [ * : * ] noreverse nowriteback  # (currently [-0.500000:4.50000] )
set zlabel "z" 
set zlabel  offset character 2, 0, 0 font "" textcolor lt -1 norotate
set zrange [ -0.250000 : 1.00000 ] noreverse nowriteback
set cbrange [ * : * ] noreverse nowriteback  # (currently [-1.00000:1.00000] )
set pm3d implicit at s
set colorbox user
set colorbox vertical origin screen 0.9, 0.2, 0 size screen 0.03, 0.6, 0 front noborder
Z(x,y) = sin(sqrt(x**2+y**2))/sqrt(x**2+y**2)
color(x,y) = sin(x/5.)*cos(y/10.)
splot '+' using 1:2:(Z($1,$2)):(color($1,$2)) with pm3d title "4 data columns x/y/z/color"
