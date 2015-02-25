# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'surface2.7.png'
set dummy u,v
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set parametric
set view 50, 30, 1, 1
set isosamples 40, 20
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set ticslevel 0
set title "Parametric Shell (clipped to limited z range)" 
set urange [ 0.00000 : 6.28319 ] noreverse nowriteback
set vrange [ 0.00000 : 6.28319 ] noreverse nowriteback
set zrange [ -3.00000 : 1.50000 ] noreverse nowriteback
splot cos(u)*u*(1+cos(v)/2),sin(v)*u/2,sin(u)*u*(1+cos(v)/2)
