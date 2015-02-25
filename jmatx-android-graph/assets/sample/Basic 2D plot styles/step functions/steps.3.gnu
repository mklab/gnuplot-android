# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'steps.3.png'
set title "Normal Distribution Function" 
plot [-3:3][0:1] norm(x)
