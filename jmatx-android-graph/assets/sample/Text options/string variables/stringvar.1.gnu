# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'stringvar.1.png'
set label 1 "\nExercise substring handling\n\nbeg = 2 end = 4\nfoo           = ABCDEF\nfoo[3:5]      = CDE\nfoo[1:1]      = A\nfoo[5:3]      = \nfoo[beg:end]  = BCD\nfoo[end:beg]  = \nfoo[5:]       = EF\nfoo[5:*]      = EF\nfoo[:]        = ABCDEF\nfoo[*:*]      = ABCDEF\nfoo.foo[2:2]  = ABCDEFB\n(foo.foo)[2:2]= B\n" at graph 0.1, 0.9, 0 left norotate back nopoint offset character 0, 0, 0
set noxtics
set noytics
set yrange [ 0.00000 : 1.00000 ] noreverse nowriteback
beg = 2
end = 4
foo = "ABCDEF"
ERRNO = 0
plot 0
