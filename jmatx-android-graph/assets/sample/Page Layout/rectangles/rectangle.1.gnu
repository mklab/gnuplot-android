# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rectangle.1.png'
set style fill   pattern 2 border 1
set label 10 "Label in a box" at -3, -4, 0 centre norotate front nopoint offset character 0, 0, 0
set style line 1  linetype -3 linewidth 1.000 pointtype 1 pointsize default
set style line 2  linetype 2 linecolor rgb "cyan"  linewidth 1.000 pointtype 2 pointsize default
set object  1 rect from 0, 0, 0 to 1, 4, 0 back lw 1.0 fc  lt 2 fillstyle   solid 1.00 border -1
set object  2 rect from -1, 1, 0 to 0, 5, 0 back lw 1.0 fc  rgb "gold"  fillstyle   solid 1.00 border -1
set object  5 rect from 0, -3, 0 to 2, -2, 0 back lw 1.0 fc  linestyle 2 fillstyle   pattern 1 border -1
set object  7 rect from graph 0.65, 0.85, 0 to graph 0.99, 0.99, 0 back lw 1.0 fc  linestyle 1 fillstyle   solid 1.00 border -1
set object  9 rect from -4, -4, 0 to -4, -3, 0 back lw 1.0 fc  lt -1 fillstyle   solid 1.00 border -1
set object 10 rect center -3, -4, 0 size character 14, 1, 0 front lw 1.0 fc default fillstyle  empty border -1
set object 20 rect from graph 0, 0, 0 to graph 1, 1, 0 behind lw 1.0 fc  rgb "gold"  fillstyle   solid 0.15 border -1
LABEL = "Label in a box"
plot [-5:5] x, -3+sin(x*5)/x lt 3 lw 3
