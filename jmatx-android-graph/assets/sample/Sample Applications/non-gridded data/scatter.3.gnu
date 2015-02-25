# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'scatter.3.png'
set dgrid3d 10,10, 4
set dummy u,v
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set parametric
set style data lines
set title "Simple demo of scatter data conversion to grid data" 
set xlabel " data style lines, dgrid3d ,,4 " 
splot "hemisphr.dat"
