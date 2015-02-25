# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rgb_variable.3.png'
set border -1 front linetype -1 linewidth 1.000
set angles degrees
set ticslevel 0
set xtics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set title "Both RGB color information\n and point size controlled by input" 
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
splot 'rgb_variable.dat' using 1:2:3:(rgb($1,$2,$3)) with points pt 7 ps 4 lc rgb variable,       '' using 1:2:3:(sprintf("0x%x",rgb($1,$2,$3))) with labels left offset 1 notitle
