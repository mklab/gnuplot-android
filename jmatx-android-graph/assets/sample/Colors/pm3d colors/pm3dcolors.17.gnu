# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'pm3dcolors.17.png'
set view map
set samples 101, 101
set isosamples 2, 2
set style data pm3d
set style function pm3d
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 2
set noytics
set noztics
set title "set palette functions gray, gray, gray" 
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set yrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set cbrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set pm3d implicit at b
set palette functions gray, gray, gray
splot x
