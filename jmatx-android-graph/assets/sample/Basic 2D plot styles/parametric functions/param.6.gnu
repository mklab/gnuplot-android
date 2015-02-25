# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'param.6.png'
set dummy t,y
set key bmargin center horizontal Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set parametric
set samples 160, 160
set trange [ 1.00000e-05 : 3.00000 ] noreverse nowriteback
set xrange [ -5.00000 : 5.00000 ] noreverse nowriteback
set yrange [ -5.00000 : 5.00000 ] noreverse nowriteback
plot t,log(t),-t,log(t),sin(t),t**2,-sin(t),t**2
