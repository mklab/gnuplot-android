# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'datastrings.5.png'
set boxwidth 0.3 absolute
set style fill   solid 1.00 border -1
set grid nopolar
set grid noxtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key inside right top vertical Right noreverse enhanced autotitles columnhead nobox
set pointsize 2
set xtics border in scale 0,0 mirror norotate  offset character 0, 0, 0 
set xtics   ("mai" 0.00000, "jun" 1.00000, "jul" 2.00000, "aug" 3.00000, "sep" 4.00000, "okt" 5.00000, "nov" 6.00000, "des" 7.00000, "jan" 8.00000, "feb" 9.00000, "mar" 10.0000, "apr" 11.0000)
set ytics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set ztics border in scale 0,0 nomirror norotate  offset character 0, 0, 0 autofreq 
set cbtics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set title "Plot actual y-value as a label" 
set xrange [ -0.500000 : 11.5000 ] noreverse nowriteback
set ylabel "mm" 
set yrange [ 0.00000 : 180.000 ] noreverse nowriteback
set bmargin  3
plot 'table.dat' using 0:2 title 2 with boxes,      ''          using 0:2:2 with labels center offset 0,1 notitle
