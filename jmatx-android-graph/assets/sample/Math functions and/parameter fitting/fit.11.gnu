# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.11.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "fit weighted by experimental errors" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
high(x) = mh*(x-Tc) + dens_Tc
lowlin(x)  = ml*(x-Tc) + dens_Tc
curve(x) = b*tanh(g*(Tc-x))
density(x) = x < Tc ? curve(x)+lowlin(x) : high(x)
y0 = 1.06865074176476
m = -0.000943519626922319
FIT_CONVERGED = 1
FIT_NDF = 35
FIT_STDFIT = 0.148686593680096
FIT_WSSR = 0.773769609906649
ml = -0.0001
mh = -0.0001
dens_Tc = 1.02
Tc = 45
g = 1
b = 0.1
plot 'lcdemo.dat' using 1:2:5 with errorbars, l(x)
## fit l(x) 'lcdemo.dat' using 1:2:5 via y0, m
