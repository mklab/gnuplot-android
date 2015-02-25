# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.10.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "data with experimental errors" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
y0 = 1.06865074176476
m = -0.000943519626922319
FIT_CONVERGED = 1
FIT_NDF = 35
FIT_STDFIT = 0.148686593680096
FIT_WSSR = 0.773769609906649
plot 'lcdemo.dat' using 1:2:5 with errorbars
## fit l(x) 'lcdemo.dat' using 1:2:5 via y0, m
