# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'hidden.2.png'
set samples 20, 20
set isosamples 20, 20
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set title "Hidden line removal of explicit surfaces" 
set xrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set yrange [ -1.00000 : 1.00000 ] noreverse nowriteback
splot x*y / (x**2 + y**2 + 0.1)
