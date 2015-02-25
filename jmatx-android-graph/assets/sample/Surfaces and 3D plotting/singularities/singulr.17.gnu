# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'singulr.17.png'
set dummy u,v
unset key
set parametric
set view 60, 120, 1, 1
set isosamples 39, 60
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set title "Klein bottle" 
set urange [ 0.00000 : 6.28319 ] noreverse nowriteback
set vrange [ 0.00000 : 6.28319 ] noreverse nowriteback
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set yrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set zrange [ -3.00000 : 3.00000 ] noreverse nowriteback
splot  (2*sin(u)*cos(v/2)-sin(2*u)*sin(v/2)+8)*cos(v),        (2*sin(u)*cos(v/2)-sin(2*u)*sin(v/2)+8)*sin(v),         2*sin(u)*sin(v/2)+sin(2*u)*cos(v/2)
