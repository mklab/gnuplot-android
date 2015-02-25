# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'datastrings.8.png'
unset border
set view 42, 100, 1.3, 1
set datafile separator "	"
set noxtics
set noytics
set noztics
set title "Gliese star catalog - 7 parsec neighborhood centered on Earth" 
set cblabel "Luminosity" 
set cblabel  offset character 0, 1, 0 font "" textcolor lt -1 norotate
set cbrange [ 0.00000 : 100.000 ] noreverse nowriteback
set pm3d implicit at s
set colorbox user
set colorbox horizontal origin screen 0.1, 0.1, 0 size screen 0.8, 0.04, 0 bdefault
splot 'nearmap.csv' using 5:6:7:11 with points pt 6 ps 2 pal notitle,       ''            using 5:6:7:11 with points pt 7 ps 1 pal notitle,       '' using 5:6:7:2 with labels font "Helvetica,9" left 	   point pt 0 offset 1,0 notitle
