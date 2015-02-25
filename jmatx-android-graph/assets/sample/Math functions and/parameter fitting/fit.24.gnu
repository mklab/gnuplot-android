# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.24.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "initial parameters" 
set xlabel "Delta [degrees]" 
set ylabel "Reflectivity" 
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
f(tc)= (tanh(Y(tc)) + abs(cos(2.*tb)) * tanh(abs(Y(tc)*cos(2.*tb)))) / (Y(tc)*(1.+(cos(2.*tb))**2))
W(x) = 1./(sqrt(2.*pi)*eta) * exp( -1. * x**2 / (2.*eta**2) )
Y(tc) = tc/sin(tb) * Fhkl * r0liV
Q(tc) = (r0*Fhkl/V)**2 * (lambda**3/sin(2.*tb)) * P * f(tc)
a(x) = W(x) * Q(tc) / mu
R(x) = sinh(A*a(x)) * exp(-1.*A*(1.+a(x)))
y0 = 0.000608464380939049
m = -0.000943519626922319
FIT_CONVERGED = 1
FIT_NDF = 146
FIT_STDFIT = 60.25893070077
FIT_WSSR = 530146.254463229
ml = -0.00100003942456695
mh = -0.000831266464087245
dens_Tc = 1.02497044549157
Tc = 46.0899236390014
g = 3.85585054746896
b = 0.00153901241270057
FIT_LIMIT = 1e-10
r = 1.0010488957183
x0 = -0.000328091268412849
z0 = 0.00245853424533358
FIT_MAXITER = 0
rho = 1000.0
phi0 = -0.16189629365459
c11 = 5.34014833463093
c33 = 12.4010700704245
c44 = 1
c13 = 4
mu = 0.113046900551349
t0 = 0.18
tb = 0.199278608299778
A = 0.020759275611633
P = 0.924693446208538
Fhkl = 3.42318325539711
r0 = 2.81794092e-13
lambda = 7.09338062818239e-09
V = 1.62253546981499e-23
r0liV = 123.194394853936
eta = 0.00012
tc = 0.0018
plot 'moli3.dat' w e, R(x)
## fit f(x,y) 'soundvel.dat' using 1:-2:2:(1) via c33, c11, phi0
