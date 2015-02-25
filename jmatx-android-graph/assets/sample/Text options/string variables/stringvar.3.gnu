# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'stringvar.3.png'
set title "String-valued expression in using spec" 
set xrange [ 300.000 : 400.000 ] noreverse nowriteback
beg = 2
end = 4
foo = "                                       1                                        2                                        3                                        4                                        5                                        6"
ERRNO = 0
haystack = "Tue Mar  6 11:00:35 PST 2007"
needle = ":"
S = 14
plot 'silver.dat' using 1:2 with linespoints notitle,      '' using 1:2:(sprintf("[%.0f,%.0f]",$1,$2)) with labels
