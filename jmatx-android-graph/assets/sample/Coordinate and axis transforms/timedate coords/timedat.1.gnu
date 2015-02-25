# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'timedat.1.png'
set xdata time
set timefmt x "%d/%m/%y\t%H%M"
set timefmt y "%d/%m/%y\t%H%M"
set timefmt z "%d/%m/%y\t%H%M"
set timefmt x2 "%d/%m/%y\t%H%M"
set timefmt y2 "%d/%m/%y\t%H%M"
set timefmt cb "%d/%m/%y\t%H%M"
set format x "%d/%m\n%H:%M"
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set key inside left top vertical Right noreverse enhanced autotitles nobox
set style data fsteps
set title "Fsteps plot\nwith date and time as x-values" 
set trange [ * : * ] noreverse nowriteback  # (currently ["31/12/99\t2359":"01/01/00\t0000"] )
set urange [ * : * ] noreverse nowriteback  # (currently ["31/12/99\t2359":"01/01/00\t0000"] )
set xlabel "Date\nTime" 
set xrange [ "01/06/93\t0000" : "01/11/93\t0000" ] noreverse nowriteback
set ylabel "Concentration\nmg/l" 
set yrange [ 0.00000 : * ] noreverse nowriteback  # (currently [:10.0000] )
plot 'timedat.dat' using 1:3 t '',      'timedat.dat' using 1:3 t 'Total P' with points,      'timedat.dat' using 1:4 t '',      'timedat.dat' using 1:4 t 'PO4' with points
