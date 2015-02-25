# set terminal png transparent nocrop enhanced font arial 8 size 610,480 
# set output 'multiplt.1.png'
set dummy wnt,y
set grid nopolar
set grid xtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000
unset key
set label 1 "" at 0.14, 17, 0 left norotate back nopoint offset character 0, 0, 0
set samples 250, 250
set size ratio 0 0.5,0.5
set origin 0.5,0
set mxtics 10.000000
set mytics 5.000000
set xtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 0.00000,5,20.0000
set ytics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 -1.00000,0.5,1.00000
set title "Second Order System - Unit Impulse Response" 
set xlabel "Normalized Time (wnt)" 
set xrange [ 0.00000 : 20.0000 ] noreverse nowriteback
set ylabel "Amplitude y(wnt)" 
set ylabel  offset character 1, 0, 0 font "" textcolor lt -1 rotate by 90
set yrange [ -1.00000 : 1.00000 ] noreverse nowriteback
mag(w) = -10*log10( (1-w**2)**2 + 4*(zeta*w)**2)
tmp(w) = (-180/pi)*atan( 2*zeta*w/(1-w**2) )
tmp1(w)= w<1?tmp(w):(tmp(w)-180)
phi(w)=zeta==1?(-2*(180/pi)*atan(w)):tmp1(w)
wdwn(zeta)=sqrt(1-zeta**2)
shift(zeta) = atan(wdwn(zeta)/zeta)
alpha(zeta)=zeta>1?sqrt(zeta**2-1.0):0
tau1(zeta)=1/(zeta-alpha(zeta))
tau2(zeta)=1/(zeta+alpha(zeta))
c1(zeta)=(zeta + alpha(zeta))/(2*alpha(zeta))
c2(zeta)=c1(zeta)-1
y1(wnt)=zeta==1?1 - exp(-wnt)*(wnt + 1):0
y2(wnt)=zeta<1?(1 - (exp(-zeta*wnt)/wdwn(zeta))*sin(wdwn(zeta)*wnt + shift(zeta))):y1(wnt)
y(wnt)=exp(-zeta*wnt) * sin(wdwn(zeta)*wnt) / wdwn(zeta)
zeta = 2
plot   zeta=.1,y(wnt),   zeta=.2,y(wnt),   zeta=.3,y(wnt),   zeta=.4,y(wnt),   zeta=.5,y(wnt),   zeta=.707,y(wnt),   zeta=1,y(wnt),   zeta=2,y(wnt)
