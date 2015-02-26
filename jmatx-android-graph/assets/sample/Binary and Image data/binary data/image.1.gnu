# set terminal png truecolor nocrop font arial 8 size 420,320 
# set output 'image.1.png'
set label 1 "\"I flew here... by plane.  Why?  For the halibut.\"" at 64, 135, 0 centre norotate back nopoint offset character 0, 0, 0
set title "Larry Ewing's GIMP penguin on vacation basking in\nthe balmy waters off the coast of Murmansk" 
set xrange [ -10.0000 : 137.000 ] noreverse nowriteback
set yrange [ -10.0000 : 157.000 ] noreverse nowriteback
plot 'blutux.rgb' binary array=128x128 flipy format='%uchar' with rgbimage
