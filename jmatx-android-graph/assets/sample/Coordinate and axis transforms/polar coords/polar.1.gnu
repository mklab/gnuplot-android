# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'polar.1.png'
set clip points
unset border
set dummy t,y
set polar
set samples 160, 160
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set xtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set title "Three circles (with aspect ratio distortion)" 
set trange [ 0.00000 : 6.28319 ] noreverse nowriteback
plot .5,1,1.5
