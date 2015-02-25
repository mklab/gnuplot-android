# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'singulr.15.png'
set dummy u,v
set parametric
set isosamples 31, 11
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set title "Moebius strip" 
set urange [ 0.00000 : 6.28319 ] noreverse nowriteback
set vrange [ -0.250000 : 0.250000 ] noreverse nowriteback
splot (2-v*sin(u/2))*sin(u),(2-v*sin(u/2))*cos(u),v*cos(u/2)
