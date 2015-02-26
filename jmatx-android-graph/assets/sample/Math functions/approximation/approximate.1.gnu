# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'approximate.1.png'
set style fill   solid 0.40 noborder
set format y "%.1f"
set key inside center top vertical Left reverse enhanced autotitles nobox
set samples 500, 500
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0  font "Symbol"
set xtics   ("-p" -3.14159, "-p/2" -1.57080, 0.00000, "p/2" 1.57080, "p" 3.14159)
set title "Polynomial approximation of sin(x)" 
set xrange [ -3.20000 : 3.20000 ] noreverse nowriteback
approx_1(x) = x - x**3/6
approx_2(x) = x - x**3/6 + x**5/120
approx_3(x) = x - x**3/6 + x**5/120 - x**7/5040
label1 = "x - x^3/3!"
label2 = "x - x^3/3! + x^5/5!"
label3 = "x - x^3/3! + x^5/5! - x^7/7!"
plot '+' using 1:(sin($1)):(approx_1($1)) with filledcurve title label1 lt 3,      '+' using 1:(sin($1)):(approx_2($1)) with filledcurve title label2 lt 2,      '+' using 1:(sin($1)):(approx_3($1)) with filledcurve title label3 lt 1,      sin(x) with lines lw 1 lc rgb "black"
