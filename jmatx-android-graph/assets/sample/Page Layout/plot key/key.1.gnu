# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'key.1.png'
set key inside right bottom vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "Key (ins vert bot right)" 
plot x,-x
