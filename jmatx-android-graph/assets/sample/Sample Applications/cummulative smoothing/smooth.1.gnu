# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'smooth.1.png'
set boxwidth 0.05 absolute
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set title "Uniform Distribution" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ * : * ] noreverse nowriteback  # (currently [-0.100000:1.10000] )
set x2range [ * : * ] noreverse nowriteback  # (currently [-0.100000:1.10000] )
set yrange [ * : * ] noreverse nowriteback  # (currently [-0.400000:1.50000] )
set y2range [ * : * ] noreverse nowriteback  # (currently [-0.400000:1.50000] )
set cbrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
bin(x, s) = s*int(x/s)
plot [-0.1:1.1][-0.4:1.5] "random-points" u 1:(0.25*rand(0)-.35) t '',      "" u (bin($1,0.05)):(20/300.) s f t 'smooth frequency' w boxes,      "" u 1:(1/300.) s cumul t 'smooth cumulative'
