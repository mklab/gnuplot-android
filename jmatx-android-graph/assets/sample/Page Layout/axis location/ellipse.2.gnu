# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'ellipse.2.png'
set border 3 front linetype -1 linewidth 1.000
set format x "%.0f"
set format y "%.0f"
unset key
set object  1 ellipse center 1.5, 1, 0 size 2, 4  angle 20
set object  1 front lw 1.0 fc lt -3 fillstyle  empty border -1
set object  2 ellipse center 1.5, 1, 0 size 6, 12  angle 20
set object  2 front lw 1.0 fc lt -3 fillstyle  empty border 3
set size ratio 1 1,1
set xzeroaxis linetype 0 linewidth 1.000
set yzeroaxis linetype 0 linewidth 1.000
set zzeroaxis linetype 0 linewidth 1.000
set xtics border out scale 0.5,0.25 nomirror norotate  offset character 0, 0, 0
set xtics 1rangelimit 
set ytics border out scale 0.5,0.25 nomirror norotate  offset character 0, 0, 0
set ytics 1rangelimit 
set ztics border out scale 0.5,0.25 nomirror norotate  offset character 0, 0, 0
set cbtics border out scale 0.5,0.25 mirror norotate  offset character 0, 0, 0
set title "Example of range-limited axes and tics" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ -8.00000 : 8.00000 ] noreverse nowriteback
set x2range [ * : * ] noreverse nowriteback  # (currently [-8.00000:8.00000] )
set ylabel  offset character 0, 0, 0 font "" textcolor lt -1 rotate by 90
set y2label  offset character 0, 0, 0 font "" textcolor lt -1 rotate by 90
set yrange [ -8.00000 : 8.00000 ] noreverse nowriteback
set y2range [ * : * ] noreverse nowriteback  # (currently [-8.00000:8.00000] )
set cblabel  offset character 0, 0, 0 font "" textcolor lt -1 rotate by 90
set cbrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
A = 0.349065850398866
plot 'random.tmp' using (1.5 + $1*cos(A)-2.*$2*sin(A)):(1.0 + $1*sin(A)+2.*$2*cos(A)) with dots
