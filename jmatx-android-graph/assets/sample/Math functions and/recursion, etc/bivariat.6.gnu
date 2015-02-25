# set terminal png transparent nocrop enhanced font arial 8 size 420,320 
# set output 'bivariat.6.png'
set key 4, -3, 0 right top vertical Right noreverse enhanced autotitles nobox
set title "Min(x,y) and Max(x,y)" 
set xrange [ -5.00000 : 5.00000 ] noreverse nowriteback
set yrange [ -10.0000 : 10.0000 ] noreverse nowriteback
integral_f(x) = (x>0)?int1a(x,x/ceil(x/delta)):-int1b(x,-x/ceil(-x/delta))
int1a(x,d) = (x<=d*.1) ? 0 : (int1a(x-d,d)+(f(x-d)+4*f(x-d*.5)+f(x))*d/6.)
int1b(x,d) = (x>=-d*.1) ? 0 : (int1b(x+d,d)+(f(x+d)+4*f(x+d*.5)+f(x))*d/6.)
f(x)=sin(x-1)-.75*sin(2*x-1)+(x**2)/8-5
integral2_f(x,y) = (x<y)?int2(x,y,(y-x)/ceil((y-x)/delta)):                         -int2(y,x,(x-y)/ceil((x-y)/delta))
int2(x,y,d) = (x>y-d*.5) ? 0 : (int2(x+d,y,d) + (f(x)+4*f(x+d*.5)+f(x+d))*d/6.)
ack(m,n) = (m == 0) ? n + 1 : (n == 0) ? ack(m-1,1) : ack(m-1,ack(m,n-1))
min(x,y) = (x < y) ? x : y
max(x,y) = (x > y) ? x : y
delta = 0.2
plot sin(x), x**2, x**3, max(sin(x), min(x**2, x**3))+0.5
