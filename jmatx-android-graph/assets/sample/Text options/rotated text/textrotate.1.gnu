# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'textrotate.1.png'
set label 1 "Default" at -4, 4, 0 left norotate back nopoint offset character 0, 0, 0
set label 2 "rotate left" at 0, 0, 0 left rotate by 90 back nopoint offset character 0, 0, 0
set label 3 "rotate right" at 0.5, 0, 0 right rotate by 90 back nopoint offset character 0, 0, 0
set label 4 "rotate center" at 1, 0, 0 centre rotate by 90 back nopoint offset character 0, 0, 0
set label 12 "rotate by -90 left" at 2, 0, 0 left rotate by -90 back nopoint offset character 0, 0, 0
set label 13 "rotate by -90 right" at 2.5, 0, 0 right rotate by -90 back nopoint offset character 0, 0, 0
set label 14 "rotate by -90 center" at 3, 0, 0 centre rotate by -90 back nopoint offset character 0, 0, 0
set label 21 "  rotate by 45" at -3, 0, 0 left rotate by 45 back point linetype -1 linewidth 1.000 pointtype 1 pointsize 2.000 offset character 0, 0, 0
set label 22 "  rotate by 90" at -3, 0, 0 left rotate by 90 back point linetype -1 linewidth 1.000 pointtype 1 pointsize 2.000 offset character 0, 0, 0
set label 23 "  rotate by -30" at -3, 0, 0 left rotate by -30 back point linetype -1 linewidth 1.000 pointtype 1 pointsize 2.000 offset character 0, 0, 0
set label 24 "  rotate by -60" at -3, 0, 0 left rotate by -60 back point linetype -1 linewidth 1.000 pointtype 1 pointsize 2.000 offset character 0, 0, 0
set label 25 "  rotate by -90" at -3, 0, 0 left rotate by -90 back point linetype -1 linewidth 1.000 pointtype 1 pointsize 2.000 offset character 0, 0, 0
set title "Rotation of label text" 
set xlabel "xlabel" 
set xrange [ -5.00000 : 5.00000 ] noreverse nowriteback
set ylabel "ylabel" 
set yrange [ -5.00000 : 5.00000 ] noreverse nowriteback
plot 0
