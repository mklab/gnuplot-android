# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'datastrings.1.png'
set key inside right top vertical Right noreverse enhanced autotitles columnhead nobox
set label 1 "Generate plot labels from first row in each column" at graph 0.02, 0.85, 0 left norotate back textcolor lt 3 nopoint offset character 0, 0, 0
set label 2 "Generate x-axis labels from first column in each row" at graph 0.02, 0.8, 0 left norotate back textcolor lt 3 nopoint offset character 0, 0, 0
set style data linespoints
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 
set xtics   ("mai" 0.00000, "jun" 1.00000, "jul" 2.00000, "aug" 3.00000, "sep" 4.00000, "okt" 5.00000, "nov" 6.00000, "des" 7.00000, "jan" 8.00000, "feb" 9.00000, "mar" 10.0000, "apr" 11.0000)
set title "Auto-labeling plots from text fields in datafile" 
set ylabel "mm" 
set yrange [ 0.00000 : 200.000 ] noreverse nowriteback
plot 'ctg-y2.dat' using 2:xticlabel(1) index 2,      ''           using 2 index 3
