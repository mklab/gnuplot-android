# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'surface2.1.png'
set dummy u,v
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set parametric
set view 45, 50, 1, 1
set isosamples 50, 10
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set ztics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 -1.00000,0.25,1.00000
set title "Parametric Sphere" 
set urange [ -1.57080 : 1.57080 ] noreverse nowriteback
set vrange [ 0.00000 : 6.28319 ] noreverse nowriteback
splot cos(u)*cos(v),cos(u)*sin(v),sin(u)
