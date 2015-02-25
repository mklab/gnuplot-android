# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'mgr.7.png'
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set samples 300, 300
set title "Ag 108 decay data" 
set xlabel "Time (sec)" 
set ylabel "Rate" 
S = 1
plot "silver.dat" t "experimental" w errorb,               "" smooth sbezier t "bezier"
