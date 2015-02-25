# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'key.2.png'
set key inside right bottom horizontal Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "Key (ins horiz bot right)" 
plot x,-x
