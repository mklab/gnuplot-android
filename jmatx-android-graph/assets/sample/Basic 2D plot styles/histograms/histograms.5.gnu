# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'histograms.5.png'
set border 3 front linetype -1 linewidth 1.000
set boxwidth 0.75 absolute
set style fill   solid 1.00 border -1
set grid nopolar
set grid noxtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key outside right top vertical Left reverse enhanced autotitles columnhead nobox
set key invert samplen 4 spacing 1 width 0 height 0 
set style histogram rowstacked title  offset character 0, 0, 0
set datafile missing '-'
set style data histograms
set xtics border in scale 1,0.5 nomirror rotate by -45  offset character 0, 0, 0 
set xtics   ("1891-1900" 0.00000, "1901-1910" 1.00000, "1911-1920" 2.00000, "1921-1930" 3.00000, "1931-1940" 4.00000, "1941-1950" 5.00000, "1951-1960" 6.00000, "1961-1970" 7.00000)
set noytics
set title "US immigration from Europe by decade\nFraction of total plotted as stacked histogram" 
set ylabel "% of total" 
set yrange [ 0.00000 : 100.000 ] noreverse nowriteback
i = 24
plot 'immigration.dat' using (100.*$2/$24):xtic(1) t 2,      for [i=3:23] '' using (100.*column(i)/column(24)) title column(i)
