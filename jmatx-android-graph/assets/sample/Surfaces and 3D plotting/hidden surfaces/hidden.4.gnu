# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'hidden.4.png'
set border 4095 front linetype -1 linewidth 1.000
set grid nopolar
set grid noxtics nomxtics noytics nomytics ztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
set samples 20, 20
set isosamples 20, 20
set hidden3d offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set ztics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 -1.00000,0.5,1.00000
set title "Hidden line removal of explicit surfaces" 
set xrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set yrange [ -3.00000 : 3.00000 ] noreverse nowriteback
set zrange [ -1.00000 : 1.00000 ] noreverse nowriteback
splot sin(x) * cos(y)
