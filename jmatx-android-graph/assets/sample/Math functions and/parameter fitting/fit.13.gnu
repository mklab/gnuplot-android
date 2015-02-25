# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.13.png'
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
FIT_STDFIT = 5.38883632121369e-05
FIT_WSSR = 9.00226263801789e-08
ml = -0.00100003889377453
mh = -0.00083126648503697
dens_Tc = 1.02497044445617
Tc = 46.0899249399587
g = 3.85580365358305
b = 0.00153901455759968
plot 'lcdemo.dat', density(x)
## fit density(x) 'lcdemo.dat' via 'start.par'
