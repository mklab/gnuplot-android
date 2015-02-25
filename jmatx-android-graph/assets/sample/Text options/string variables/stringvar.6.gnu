# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'stringvar.6.png'
set key title "file(i) = sprintf(\"%1d.dat\",i); N=2; M=3"
set key inside left top vertical Left reverse enhanced autotitles nobox
set key noinvert samplen 4 spacing 1 width 25 height 0 
set title "String-valued functions to generate datafile names" 
file(i) = sprintf("%1d.dat",i)
beg = 2
end = 4
foo = "                                       1                                        2                                        3                                        4                                        5                                        6"
ERRNO = 0
haystack = "Tue Mar  6 11:00:35 PST 2007"
needle = ":"
S = 14
N = 2
M = 3
plot 5*sin(x)/x, file(N), file(M)
