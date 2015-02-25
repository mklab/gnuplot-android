# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'hidden.1.png'
set samples 20, 20
set isosamples 20, 20
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set title "Hidden line removal of explicit surfaces" 
set xrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set yrange [ -2.00000 : 2.00000 ] noreverse nowriteback
splot 1 / (x*x + y*y + 1)
