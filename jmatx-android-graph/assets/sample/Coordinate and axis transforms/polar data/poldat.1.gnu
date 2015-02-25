# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'poldat.1.png'
set clip points
set dummy t,y
unset key
set parametric
set size ratio 1 1,1
set xtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set ytics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autofreq 
set title "Primitive Smith Chart" 
set timestamp "%a %b %d %H:%M:%S %Y" 
set rrange [ 0.00000 : 10.0000 ] noreverse nowriteback
set trange [ -3.14159 : 3.14159 ] noreverse nowriteback
set xlabel "Impedance or Admittance Coordinates" 
set xrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set yrange [ -1.00000 : 1.00000 ] noreverse nowriteback
tv(t,r) = sin(t)/(1+r)
tu(t,r) = (cos(t) +r)/(1+r)
cu(t,x) = 1 + cos(t)/x
cv(t,x) = (1+ sin(t))/x
plot cu(t,.1),cv(t,.1),cu(t,.1),-cv(t,.1),cu(t,1),cv(t,1),cu(t,1),-cv(t,1),cu(t,10),cv(t,10),cu(t,10),-cv(t,10),tu(t,.1),tv(t,.1),tu(t,.5),tv(t,.5),tu(t,1),tv(t,1),tu(t,5),tv(t,5),tu(t,10),tv(t,10),cu(t,.5),cv(t,.5),cu(t,.5),-cv(t,.5),tu(t,0),tv(t,0)
