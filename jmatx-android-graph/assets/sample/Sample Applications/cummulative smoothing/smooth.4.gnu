# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'smooth.4.png'
set boxwidth 0.1 absolute
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set title "Mixed Distribution (Lognormal with shifted Gaussian)" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ * : * ] noreverse nowriteback  # (currently [0.00000:12.0000] )
set x2range [ * : * ] noreverse nowriteback  # (currently [0.0500000:10.5500] )
set yrange [ * : * ] noreverse nowriteback  # (currently [-0.400000:1.00000] )
set y2range [ * : * ] noreverse nowriteback  # (currently [-0.347042:1.000000] )
set cbrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
bin(x, s) = s*int(x/s)
plot "random-points" u 4:(0.25*rand(0)-.35) t '',      "" u (bin($4,0.1)):(10/300.) s f t 'smooth frequency' w boxes,      "" u 4:(1/300.) s cumul t 'smooth cumulative'
