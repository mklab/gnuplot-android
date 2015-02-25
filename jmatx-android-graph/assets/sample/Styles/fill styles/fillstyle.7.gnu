# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fillstyle.7.png'
set boxwidth 0.5 absolute
set style fill   pattern 0 border
set samples 11, 11
set noxtics
set noytics
set title "A demonstration of boxes in mono with style fill pattern" 
set yrange [ 0.00000 : 120.000 ] noreverse nowriteback
plot [-2.5:4.5] 100/(1.0+x*x) title 'pattern 0' with boxes lt -1,                  80/(1.0+x*x) title 'pattern 1' with boxes lt -1,                  40/(1.0+x*x) title 'pattern 2' with boxes lt -1,                  20/(1.0+x*x) title 'pattern 3' with boxes lt -1
