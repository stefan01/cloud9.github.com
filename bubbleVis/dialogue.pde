color color1 = color(  0,125,182); 
color color2 = color(  0,145,178); 
color color3 = color(  0,156,158); 
color color4 = color(  0,167,126);
color color5 = color( 34,178, 76); 
color color6 = color( 96,176, 49); 
color color7 = color(162,182, 46);

color[] colors = { color1,color3,color5,color7,color7,color6,color7 };

int dialogueWidth = 270;
int dialogueHeight = 80;  
int hookPadding = 50;
int hookWidth = 26;
int hookHeight = 20;

int liveSpan = 800;
int dialogueColor = 153;

int ageInit = 28 + 8;
float ageInitExpand = ageInit*0.5;
float ageInitShrink = ageInit*0.7;
float scaleInitExpand = 1.2;
float scaleInitShrink = 0.8;
int liveSpanBouncing = 24 + 8;
float ageBouncingExpand = liveSpanBouncing*0.8;
float ageBouncingShrink = liveSpanBouncing*0.95;

int dTextMargin = 12;
int dTextSize = 18;


class Dialogue{

  String name;
  String organization;
  String message;

  String typeOfEvent;
  
  color dColor;
  int age;
  float dScale;
  
  int ageBouncing;
  
  float x;
  float y;
  
  float toX;
  float toY;
  float fromX;
  float fromY;
  
  boolean isHookLeft;
  boolean isBouncing;
  
  float offsetX;
  float offsetY;
  
  Event event;
  
  Dialogue(String _name, String _organization, String _message, String _typeOfEvent, Event _event){
      
    name = _name;
    organization = _organization;
    message = _message;
    
    typeOfEvent = _typeOfEvent;
    
    event = _event;
    
    isBouncing = false;
    ageBouncing = 0;
    
    dScale = 0;
    age = 0;
    
    offsetX = random(1);
    offsetY = random(1);
  
    for(int i = 0; i<eventTypes.length; i++){
      if(eventTypes[i].equals(typeOfEvent)){
        dColor = colors[i];
        break;
      }
    }
  
    if(random(1)<0.5)
      isHookLeft = true;
    else
      isHookLeft = false;
      
    x = event.centerX + random(-120,120) - dialogueWidth/2;
    y = random( 100,height-300);
  }

  void draw(){
    age++;
    
    if(age<ageInit){
      dScale = scaleForPhaseInit(age);
    }
    
    else if(isBouncing){
      ageBouncing++;
      updatePositionForBouncing(this);
      
      if(ageBouncing>liveSpanBouncing)
        isBouncing = false;
    }
    
    if(displayMode==1){
      drawInit();
      drawBezier();
    }
  }
 
  void drawBezier(){
    float newRed = red(dColor) + float(age)/float(liveSpan)*(255-red(dColor));
    float newGreen = green(dColor) + float(age)/float(liveSpan)*(255-green(dColor));
    float newBlue = blue(dColor) + float(age)/float(liveSpan)*(255-blue(dColor));
    if(isHookLeft){
      noFill();
      strokeWeight(0.5);
      stroke(newRed, newGreen, newBlue);
      bezier( x+hookPadding, y+dialogueHeight+hookHeight, x+hookPadding-hookWidth, y+dialogueHeight+hookHeight*2, event.centerX, event.centerY-100, event.centerX, event.centerY);
    }
    else{
      noFill();
      strokeWeight(0.5);      
      stroke(newRed, newGreen, newBlue);
      bezier( x+dialogueWidth-hookPadding, y+dialogueHeight+hookHeight, x+dialogueWidth-hookPadding+hookWidth, y+dialogueHeight+hookHeight*2, event.centerX, event.centerY-100, event.centerX, event.centerY);
    }
    
  }
  
  void drawInit(){
    
    // phaseInit
    noStroke();
    
    float newRed = red(dColor) + float(age)/float(liveSpan)*(255-red(dColor));
    float newGreen = green(dColor) + float(age)/float(liveSpan)*(255-green(dColor));
    float newBlue = blue(dColor) + float(age)/float(liveSpan)*(255-blue(dColor));
    
    pushMatrix();
    translate( (x+dialogueWidth/2), (y+dialogueHeight/2) );
    scale( dScale );
    translate( -(x+dialogueWidth/2), -(y+dialogueHeight/2) );
    
    fill(newRed, newGreen, newBlue);
    stroke(255);
    strokeWeight(3);
    
    if(isHookLeft){
      beginShape();
      vertex(x, y);
      vertex(x+dialogueWidth, y);
      vertex(x+dialogueWidth, y+dialogueHeight);
      vertex(x+hookPadding+hookWidth, y+dialogueHeight);
      vertex(x+hookPadding, y+dialogueHeight+hookHeight);
      vertex(x+hookPadding, y+dialogueHeight);
      vertex(x, y+dialogueHeight);
      endShape(CLOSE);
    }
    else{
      beginShape();
      vertex(x, y);
      vertex(x+dialogueWidth, y);
      vertex(x+dialogueWidth, y+dialogueHeight);
      vertex(x+dialogueWidth-hookPadding, y+dialogueHeight);
      vertex(x+dialogueWidth-hookPadding, y+dialogueHeight+hookHeight);
      vertex(x+dialogueWidth-hookPadding-hookWidth, y+dialogueHeight);
      vertex(x, y+dialogueHeight);
      endShape(CLOSE);
    }
    
    fill(255);
    noStroke();
    textFont(fontSemiBold, dTextSize);
    
    if( organization.equals("") ){
      text(name,x+dTextMargin, y+dTextMargin*1.1, dialogueWidth-dTextMargin*2, 20);
    }
    else{
      text(name+" @ ",x+dTextMargin, y+dTextMargin*1.1, dialogueWidth-dTextMargin*2, 20);
      float nameWidth = textWidth(name + " @ ");
//      textFont(fontSemiBoldItalic, dTextSize);
      if( nameWidth < dialogueWidth-dTextMargin*2 )
      text( organization, x+dTextMargin+nameWidth, y+dTextMargin*1.1, dialogueWidth-dTextMargin-nameWidth, 20);
    }
    
    textFont(fontBook, dTextSize);
    
    
    text( message, x+dTextMargin, y+dTextMargin*2.2+8, dialogueWidth-dTextMargin*2, dialogueHeight - dTextMargin*2.2);
    popMatrix();
  }

}



