# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'smooth.2.png'
set boxwidth 0.05 absolute
set key inside left top vertical Right noreverse enhanced autotitles nobox
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set title "Normal Distribution" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ * : * ] noreverse nowriteback  # (currently [-3.00000:3.00000] )
set x2range [ * : * ] noreverse nowriteback  # (currently [-2.58507:2.62500] )
set yrange [ * : * ] noreverse nowriteback  # (currently [-0.400000:1.00000] )
set y2range [ * : * ] noreverse nowriteback  # (currently [-0.348254:1.000000] )
set cbrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
bin(x, s) = s*int(x/s)
plot "random-points" u 2:(0.25*rand(0)-.35) t '',      "" u (bin($2,0.05)):(20/300.) s f t 'smooth frequency' w boxes,      "" u 2:(1/300.) s cumul t 'smooth cumulative'
