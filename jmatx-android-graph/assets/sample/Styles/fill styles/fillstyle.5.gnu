# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillstyle.5.png'
set boxwidth 0.5 absolute
set style fill   solid 0.25 border
set samples 25, 25
set noxtics
set noytics
set title "Filled boxes at 50% fill density" 
set yrange [ 0.00000 : 120.000 ] noreverse nowriteback
plot [-10:10] 100/(1.0+x*x) title 'distribution' with boxes
