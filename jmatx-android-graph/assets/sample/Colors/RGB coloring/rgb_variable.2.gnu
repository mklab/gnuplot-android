# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rgb_variable.2.png'
unset border
set angles degrees
set arrow 1 from 0, 0, 0 to 0, 1, 0 nohead back nofilled linetype -1 linecolor rgb "red"  linewidth 3.000
set arrow 2 from 0, 0, 0 to 0.866025, -0.5, 0 nohead back nofilled linetype -1 linecolor rgb "green"  linewidth 3.000
set arrow 3 from 0, 0, 0 to -0.866025, -0.5, 0 nohead back nofilled linetype -1 linecolor rgb "blue"  linewidth 3.000
set noxtics
set noytics
set noztics
set title "Both RGB color information\n and point size controlled by input" 
set lmargin  5
set bmargin  2
set rmargin  5
rgb(r,g,b) = int(r)*65536 + int(g)*256 + int(b)
xrgb(r,g,b) = (g-b)/255. * cos(30.)
yrgb(r,g,b) = r/255. - (g+b)/255. * sin(30.)
plot 'rgb_variable.dat' using (xrgb($1,$2,$3)):(yrgb($1,$2,$3)):(1.+2.*rand(0)):(rgb($1,$2,$3))      with points pt 7 ps var lc rgb variable notitle
