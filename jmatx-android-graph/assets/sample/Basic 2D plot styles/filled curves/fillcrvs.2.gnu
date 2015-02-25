# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillcrvs.2.png'
set key outside right top vertical Right noreverse enhanced autotitles nobox
set title "Intersection of two parabolas" 
plot x*x with filledcurves, 50-x*x with filledcurves, x*x with line lt 1
