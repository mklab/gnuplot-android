# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'textcolor.2.png'
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set label 1 "textcolor palette z" at 12, -10, -10 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 3 "tc pal z" at 12, -6, -6 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 4 "tc pal z" at 12, -3, -3 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 5 "tc pal z" at 12, 0, 0 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 6 "tc pal z" at 12, 3, 3 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 7 "tc pal z" at 12, 6, 6 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 8 "tc pal z" at 12, 9, 9 left norotate back textcolor palette z nopoint offset character 0, 0, 0
set label 10 "textcolor lt 1" at -10, -8, 24 left norotate front textcolor lt 1 nopoint offset character 0, 0, 0
set label 11 "tc lt 2" at -10, -8, 21 left norotate front textcolor lt 2 nopoint offset character 0, 0, 0
set label 12 "tc lt 3" at -10, -8, 18 left norotate front textcolor lt 3 nopoint offset character 0, 0, 0
set label 13 "textcolor default" at -10, -8, 15 left norotate front nopoint offset character 0, 0, 0
set label 14 "textcolor cb 5" at -10, -8, 12 left norotate front textcolor palette cb 5 nopoint offset character 0, 0, 0
set label 15 "tc cb 0" at -10, -8, 9 left norotate front textcolor palette cb 0 nopoint offset character 0, 0, 0
set label 16 "tc cb -5" at -10, -8, 6 left norotate front textcolor palette cb -5 nopoint offset character 0, 0, 0
set label 17 "textcolor frac .75" at -10, -8, 3 left norotate back textcolor palette fraction 0.75 nopoint offset character 0, 0, 0
set label 18 "tc frac .25" at -10, -8, 0 left norotate back textcolor palette fraction 0.25 nopoint offset character 0, 0, 0
set view 58, 64, 0.83, 1
set samples 20, 20
set isosamples 20, 20
set title "Textcolor options in splot (notice this title in color)" 
set title  offset character 0, 0, 0 font "" textcolor lt 1 norotate
set xlabel "xlabel should be lt 4" 
set xlabel  offset character 0, 0, 0 font "" textcolor lt 4 norotate
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set ylabel "ylabel should still be black" 
set yrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set zrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set cblabel "color cblabel" 
set cblabel  offset character 0, 0, 0 font "" textcolor lt 3 norotate
set pm3d implicit at s
set colorbox user
set colorbox horizontal origin screen 0.1, 0.12, 0 size screen 0.8, 0.015, 0 bdefault
splot y
