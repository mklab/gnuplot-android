# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'random.2.png'
set dummy u,v
set format x "%3.2f"
set format y "%3.2f"
set format z "%3.2f"
unset key
set parametric
set samples 50, 50
set style function dots
set title "Lattice test for random numbers" 
set xlabel "rand(n) ->" 
set xrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set ylabel "rand(n + 1) ->" 
set yrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set zlabel "rand(n + 2) ->" 
set zrange [ 0.00000 : 1.00000 ] noreverse nowriteback
splot rand(0), rand(0), rand(0)
