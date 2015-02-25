# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'tics.3.png'
set mxtics
set xtics border out scale 3,2 mirror norotate  offset character 0, -1, 0 autofreq  textcolor rgb "red" 
set ytics border in scale 3,2 mirror rotate by 90  offset character 2, 0, 0 autofreq 
set ztics border in scale 3,2 nomirror rotate by 45  offset character 0, 0, 0 autofreq 
set cbtics border in scale 3,2 mirror rotate by 45  offset character 0, 0, 0 autofreq 
set title "Different modification of tics settings" 
set xlabel "x" 
set xrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set ylabel "y" 
set yrange [ -0.250000 : 1.00000 ] noreverse nowriteback
plot sin(sqrt(x**2))/sqrt(x**2) notitle
