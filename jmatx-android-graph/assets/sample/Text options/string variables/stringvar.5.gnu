# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'stringvar.5.png'
set style data lines
set title "Constant string expressions as plot symbols" 
set xrange [ 250.000 : 500.000 ] noreverse nowriteback
set yrange [ * : * ] noreverse nowriteback  # (currently [0.00000:1.00000] )
beg = 2
end = 4
foo = "                                       1                                        2                                        3                                        4                                        5                                        6"
ERRNO = 0
haystack = "Tue Mar  6 11:00:35 PST 2007"
needle = ":"
S = 14
plot 'silver.dat'         u 1:2 lt -1 lw 0.5 notitle,      '' u 1:($3+$1/50.) lt 3 lw 0.5 notitle,      '' using 1:2:( ($2>($3+$1/50.)) ? "J" : "D" ) with labels font "WingDings,28"        title 'Same thing using character glyphs from WingDings font'
