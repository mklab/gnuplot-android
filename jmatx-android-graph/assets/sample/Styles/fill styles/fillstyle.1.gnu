# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillstyle.1.png'
set samples 25, 25
set noxtics
set noytics
set title "A demonstration of boxes with default properties" 
set yrange [ 0.00000 : 120.000 ] noreverse nowriteback
plot [-10:10] 100/(1.0+x*x) title 'distribution' with boxes
