# set terminal png truecolor nocrop enhanced font arial 9 size 512,384 
# set output 'transparent_solids.2.png'
unset border
set style fill  transparent solid 0.65 border
set dummy u,v
unset key
set label 1 "Kuen's Surface" at screen 0.6, 0.9, 0 left norotate font "frscript,25" back nopoint offset character 0, 0, 0
set style line 2  linetype 2 linecolor rgb "#a0a0f0"  linewidth 0.500 pointtype 2 pointsize default pointinterval 0
set style line 3  linetype -1 linewidth 0.500 pointtype 3 pointsize default pointinterval 0
set object  1 rect from screen 0, 0, 0 to screen 1, 1, 0
set object  1 behind lw 1.0 fc  rgb "gray"  fillstyle   solid 1.00 border lt -1
set parametric
set view 122, 357, 1.35, 1.08  
set isosamples 51, 51
set hidden3d back offset 1 trianglepattern 3 undefined 1 altdiagonal bentover
set ticslevel 0
set noxtics
set noytics
set noztics
set urange [ -4.50000 : 4.50000 ] noreverse nowriteback
set vrange [ 0.0500000 : 3.09159 ] noreverse nowriteback
set xrange [ * : * ] noreverse nowriteback  # (currently [-1.02370:2.00000] )
set yrange [ * : * ] noreverse nowriteback  # (currently [-1.06007:1.06007] )
set zrange [ * : * ] noreverse nowriteback  # (currently [-1.78735:1.78735] )
set cbrange [ * : * ] noreverse nowriteback  # (currently [-2.00000:2.00000] )
set lmargin at screen 0.1
set bmargin at screen 0.1
set rmargin at screen 0.9
set tmargin at screen 0.9
set pm3d depthorder
set pm3d interpolate 1,1 flush begin noftriangles hidden3d 3 corners2color mean
unset colorbox
x(u,v) = 2.*a*(cos(u)+u*sin(u))*sin(v) / (1+u**2.*(sin(v))**2)
y(u,v) = 2.*a*(sin(u)-u*cos(u))*sin(v) / (1+u**2.*(sin(v))**2)
z(u,v) = a*log(tan(v/2.))+2.*cos(v)/(1+u**2.*(sin(v))**2)
a = 1.0
GPFUN_x = "x(u,v) = 2.*a*(cos(u)+u*sin(u))*sin(v) / (1+u**2.*(sin(v))**2)"
GPFUN_y = "y(u,v) = 2.*a*(sin(u)-u*cos(u))*sin(v) / (1+u**2.*(sin(v))**2)"
GPFUN_z = "z(u,v) = a*log(tan(v/2.))+2.*cos(v)/(1+u**2.*(sin(v))**2)"
splot x(u,v), y(u,v), z(u,v) with pm3d
