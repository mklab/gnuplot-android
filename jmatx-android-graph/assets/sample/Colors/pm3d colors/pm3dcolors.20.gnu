# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'pm3dcolors.20.png'
set view map
set samples 101, 101
set isosamples 2, 2
set style data pm3d
set style function pm3d
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 2
set noytics
set noztics
set title "set palette model XYZ functions model XYZ  gray**0.35, gray**0.5, gray**0.8" 
set xrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set yrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set cbrange [ -10.0000 : 10.0000 ] noreverse nowriteback
set pm3d implicit at b
set palette positive nops_allcF maxcolors 0 gamma 1.5 color model XYZ 
set palette functions gray**0.35, gray**0.5, gray**0.8
splot x
