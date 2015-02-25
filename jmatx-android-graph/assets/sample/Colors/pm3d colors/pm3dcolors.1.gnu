# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'pm3dcolors.1.png'
set view map
set samples 101, 101
set isosamples 2, 2
set style data pm3d
set style function pm3d
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 2
set noytics
set noztics
set title "set palette defined" 
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set cbrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set pm3d implicit at b
set palette defined ( 0 0.05 0.05 0.2, 0.1 0 0 1, 0.25 0.7 0.85 0.9,\
     0.4 0 0.75 0, 0.5 1 1 0, 0.7 1 0 0, 0.9 0.6 0.6 0.6,\
     1 0.95 0.95 0.95 )
splot x
