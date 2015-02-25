# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'fit.3.png'
set key bmargin center horizontal Right noreverse enhanced autotitles nobox
set title "all fit params set to 0" 
set xlabel "Temperature T  [deg Cels.]" 
set ylabel "Density [g/cm3]" 
l(x) = y0 + m*x
y0 = 0.0
m = 0.0
plot 'lcdemo.dat', l(x)
