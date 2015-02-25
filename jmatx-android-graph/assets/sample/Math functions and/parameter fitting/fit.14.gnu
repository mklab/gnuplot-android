# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.14.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "fitted to realistic model function" 
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
FIT_NDF = 31
FIT_STDFIT = 5.38882119850527e-05
FIT_WSSR = 9.00221211193253e-08
ml = -0.00100003942456695
mh = -0.000831266464087245
dens_Tc = 1.02497044549157
Tc = 46.0899236390014
g = 3.85585054746896
b = 0.00153901241270057
FIT_LIMIT = 1e-10
plot 'lcdemo.dat', density(x)
## fit density(x) 'lcdemo.dat' via 'start.par'
