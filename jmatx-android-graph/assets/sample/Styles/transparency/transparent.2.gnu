# set terminal pngcairo  enhanced font "arial,10" size 420, 300 
# set output 'transparent.2.png'
set clip two
set style fill  transparent solid 0.50 noborder
set key title "Gaussian Distribution"
set key inside left top vertical Left reverse enhanced autotitles nobox
set key noinvert samplen 1 spacing 1 width 0 height 0 
set style function filledcurves y1=0
set title "Transparent filled curves" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ -5.00000 : 5.00000 ] noreverse nowriteback
set x2range [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set yrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set y2range [ * : * ] noreverse nowriteback  # (currently [0.00000:1.00000] )
set cbrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
unset colorbox
Gauss(x,mu,sigma) = 1./(sigma*sqrt(2*pi)) * exp( -(x-mu)**2 / (2*sigma**2) )
d1(x) = Gauss(x, 0.5, 0.5)
d2(x) = Gauss(x,  2.,  1.)
d3(x) = Gauss(x, -1.,  2.)
GPFUN_Gauss = "Gauss(x,mu,sigma) = 1./(sigma*sqrt(2*pi)) * exp( -(x-mu)**2 / (2*sigma**2) )"
GPFUN_d1 = "d1(x) = Gauss(x, 0.5, 0.5)"
GPFUN_d2 = "d2(x) = Gauss(x,  2.,  1.)"
GPFUN_d3 = "d3(x) = Gauss(x, -1.,  2.)"
plot d1(x) fs solid 1.0 lc rgb "forest-green" title "μ =  0.5 σ = 0.5",      d2(x) lc rgb "gold" title "μ =  2.0 σ = 1.0",      d3(x) lc rgb "red" title "μ = -1.0 σ = 2.0"
