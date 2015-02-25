# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'discrete.2.png'
set contour base
set cntrparam levels discrete 0,15 ,75 
set title "3 discrete contours at 0 15 & 75" 
splot x*y
