# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.9.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "bias to high-temperates" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
y0 = 1.06986180180309
m = -0.000969050590764801
FIT_CONVERGED = 1
FIT_NDF = 35
FIT_STDFIT = 0.00136220340861129
FIT_WSSR = 6.49459344251274e-05
plot 'lcdemo.dat', l(x)
## fit l(x) 'lcdemo.dat' using 1:2:4 via y0, m
