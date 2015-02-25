# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'textcolor.1.png'
set label 10 "label with tc default" at -1.5, 1.6, 0 left norotate front nopoint offset character 0, 0, 0
set label 11 "label with textcolor lt 1" at -1.5, 1.8, 0 left norotate front textcolor lt 1 nopoint offset character 0, 0, 0
set label 12 "label with tc lt 2" at -1.5, 1.4, 0 left norotate front textcolor lt 2 nopoint offset character 0, 0, 0
set label 13 "label with tc lt 3" at -1.5, 1.2, 0 left norotate front textcolor lt 3 nopoint offset character 0, 0, 0
set title "Textcolor options in 2D plot (notice this title in color)" 
set title  offset character 0, 0, 0 font "" textcolor lt 1 norotate
set xlabel "color of xlabel should be lt 4" 
set xlabel  offset character 0, 0, 0 font "" textcolor lt 4 norotate
set xrange [ -2.00000 : 2.00000 ] noreverse nowriteback
set ylabel "color of ylabel should still be black" 
set yrange [ -2.00000 : 2.00000 ] noreverse nowriteback
plot sin(x)
