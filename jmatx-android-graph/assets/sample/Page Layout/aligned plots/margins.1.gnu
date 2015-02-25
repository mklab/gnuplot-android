# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'margins.1.png'
unset key
set view map
set samples 50, 50
set isosamples 50, 50
set noytics
set urange [ -15.0000 : 15.0000 ] noreverse nowriteback
set vrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set xrange [ -15.0000 : 15.0000 ] noreverse nowriteback
set yrange [ * : * ] noreverse nowriteback  # (currently [-15.0000:15.0000] )
set zrange [ -0.250000 : 1.00000 ] noreverse nowriteback
set lmargin screen 0.2
set bmargin screen 0.1
set rmargin screen 0.85
set tmargin screen 0.25
f(h) = sin(sqrt(h**2))/sqrt(h**2)
y = 0
plot sin(sqrt(x**2+y**2))/sqrt(x**2+y**2)
