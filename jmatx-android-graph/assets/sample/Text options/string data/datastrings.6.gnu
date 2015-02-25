# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'datastrings.6.png'
set border 3 front linetype -1 linewidth 1.000
set key inside right bottom vertical Right noreverse enhanced autotitles nobox
set size ratio 1 1,1
set noxtics
set noytics
set title "Read labels from a datafile column\nHere the 'plot with labels' command generates a\nC-alpha trace of retro-GCN4 peptide" 
set xlabel "X--->" 
set ylabel "Z--->" 
plot 'labelplot.pdb' using 7:9 with lines notitle,       '' using 7:9:4 with labels tc lt 3 font "Helvetica,10" notitle
