# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'binary.1.png'
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set style data lines
set ticslevel 0
set title "Hidden line removal of explicit binary surfaces" 
set xrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set yrange [ -2.00000 : 2.00000 ] noreverse nowriteback
splot "binary1" binary
