# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'singulr.10.png'
set dummy u,v
set parametric
set view 60, 210, 1, 1
set isosamples 31, 31
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set title "Real part of complex 4th root function (different view)" 
set urange [ -1.00000 : 1.00000 ] noreverse nowriteback
set vrange [ -1.00000 : 1.00000 ] noreverse nowriteback
splot u**4-6*u**2*v**2+v**4,4*u**3*v-4*u*v**3,u
