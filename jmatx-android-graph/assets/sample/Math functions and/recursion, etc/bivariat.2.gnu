# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'bivariat.2.png'
set key inside right bottom vertical Right noreverse enhanced autotitles nobox
set samples 50, 50
set title "approximate the integral of functions" 
integral_f(x) = (x>0)?int1a(x,x/ceil(x/delta)):-int1b(x,-x/ceil(-x/delta))
int1a(x,d) = (x<=d*.1) ? 0 : (int1a(x-d,d)+(f(x-d)+4*f(x-d*.5)+f(x))*d/6.)
int1b(x,d) = (x>=-d*.1) ? 0 : (int1b(x+d,d)+(f(x+d)+4*f(x+d*.5)+f(x))*d/6.)
f(x)=cos(x)
integral2_f(x,y) = (x<y)?int2(x,y,(y-x)/ceil((y-x)/delta)):                         -int2(y,x,(x-y)/ceil((x-y)/delta))
int2(x,y,d) = (x>y-d*.5) ? 0 : (int2(x+d,y,d) + (f(x)+4*f(x+d*.5)+f(x+d))*d/6.)
delta = 0.2
plot [-5:5] f(x) title "f(x)=cos(x)", integral_f(x)
