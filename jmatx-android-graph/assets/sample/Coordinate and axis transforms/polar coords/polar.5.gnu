# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'polar.5.png'
set clip points
unset border
set dummy t,y
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set polar
set samples 160, 160
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set xtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set trange [ 0.00000 : 6.28319 ] noreverse nowriteback
set xrange [ -5.00000 : 5.00000 ] noreverse nowriteback
set yrange [ -5.00000 : 5.00000 ] noreverse nowriteback
plot t/cos(3*t)
