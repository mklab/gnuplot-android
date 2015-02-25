# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'histograms.6.png'
set border 3 front linetype -1 linewidth 1.000
set boxwidth 0.75 absolute
set style fill   solid 1.00 border -1
set grid nopolar
set grid noxtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key outside right top vertical Left reverse enhanced autotitles columnhead box linetype -1 linewidth 1.000
set style histogram columnstacked title  offset character 0, 0, 0
set datafile missing '-'
set style data histograms
set xtics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 
set xtics   ("Denmark" 0.500000, "Netherlands" 1.50000, "Norway" 2.50000, "Sweden" 3.50000)
set ytics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set ztics border in scale 0,0 nomirror norotate  offset character 0, 0, 0 autofreq 
set cbtics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set title "Immigration from Northern Europe\n(columstacked histogram)" 
set xlabel "Country of Origin" 
set ylabel "Immigration by decade" 
set yrange [ 0.00000 : * ] noreverse nowriteback  # (currently [:100.000] )
i = 24
plot 'immigration.dat' using 6 ti col, '' using 12 ti col,      '' using 13 ti col, '' using 14:key(1) ti col
