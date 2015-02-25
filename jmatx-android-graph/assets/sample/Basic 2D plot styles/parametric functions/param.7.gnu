# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'param.7.png'
set dummy t,y
set key bmargin center horizontal Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set parametric
set samples 160, 160
set trange [ 0.000100000 : 31.4159 ] noreverse nowriteback
set xrange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set yrange [ -1.50000 : 1.50000 ] noreverse nowriteback
plot sin(t)/t,cos(t)/t
