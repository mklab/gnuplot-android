# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'layout.4.png'
set boxwidth 0.8 absolute
set style fill   solid 1.00 border
set format y "    "
set key inside right top vertical Right noreverse enhanced autotitles columnhead nobox
set key noinvert samplen 1 spacing 1 width 0 height 0 
set style histogram columnstacked title  offset character 0, 0, 0
set style data boxes
set xtics border in scale 0,0 nomirror norotate  offset character 0, 0, 0 
set xtics   ("1891-1900" 0.00000, "1901-1910" 1.00000, "1911-1920" 2.00000, "1921-1930" 3.00000, "1931-1940" 4.00000, "1941-1950" 5.00000, "1951-1960" 6.00000, "1961-1970" 7.00000)
set ytics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set ztics border in scale 0,0 nomirror norotate  offset character 0, 0, 0 autofreq 
set cbtics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set xlabel "Immigration to U.S. by Decade" 
set yrange [ 0.00000 : 800000. ] noreverse nowriteback
set lmargin  3
set bmargin  0
set rmargin  3
set tmargin  0
plot 'immigration.dat' using 21:xtic(1) lt 4
