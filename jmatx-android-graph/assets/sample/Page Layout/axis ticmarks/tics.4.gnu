# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'tics.4.png'
set border 4095 front linetype -1 linewidth 1.000
set view map
set samples 50, 50
set isosamples 50, 50
set style data pm3d
set style function pm3d
set mxtics
set xtics border out scale 3,2 mirror norotate  offset character 0, 0, 0 autofreq  textcolor rgb "red" 
set ytics border in scale 3,2 mirror norotate  offset character 0, 0, 0 autofreq 
set ztics border in scale 3,2 nomirror norotate  offset character 0, 0, 0 autofreq 
set cbtics border in scale 4,2 mirror norotate  offset character 0, 0, 0 autofreq 
set title "Modification of tics settings (pm3d map with colorbar)" 
set xlabel "x" 
set xrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set ylabel "y" 
set yrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set zrange [ -0.250000 : 1.00000 ] noreverse nowriteback
set pm3d explicit at b
splot sin(sqrt(x**2+y**2))/sqrt(x**2+y**2) notitle
