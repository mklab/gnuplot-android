# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'polar.8.png'
set clip points
unset border
set dummy t,y
unset key
set polar
set samples 800, 800
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set xtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set title "Butterfly" 
set trange [ 0.00000 : 37.6991 ] noreverse nowriteback
set xrange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set yrange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
butterfly(x)=exp(cos(x))-2*cos(4*x)+sin(x/12)**5
plot butterfly(t)
