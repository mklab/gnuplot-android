# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.16.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "the scattered points, fitted curve" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
high(x) = mh*(x-Tc) + dens_Tc
lowlin(x)  = ml*(x-Tc) + dens_Tc
curve(x) = b*tanh(g*(Tc-x))
density(x) = x < Tc ? curve(x)+lowlin(x) : high(x)
h(x,y) = sqrt(r*r - (abs(x-x0))**2.2 - (abs(y-y0))**1.8) + z0
y0 = 0.000608464380939049
m = -0.000943519626922319
FIT_CONVERGED = 0
FIT_NDF = 245
FIT_STDFIT = 0.0188593418504431
FIT_WSSR = 0.0871403198828091
ml = -0.00100003942456695
mh = -0.000831266464087245
dens_Tc = 1.02497044549157
Tc = 46.0899236390014
g = 3.85585054746896
b = 0.00153901241270057
FIT_LIMIT = 1e-05
r = 1.0010488957183
x0 = -0.000328091268412849
z0 = 0.00245853424533358
FIT_MAXITER = 50
splot 'hemisphr.dat' using 1:2:3, h(x,y)
## fit h(x,y) 'hemisphr.dat' using 1:2:3:(1) via r, x0, y0, z0
