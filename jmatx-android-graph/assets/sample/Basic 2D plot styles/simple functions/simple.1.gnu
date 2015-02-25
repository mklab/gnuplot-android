# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'simple.1.png'
set key inside left top vertical Right noreverse enhanced autotitles box linetype -1 linewidth 1.000
set samples 50, 50
plot [-10:10] sin(x),atan(x),cos(atan(x))
