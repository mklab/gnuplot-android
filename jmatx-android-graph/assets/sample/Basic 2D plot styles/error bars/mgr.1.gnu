# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'mgr.1.png'
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set samples 50, 50
set title "Bragg reflection -- Peak only" 
set xlabel "Angle (deg)" 
set ylabel "Amplitude" 
plot "big_peak.dat" title "Rate" with errorbars,                  "" smooth csplines t "Rate"
