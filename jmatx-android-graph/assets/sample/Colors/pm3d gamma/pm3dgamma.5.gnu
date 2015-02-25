# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'pm3dgamma.5.png'
set view map
set samples 101, 101
set isosamples 2, 2
set style data pm3d
set style function pm3d
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 2
set noytics
set noztics
set title "gamma = 1.75" 
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set cbrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set pm3d implicit at b
set palette positive nops_allcF maxcolors 0 gamma 1.75 gray
splot x
