# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.7.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "fit weighted towards low temperatures" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
y0 = 1.07274734680908
m = -0.00100355638059072
FIT_CONVERGED = 1
FIT_NDF = 35
FIT_STDFIT = 4.3703390819187e-05
FIT_WSSR = 6.68495229183109e-08
plot 'lcdemo.dat', l(x)
## fit l(x) 'lcdemo.dat' using 1:2:3 via y0, m
