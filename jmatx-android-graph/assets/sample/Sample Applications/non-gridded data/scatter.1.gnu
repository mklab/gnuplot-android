# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'scatter.1.png'
set dummy u,v
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set parametric
set title "Simple demo of scatter data conversion to grid data" 
set xlabel "data style point - no dgrid" 
splot "hemisphr.dat"
