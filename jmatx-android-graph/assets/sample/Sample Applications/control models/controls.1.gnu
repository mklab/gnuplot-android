# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'controls.1.png'
set dummy t,y
set key inside right top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set samples 50, 50
set xrange [ 0.00000 : 13.0000 ] noreverse nowriteback
damp(t) = exp(-s*wn*t)/sqrt(1.0-s*s)
per(t) = sin(wn*sqrt(1.0-s**2)*t - atan(-sqrt(1.0-s**2)/s))
c(t) = 1-damp(t)*per(t)
s = 2.0
wn = 1.0
plot s=.1,c(t),s=.3,c(t),s=.5,c(t),s=.7,c(t),s=.9,c(t),s=1.0,c(t),s=1.5,c(t),s=2.0,c(t)
