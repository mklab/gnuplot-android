# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rectangle.2.png'
set style rectangle back fc  lt -1 fillstyle   solid 0.15 noborder
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set label 1 "(Weekend)" at 5, 25, 0 centre norotate back nopoint offset character 0, 0, 0
set object  1 rect from 1, graph 0, 0 to 2, graph 1, 0 back lw 1.0 fc default fillstyle  default
set object  2 rect from 3, graph 0, 0 to 4, graph 1, 0 back lw 1.0 fc default fillstyle  default
set object  3 rect from 5, graph 0, 0 to 6, graph 1, 0 back lw 1.0 fc default fillstyle  default
set object  4 rect from 7, graph 0, 0 to 8, graph 1, 0 back lw 1.0 fc default fillstyle  default
set title "Convex     November 1-7 1989" 
set xrange [ 1.00000 : 8.00000 ] noreverse nowriteback
LABEL = "Label in a box"
plot 'using.dat' using 3:4 title "Logged in" with impulses,     'using.dat' using 3:5 t "Load average" with points,     'using.dat' using 3:6 t "%CPU used" with lines
