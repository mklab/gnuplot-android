# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'rainbow.2.png'
set style line 1  linetype 1 linecolor palette fraction 0.00 linewidth 3.000 pointtype 1 pointsize default
set style line 2  linetype 2 linecolor palette fraction 0.10 linewidth 2.000 pointtype 2 pointsize default
set style line 3  linetype 3 linecolor palette fraction 0.16 linewidth 3.000 pointtype 3 pointsize default
set style line 4  linetype 4 linecolor palette fraction 0.33 linewidth 2.000 pointtype 4 pointsize default
set style line 5  linetype 5 linecolor palette fraction 0.50 linewidth 3.000 pointtype 5 pointsize default
set style line 6  linetype 6 linecolor palette fraction 0.66 linewidth 2.000 pointtype 6 pointsize default
set style line 7  linetype 7 linecolor palette fraction 0.79 linewidth 3.000 pointtype 7 pointsize default
set macros
set noxtics
set noytics
set title "Terminal-independent palette colors in 2D\nImplemented using command line macros referring to a fixed HSV palette" 
set xlabel "HSV color wheel" 
set xrange [ -0.500000 : 3.50000 ] noreverse nowriteback
set yrange [ -1.00000 : 1.40000 ] noreverse nowriteback
set cbrange [ 0.00000 : 1.00000 ] noreverse nowriteback
set bmargin  7
set palette positive nops_allcF maxcolors 0 gamma 1.5 color model HSV 
set palette defined ( 0 0 1 1, 1 1 1 1 )
set colorbox user
set colorbox horizontal origin screen 0.1, 0.08, 0 size screen 0.8, 0.05, 0 bdefault
red = "lt pal frac 0"
orange = "lt pal frac 0.10"
yellow = "lt pal frac 0.16"
green = "lt pal frac 0.33"
cyan = "lt pal frac 0.5"
blue = "lt pal frac 0.66"
violet = "lt pal frac 0.79"
magenta = "lt pal frac 0.83"
black = "lt -1"
plot cos(x)     ls 1 title 'red',        cos(x-.2)  ls 2 title 'orange',     cos(x-.4)  ls 3 title 'yellow',     cos(x-.6)  ls 4 title 'green',      cos(x-.8)  ls 5 title 'cyan',       cos(x-1.)  ls 6 title 'blue',       cos(x-1.2) ls 7 title 'violet'
