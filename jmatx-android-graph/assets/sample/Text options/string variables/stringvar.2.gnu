# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'stringvar.2.png'
set label 1 "Exercise string handling functions\n\nfoo          = ABCDEF\nstrlen(foo)  = 6\nsubstr(foo,3,4) = CD\n\nhaystack     = `date`\nhaystack     = Tue Mar  6 11:00:35 PST 2007\nneedle       = :\nS = strstrt(haystack,needle) = 14\nhaystack[S-2:S+2] = 11:00\nIt is now 11:00\n\nwords(haystack)   = 6\nword(haystack,5)  = PST\n\nsprintf output of long strings works OK\n" at graph 0.1, 0.9, 0 left norotate back nopoint offset character 0, 0, 0
set noxtics
set noytics
set yrange [ 0.00000 : 1.00000 ] noreverse nowriteback
beg = 2
end = 4
foo = "                                       1                                        2                                        3                                        4                                        5                                        6"
ERRNO = 0
haystack = "Tue Mar  6 11:00:35 PST 2007"
needle = ":"
S = 14
plot 0
