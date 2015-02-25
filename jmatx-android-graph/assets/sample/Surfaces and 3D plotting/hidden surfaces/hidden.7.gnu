# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'hidden.7.png'
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set view 50, 30, 1, 1
set samples 20, 20
set isosamples 20, 20
set contour base
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set style data lines
set title "Hidden line removal of explicit surfaces" 
set xrange [ 0.00000 : 15.0000 ] noreverse nowriteback
set yrange [ 0.00000 : 15.0000 ] noreverse nowriteback
set zrange [ -1.00000 : 1.00000 ] noreverse nowriteback
splot "glass.dat" using 1
