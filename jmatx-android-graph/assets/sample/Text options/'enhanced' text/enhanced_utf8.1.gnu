# set terminal png transparent nocrop enhanced font arial 9 size 500,350 
# set output 'enhanced_utf8.1.png'
set format x "%.1f"
set format y "%.1f"
set label 1 "Superscripts and subscripts:" at -0.65, 0.95, 0 left norotate back textcolor lt 3 nopoint offset character 0, 0, 0
set label 3 "A_{j,k} 10^{-2}  x@^2_k    x@_0^{-3/2}y" at -0.55, 0.85, 0 left norotate back nopoint offset character 0, 0, 0
set label 5 "Space-holders:" at -0.55, 0.7, 0 left norotate back textcolor lt 3 nopoint offset character 0, 0, 0
set label 6 "<&{{/=20 B}ig}> <&{x@_0^{-3/2}y}> holds space for" at -0.45, 0.6, 0 left norotate back nopoint offset character 0, 0, 0
set label 7 "<{{/=20 B}ig}> <{x@_0^{-3/2}y}>" at -0.45, 0.5, 0 left norotate back nopoint offset character 0, 0, 0
set label 8 "Overprint\n(v should be centred over d)" at -0.9, -0.2, 0 left norotate back textcolor lt 3 nopoint offset character 0, 0, 0
set label 9 " ~{abcdefg}{0.8v}" at -0.85, -0.4, 0 left norotate back nopoint offset character 0, 0, 0
set label 10 "UTF-8 encoding does not require Symbol font:" at -0.4, 0.35, 0 left norotate back textcolor lt 3 nopoint offset character 0, 0, 0
set label 11 "{/*1.5 \342\210\253@_{/=9.6 0}^{/=12 \342\210\236}} {e^{-{\316\274}^2/2} d}{\316\274=(\317\200/2)^{1/2}}" at -0.3, 0.2, 0 left norotate back nopoint offset character 0, 0, 0
set label 21 "Left  ^{centered} \306\222(\316\261\316\262\316\263\316\264\316\265\316\266)" at 0.5, -0.1, 0 left norotate back nopoint offset character 0, 0, 0
set label 22 "Right ^{centered} \306\222(\316\261\316\262\316\263\316\264\316\265\316\266)" at 0.5, -0.2, 0 right norotate back nopoint offset character 0, 0, 0
set label 23 "Center^{centered} \306\222(\316\261\316\262\316\263\316\264\316\265\316\266)" at 0.5, -0.3, 0 centre norotate back nopoint offset character 0, 0, 0
set arrow 1 from 0.5, -0.5, 0 to 0.5, 0, 0 nohead back nofilled linetype -1 linewidth 1.000
set encoding utf8
set title "Demo of enhanced text mode using a single UTF-8 encoded font" 
set rrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
set trange [ * : * ] noreverse nowriteback  # (currently [-5.00000:5.00000] )
set xrange [ -1.00000 : 1.00000 ] noreverse nowriteback
set x2range [ * : * ] noreverse nowriteback  # (currently [-1.00000:1.00000] )
set yrange [ -0.500000 : 1.10000 ] noreverse nowriteback
set y2range [ * : * ] noreverse nowriteback  # (currently [-0.500000:1.10000] )
set cbrange [ * : * ] noreverse nowriteback  # (currently [8.98847e+307:-8.98847e+307] )
plot sin(x)**2 lt 2 lw 2 title "sin^2(x)"
