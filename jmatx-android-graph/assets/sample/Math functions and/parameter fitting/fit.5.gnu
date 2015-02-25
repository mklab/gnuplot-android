# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.5.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "unweighted fit" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
y0 = 1.08016573988928
m = -0.00118151895103669
FIT_CONVERGED = 1
FIT_NDF = 35
FIT_STDFIT = 0.00043330125502521
FIT_WSSR = 6.57124921622476e-06
plot 'lcdemo.dat', l(x)
## fit l(x) 'lcdemo.dat' via y0, m
