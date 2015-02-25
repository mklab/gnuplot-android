# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'key.3.png'
set key outside right bottom vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "Key (out vert bot right)" 
plot x,-x
