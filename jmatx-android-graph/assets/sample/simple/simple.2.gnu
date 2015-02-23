# set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
# set output 'simple.2.png'
plot [-pi/2:pi] cos(x),-(sin(x) > sin(x+1) ? sin(x) : sin(x+1))
