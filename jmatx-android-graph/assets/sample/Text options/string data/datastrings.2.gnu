# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'datastrings.2.png'
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
set title "Read tic labels from a datafile column\nAn approximation of Hans Olav Eggestad's categoric plot patch\nusing 'using ($0):2:xticlabels(1)' and 'set style fill solid border -1'" 
set xrange [ -0.500000 : 11.5000 ] noreverse nowriteback
set ylabel "mm" 
set yrange [ 0.00000 : 180.000 ] noreverse nowriteback
set bmargin  3
plot 'ctg-y2.dat' using ($0-0.2):2 index 0 with boxes title "precipitation 1992-2000",      ''           using ($0+0.2):2 index 1 with boxes title "              2000-2001",      ''           using ($0):2 index 2 with linespoints lw 3 title "runoff 1992-2000",      ''           using ($0):2:xticlabels(1) index 3 with linespoints lw 3 title "       2000-2001"
