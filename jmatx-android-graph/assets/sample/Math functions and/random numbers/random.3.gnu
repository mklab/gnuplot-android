# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'random.3.png'
set dummy u,v
unset key
set parametric
set view 68, 28, 1, 1
set samples 50, 50
set isosamples 30, 30
set contour base
unset clabel
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set cntrparam levels discrete 0.1
set style function dots
set ticslevel 0
set ztics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 0.00000,0.05
set title "50 random samples from a 2D Gaussian PDF with\nunit variance, zero mean and no dependence" 
set urange [ -3.00000 : 3.00000 ] noreverse nowriteback
set vrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set xrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set yrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set zrange [ -0.200000 : 0.200000 ] noreverse nowriteback
tstring(n) = sprintf("%d random samples from a 2D Gaussian PDF with\nunit variance, zero mean and no dependence", n)
nsamp = 50
splot u,v,( 1/(2*pi) * exp(-0.5 * (u**2 + v**2)) ) with line lc rgb "black",    "random.tmp" using 1:2:(-0.2) with points pointtype 7 lc rgb "black"
