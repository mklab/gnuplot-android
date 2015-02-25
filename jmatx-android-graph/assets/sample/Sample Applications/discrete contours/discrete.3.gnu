# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'discrete.3.png'
set contour base
set cntrparam levels incremental -20,5,10
set title "9 incremental contours starting at -20, stepping by 5" 
splot x*y
