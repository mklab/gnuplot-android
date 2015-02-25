# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'using.1.png'
set key inside left top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set title "Convex     November 1-7 1989    Circadian" 
set xrange [ -1.00000 : 24.0000 ] noreverse nowriteback
plot 'using.dat' using 2:4 title "Logged in" with impulses,     'using.dat' using 2:4 title "Logged in" with points
