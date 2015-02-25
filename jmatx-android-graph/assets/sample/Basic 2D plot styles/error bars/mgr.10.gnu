# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'mgr.10.png'
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "UM1-Cell Power" 
set xlabel "Resistance [Ohm]" 
set ylabel "Power [W]" 
n(x)=1.53**2*x/(5.67+x)**2
S = 1
plot [0:50] "battery.dat" t "Power" with boxxy, n(x) t "Theory" w lines
