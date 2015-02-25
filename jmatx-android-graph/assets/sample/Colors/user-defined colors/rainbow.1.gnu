# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rainbow.1.png'
set label 1 "set style line 1 lt rgb \"red\" lw 3" at -0.4, -0.25, 0 left norotate back textcolor rgb "red"  nopoint offset character 0, 0, 0
set label 2 "set style line 2 lt rgb \"orange\" lw 2" at -0.4, -0.35, 0 left norotate back textcolor rgb "orange"  nopoint offset character 0, 0, 0
set label 3 "set style line 3 lt rgb \"yellow\" lw 3" at -0.4, -0.45, 0 left norotate back textcolor rgb "yellow"  nopoint offset character 0, 0, 0
set label 4 "set style line 4 lt rgb \"green\" lw 2" at -0.4, -0.55, 0 left norotate back textcolor rgb "green"  nopoint offset character 0, 0, 0
set label 5 "set style line 5 lt rgb \"cyan\" lw 3" at -0.4, -0.65, 0 left norotate back textcolor rgb "cyan"  nopoint offset character 0, 0, 0
set label 6 "set style line 6 lt rgb \"blue\" lw 2" at -0.4, -0.75, 0 left norotate back textcolor rgb "blue"  nopoint offset character 0, 0, 0
set label 7 "set style line 7 lt rgb \"violet\" lw 3" at -0.4, -0.85, 0 left norotate back textcolor rgb "violet"  nopoint offset character 0, 0, 0
set style line 1  linetype 1 linecolor rgb "red"  linewidth 3.000 pointtype 1 pointsize default
set style line 2  linetype 2 linecolor rgb "orange"  linewidth 2.000 pointtype 2 pointsize default
set style line 3  linetype 3 linecolor rgb "yellow"  linewidth 3.000 pointtype 3 pointsize default
set style line 4  linetype 4 linecolor rgb "green"  linewidth 2.000 pointtype 4 pointsize default
set style line 5  linetype 5 linecolor rgb "cyan"  linewidth 3.000 pointtype 5 pointsize default
set style line 6  linetype 6 linecolor rgb "blue"  linewidth 2.000 pointtype 6 pointsize default
set style line 7  linetype 7 linecolor rgb "violet"  linewidth 3.000 pointtype 7 pointsize default
set noxtics
set noytics
set title "Terminal-independent RGB colors in 2D" 
set xlabel "Implemented using built-in rgb color names\n(only works for terminals that can do full rgb color)" 
set xrange [ -0.500000 : 3.50000 ] noreverse nowriteback
set yrange [ -1.00000 : 1.40000 ] noreverse nowriteback
set bmargin  7
plot cos(x)     ls 1 title 'red',        cos(x-.2)  ls 2 title 'orange',     cos(x-.4)  ls 3 title 'yellow',     cos(x-.6)  ls 4 title 'green',      cos(x-.8)  ls 5 title 'cyan',       cos(x-1.)  ls 6 title 'blue',       cos(x-1.2) ls 7 title 'violet'
