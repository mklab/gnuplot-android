# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'param.4.png'
set dummy t,y
set key bmargin center horizontal Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set parametric
set samples 160, 160
set xrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set yrange [ -3.00000 : 3.00000 ] noreverse nowriteback
plot -t,t,cos(t),cos(2*t),2*cos(t),sin(t),-cosh(t),sinh(t)
