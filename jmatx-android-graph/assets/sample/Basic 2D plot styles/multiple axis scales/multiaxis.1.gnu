# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'multiaxis.1.png'
set dummy jw,y
set grid nopolar
set grid xtics nomxtics noytics nomytics noztics nomztics \
 nox2tics nomx2tics y2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key inside center top vertical Right noreverse enhanced autotitles nobox
set logscale x 10
set logscale y 10
set logscale x2 10
set xtics border out scale 1,0.5 mirror norotate  offset character 0, 0, 0 autofreq 
set ytics border out scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ztics border out scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set y2tics border out scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set cbtics border out scale 1,0.5 mirror norotate  offset character 0, 0, 0 autofreq 
set title "Transistor Amplitude and Phase Frequency Response" 
set xlabel "jw (radians)" 
set xrange [ 1.10000 : 90000.0 ] noreverse nowriteback
set x2range [ 1.10000 : 90000.0 ] noreverse nowriteback
set ylabel "magnitude of A(jw)" 
set y2label "Phase of A(jw) (degrees)" 
A(jw) = ({0,1}*jw/({0,1}*jw+p1)) * (1/(1+{0,1}*jw/p2))
p1 = 10
p2 = 10000
plot abs(A(jw)) axes x1y1, 180./pi*arg(A(jw)) axes x2y2
