# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'singulr.5.png'
set dummy u,v
set parametric
set isosamples 21, 21
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set title "Real part of complex cube root function" 
set urange [ -3.00000 : 3.00000 ] noreverse nowriteback
set vrange [ -3.00000 : 3.00000 ] noreverse nowriteback
splot u**3-3*u*v**2,3*u**2*v-v**3,u
