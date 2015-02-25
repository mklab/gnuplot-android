# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'arrowstyle.6.png'
set label 1 "arrowstyle 1:" at -520, -100, 0 right norotate back nopoint offset character 0, 0, 0
set label 2 "arrowstyle 2:" at -520, -110, 0 right norotate back nopoint offset character 0, 0, 0
set label 3 "arrowstyle 3:" at -520, -120, 0 right norotate back nopoint offset character 0, 0, 0
set label 4 "arrowstyle 4:" at -520, -130, 0 right norotate back nopoint offset character 0, 0, 0
set label 5 "arrowstyle 5:" at -520, -140, 0 right norotate back nopoint offset character 0, 0, 0
set label 6 "arrowstyle 6:" at -520, -150, 0 right norotate back nopoint offset character 0, 0, 0
set label 7 "arrowstyle 7:" at -520, -160, 0 right norotate back nopoint offset character 0, 0, 0
set label 8 "arrowstyle 8:" at -520, -170, 0 right norotate back nopoint offset character 0, 0, 0
set arrow 1 from -500, -100, 0 to 500, -100, 0 head back filled linetype 1 linewidth 2.000 size screen 0.025,30.000,45.000
set arrow 2 from -500, -110, 0 to 500, -110, 0 head back nofilled linetype 3 linewidth 2.000 size screen 0.030,15.000,90.000
set arrow 3 from -500, -120, 0 to 500, -120, 0 head back filled linetype 1 linewidth 2.000 size screen 0.030,15.000,45.000
set arrow 4 from -500, -130, 0 to 500, -130, 0 head back filled linetype 3 linewidth 2.000 size screen 0.030,15.000,90.000
set arrow 5 from -500, -140, 0 to 500, -140, 0 heads back filled linetype 1 linewidth 2.000 size screen 0.030,15.000,135.000
set arrow 6 from -500, -150, 0 to 500, -150, 0 head back empty linetype 3 linewidth 2.000 size screen 0.030,15.000,135.000
set arrow 7 from -500, -160, 0 to 500, -160, 0 nohead back nofilled linetype 1 linewidth 2.000
set arrow 8 from -500, -170, 0 to 500, -170, 0 heads back nofilled linetype 3 linewidth 2.000 size screen 0.008,90.000,90.000
set style line 1  linetype 1 linewidth 2.000 pointtype 1 pointsize default
set style line 2  linetype 3 linewidth 2.000 pointtype 2 pointsize default
set style arrow 1 head back filled linetype 1 linewidth 2.000 size screen 0.025,30.000,45.000
set style arrow 2 head back nofilled linetype 3 linewidth 2.000 size screen 0.030,15.000,90.000
set style arrow 3 head back filled linetype 1 linewidth 2.000 size screen 0.030,15.000,45.000
set style arrow 4 head back filled linetype 3 linewidth 2.000 size screen 0.030,15.000,90.000
set style arrow 5 heads back filled linetype 1 linewidth 2.000 size screen 0.030,15.000,135.000
set style arrow 6 head back empty linetype 3 linewidth 2.000 size screen 0.030,15.000,135.000
set style arrow 7 nohead back nofilled linetype 1 linewidth 2.000
set style arrow 8 heads back nofilled linetype 3 linewidth 2.000 size screen 0.008,90.000,90.000
set title "Top: plot with vectors arrowstyle 6, Bottom: explicit arrows" 
set xrange [ -1000.00 : 1000.00 ] noreverse nowriteback
set yrange [ -178.000 : 86.0000 ] noreverse nowriteback
plot      'arrowstyle.dat' using 1:2:(0):3 notitle with vectors arrowstyle 6
