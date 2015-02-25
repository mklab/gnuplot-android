# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'scatter.8.png'
set dgrid3d 10,10, 4
set dummy u,v
set parametric
set contour base
set style data lines
set title "Simple demo of scatter data conversion to grid data" 
set xlabel "data style lines, dgrid3d ,,4" 
splot "scatter2.dat"
