
/*
void drawTime(){

  int tMin = minute();
  int tHour = hour();
  int tSecond = second();
  int d = day();    // Values from 1 - 31
  int m = month();  // Values from 1 - 12
  int y = year();   // 2003, 2004, 2005, etc.
  
  fill(66);
  textFont(fontTime, 27);
  text( String.valueOf(tSecond)+ ":" + String.valueOf(tHour) + ":" + String.valueOf(tMin) + " "+String.valueOf(d) + "-"+ String.valueOf(m) + "-"+ String.valueOf(y), width-400, height-100);
}
*/


/*
int clockRadius = 30;
int clockNeedleLength = 15;
float clockNeedleStorke = 3;

int clockMarginLeft = 20;
int clockMarginBottom = 10;
float clockGap = clockRadius*2.1;



void drawClocks(){
  
  for(int i=0; i<24; i++){
  
    float cx = clockMarginLeft+clockRadius+i*clockGap;
    float cy = height-clockMarginBottom-clockRadius;
    
    pushMatrix();
    translate(cx, cy);
    rotate(PI/6*i+PI);
    
    if(hour()==i){
      fill(180);
      noStroke();
      ellipse(0,0,clockRadius,clockRadius);
      
      noStroke();
      fill(255);
      rect( -clockNeedleStorke/2-1, 0, clockNeedleStorke, clockNeedleLength);
    }
    else{
      strokeWeight(1);
      stroke(180);
      fill(255);
      ellipse(0,0,clockRadius,clockRadius);
      
      noStroke();
      fill(180);
      rect( -clockNeedleStorke/2-1, 0, clockNeedleStorke, clockNeedleLength);
    }
    
    popMatrix();
  }
  
}


void drawColorCodeAnnotation(){
  
  for(int i =0; i<eventTypes.length; i++){
    noStroke();
    textFont(fontBook, 25);
    fill( colors[i] );
    text( eventTypes[i], (width-400)/eventTypes.length + i * 120, height-80 );
  }
  
}

*/
