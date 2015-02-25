# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'layout.1.png'
set boxwidth 0.8 absolute
set style fill   solid 1.00 border
set format y "    "
set key inside right top vertical Right noreverse enhanced autotitles columnhead nobox
set style histogram columnstacked title  offset character 0, 0, 0
set xtics border in scale 0,0 mirror norotate  offset character 0, 0, 0 
set xtics   ("Austria" 0.500000, "France" 1.50000, "Germany" 2.50000, "Italy" 3.50000)
set ytics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set ztics border in scale 0,0 nomirror norotate  offset character 0, 0, 0 autofreq 
set cbtics border in scale 0,0 mirror norotate  offset character 0, 0, 0 autofreq 
set title "Plot 3" 
plot 'immigration.dat' using 2 with histograms,      '' using 7  with histograms ,      '' using 8  with histograms ,      '' using 11 with histograms
