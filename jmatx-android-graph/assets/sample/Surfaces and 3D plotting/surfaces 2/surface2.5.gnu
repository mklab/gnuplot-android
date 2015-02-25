# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'surface2.5.png'
set dummy u,v
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set parametric
set view 45, 50, 0.7, 1.4
set isosamples 50, 10
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set ticslevel 0
set title "Parametric Hexagon" 
set urange [ -1.30000 : 1.30000 ] noreverse nowriteback
set vrange [ 0.00000 : 6.28319 ] noreverse nowriteback
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.00000:1.00000] )
splot cos(v)**3*cos(u)**3,sin(v)**3*cos(u)**3,sin(u)**3
