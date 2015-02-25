# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.15.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "the scattered points, and the initial parameter" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
high(x) = mh*(x-Tc) + dens_Tc
lowlin(x)  = ml*(x-Tc) + dens_Tc
curve(x) = b*tanh(g*(Tc-x))
density(x) = x < Tc ? curve(x)+lowlin(x) : high(x)
h(x,y) = sqrt(r*r - (abs(x-x0))**2.2 - (abs(y-y0))**1.8) + z0
y0 = 0.2
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
FIT_LIMIT = 1e-05
r = 0.5
x0 = 0.1
z0 = 0.3
FIT_MAXITER = 50
splot 'hemisphr.dat' using 1:2:3, h(x,y)
## fit density(x) 'lcdemo.dat' via 'start.par'
