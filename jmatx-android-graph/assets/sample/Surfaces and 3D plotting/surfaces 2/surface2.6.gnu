# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'surface2.6.png'
set dummy u,v
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set parametric
set view 45, 50, 1, 1
set isosamples 100, 20
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set ticslevel 0
set title "Parametric Helix" 
set urange [ 0.00000 : 31.4159 ] noreverse nowriteback
set vrange [ 0.00000 : 6.28319 ] noreverse nowriteback
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.00000:1.00000] )
splot (1-0.1*cos(v))*cos(u),(1-0.1*cos(v))*sin(u),0.1*(sin(v)+u/1.7-10)
