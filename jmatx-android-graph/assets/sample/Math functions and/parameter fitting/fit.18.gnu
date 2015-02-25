# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.18.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "sound data, and model with initial parameters" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
high(x) = mh*(x-Tc) + dens_Tc
lowlin(x)  = ml*(x-Tc) + dens_Tc
curve(x) = b*tanh(g*(Tc-x))
density(x) = x < Tc ? curve(x)+lowlin(x) : high(x)
h(x,y) = sqrt(r*r - (abs(x-x0))**2.2 - (abs(y-y0))**1.8) + z0
phi(x)	    = (x - phi0)/360.0*2.0*pi
main(x)     = c11*sin(phi(x))**2 + c33*cos(phi(x))**2 + c44
mixed(x)    = sqrt( ((c11-c44)*sin(phi(x))**2				                    +(c44-c33)*cos(phi(x))**2)**2                     +(2.0*(c13+c44)*sin(phi(x))*cos(phi(x)))**2 )
vlong(x)    = sqrt(1.0/2.0/rho*1e9*(main(x) + mixed(x)))
vtrans(x)   = sqrt(1.0/2.0/rho*1e9*(main(x) - mixed(x)))
f(x,y)	=  y==1  ?  vlong(x)  :  vtrans(x)
y0 = 0.000608464380939049
m = -0.000943519626922319
FIT_CONVERGED = 1
FIT_NDF = 144
FIT_STDFIT = 23.3781402254247
FIT_WSSR = 78701.3914175457
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
FIT_MAXITER = 0
rho = 1000.0
phi0 = -0.399583044085545
c11 = 5.49004711383962
c33 = 12.5887439344521
c44 = 0.701948193955634
c13 = 3.99874625029901
plot 'soundvel.dat', vlong(x), vtrans(x)
## fit f(x,y) 'soundvel.dat' using 1:-2:2:(1) via 'sound.par'
