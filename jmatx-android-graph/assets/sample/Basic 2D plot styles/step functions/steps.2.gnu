# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'steps.2.png'
set title "Histogram built from unsorted data by 'smooth frequency'" 
plot 'hemisphr.dat' u (floor($1*20)):(1) smooth frequency with histeps
