import processing.core.*; 
import processing.xml.*; 

import org.json.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class inklingFeeds_simulation extends PApplet {

//import fullscreen.*; 

//FullScreen fs; 

ArrayList dialogues;
ArrayList events;
HashMap hmEventTypes;

PFont fontSemiBold;
PFont fontSemiBoldItalic;
PFont fontBook;
PFont fontTime;

String[] eventTypes = {"social","purchase","note","signup"};

String URL = "http://api-devel.inkling.com/v1/reports/latest.json?timestamp=";

float timeStamp = 10.0f;
float timeFrame;

JSONArray json;
boolean flagPopJson;

int startDate;
int startHour;

int displayMode; // 1: balloon  2: chart

HourRecord currentHR;

public void setup(){
  
//  size(screen.width, screen.height);
  size(1100,700);
  background(255);
  
  startHour = hour();
  startDate = day();
  
  displayMode = 1;
  
  dialogues = new ArrayList();
  events = new ArrayList();
  hmEventTypes = new HashMap();

  fontBook = loadFont("Whitney-Book-48.vlw");
  fontSemiBold = loadFont("Whitney-Semibold-48.vlw");
//  fontSemiBoldItalic = loadFont("Whitney-SemiboldItalic-48.vlw");
  fontTime = loadFont("Chalkduster-48.vlw");
  
  float eventGap = (width)/(eventTypes.length+1);
  float eventPadding = eventGap;
  
  // initialize events
  for(int i=0; i<eventTypes.length; i++){
//    Event event = new Event( eventTypes[i], colors[i], eventPadding+i*eventGap, height-150);
    Event event = new Event( eventTypes[i], colors[i], eventPadding+i*eventGap, height-150);
    events.add(event);
    
    hmEventTypes.put( eventTypes[i], i);
  }
  
  
  flagPopJson = false; 
 
//  new ToDo ( 5 ) ;
  
  smooth();
  
  currentHR = new HourRecord(0, second(), minute(), hour(), day(), month());
  
  
//  fs = new FullScreen(this); 
//  fs.enter();
    
}


public void draw(){
  
  background(255,255,250);
  
  for(int j=0; j<dialogues.size(); j++){
    
    Dialogue dialogue = (Dialogue)dialogues.get(j);
    dialogue.draw();
    
    if( (dialogue.age+20)>liveSpan){
      dialogue.event.dSize--;
      dialogues.remove(dialogue);
    }
  }
  
  for(int k=0; k<events.size(); k++){
  
    Event event = (Event)events.get(k);
    if(displayMode==1){
      event.draw();
    }
  }
  
  groupingTypesOfDialogues();
 
  // Create Dialogue Dynamicly
//  if( millis()%timeFrame==0 && json.length()>0){
//     
//    try {
//      
//      int numOfDialogue;
//      
//      if( json.length()>5 )
//        numOfDialogue = floor( random(1, 5));
//      else
//        numOfDialogue = 1;
//        
//      for(int i=0; i<numOfDialogue; i++){
//        int index = floor( random(0,json.length()) );
//        JSONObject ob = json.getJSONObject(index);
//        createDialogue(ob);
//        json.remove(index);
//      }
//  
//    } catch(JSONException e){
//      println("JSONException");
//    }
//  }

  simulatData();
  
  if( json!=null && json.length()==0)
    flagPopJson = false; 
  
  if(json!=null)
    println(json.length());
    
  if(displayMode==2){
    currentHR.draw();
  }
  
}

//createDialogue(messages[typeIndex], name, school, type);
public void createDialogue(String message, String name, String school, String type){


  Event event;
  
  for(int m=0; m<events.size(); m++){
    Event anEvent = (Event)events.get(m);
    if(anEvent.eventType.equals(type))
    {
      event = anEvent;
      anEvent.dSize++;
      
      Dialogue newDialogue = new Dialogue(name,school,message,type,event);
      dialogues.add(newDialogue);  
    
      // add to hourRecord
      if( currentHR.hourValue == hour()){
        
        int typIndex = (Integer)hmEventTypes.get(type);
        currentHR.addEventType(typIndex);
        
      }
    
      for(int i=0; i<dialogues.size(); i++){
        Dialogue dialogue = (Dialogue)dialogues.get(i);
        if(dialogue!=newDialogue)
          positionAfterRippleEffect(dialogue, newDialogue.x, newDialogue.y);
      }
      
      break;
    }
  }
}

public void keyPressed() {
  
  switch(key){
    case '1':
      displayMode = 1;
      break;
    case '2':
      displayMode = 2;
      break;      
  
  }
}



class Event{

  float x;
  float y;
  
  float centerX;
  float centerY;
  
  int eventColor;
  String eventType;
  int dSize;
  
  Event(String _eventType, int _eventColor, float _x, float _y){
    eventType = _eventType;
    x = _x;
    y = _y;
    eventColor = _eventColor; 
    dSize = 0;
  }
  
  public void draw(){
       
    fill(eventColor);
    noStroke();
    if(dSize>0 && dSize<19)
      textFont(fontBook,dSize*2+10);
    else if(dSize==0)
      textFont(fontBook,0);
    else
      textFont(fontBook,48);
    text( eventType, x, y);
    
    if(dSize<22){
      float targetY = height-100 + dSize*3;
     
      y += (targetY-y)*0.1f;
    }
    
    centerX = x + textWidth(eventType)/2;
    centerY = y - textAscent()-6;

  }

}


public void groupingTypesOfDialogues(){
   
  float[] medianX = new float[events.size()];
  float[] medianY = new float[events.size()];
  
  int[] count = new int[events.size()];
  
  for(int j=0; j<events.size(); j++){
    medianX[j] = 0;
    medianY[j] = 0;
    count[j] = 0;
  }
  
  for(int i=0; i<dialogues.size(); i++){
  
    Dialogue dialogue = (Dialogue) dialogues.get(i);
    String type = dialogue.event.eventType;
    
    int index = (Integer)hmEventTypes.get(type);
    
    medianX[index] += dialogue.x;
    medianY[index] += dialogue.y;
    count[index] ++;
  }
  
  for(int k=0; k<events.size(); k++){
  
    medianX[k] = medianX[k] / count[k];
    medianY[k] = medianY[k] / count[k];    
  }
  
  for(int ii=0; ii<dialogues.size(); ii++){
  
    Dialogue dialogue = (Dialogue) dialogues.get(ii);
    String type = dialogue.event.eventType;
    
    int index = (Integer)hmEventTypes.get(type);
    float variant = 0.003f;
    float deltaX = (medianX[index] - dialogue.x)*variant*dialogue.offsetX + (dialogue.event.centerX*dialogue.offsetX-dialogue.x-dialogueWidth)*variant*0.1f;
    float deltaY = (medianY[index] - dialogue.y)*variant*dialogue.offsetY; //+ (dialogue.event.centerY+300-dialogue.y)*variant*dialogue.offsetY*0.01;
    
    dialogue.x += deltaX ;
    dialogue.y += deltaY ;
  }
  
}


int HRBoxWidth = 20;
int HRBoxHeight = 5;
int HRGap = 2+HRBoxHeight;

int HRMarginLeft = 300;
int HRMarginTop = height/2;

float twistLevel = 1.2f;

class HourRecord{
  
  float secondValue;
  int minuteValue;
  int hourValue;
  int dayValue;
  int monthValue;
  
  int index;
  
  int newType;
   
  ArrayList dataList;
  
  int[] eventCount; //eventTypes
  
  HourRecord( int _index, int _secondValue,int _minuteValue,int _hourValue, int _dayValue, int _monthValue ){
    
    index = _index;
    secondValue = _secondValue;
    minuteValue = _minuteValue;
    hourValue = _hourValue;
    dayValue = _dayValue;
    monthValue = _monthValue;
    
    newType = 0;
    
    dataList = new ArrayList();
    
    eventCount = new int[ eventTypes.length ];
    
    for(int i=0; i<eventTypes.length; i++){
      eventCount[i] = 0;
    }
      
  }
  
  public void addEventType(int type){
    
    dataList.add(type);
    eventCount[type]++;
    newType = type;
  }
  
  public void draw(){
 
    // circle 
    pushMatrix();
    translate( HRMarginLeft,  height/2);
    
    float currentAngle = 0;//-PI/2;
    
    for(int j=0; j<eventCount.length; j++){
      int count = eventCount[j];
     
      fill(colors[j]);
      noStroke();
      
      float radius = 150 ;//+dataList.size()*0.2;
      float arcAngle = PApplet.parseFloat(count)/dataList.size() * (PI*2);
      arc( 0, 0, radius, radius, currentAngle, currentAngle+arcAngle);
      
      currentAngle += arcAngle;
      
      int textMarginLeft = 166;
      int textMarginTop = -30;
      
      textFont(fontSemiBold, 31);
      
      textAlign(RIGHT);
      text( count, textMarginLeft, textMarginTop+j*30);
      textFont(fontBook, 21);
      
     
      textAlign(LEFT);
      if(count<=1)
        text( eventTypes[j] +" event", textMarginLeft+18, textMarginTop+j*30 );
      else
        text( eventTypes[j] +" events", textMarginLeft+18, textMarginTop+j*30-2 );  
     
      
      noStroke();
      fill(99);

      textAlign(RIGHT);
      textFont(fontSemiBold, 21);
      if(hourValue/12>0){
        text( "/ "+hourValue%12+" pm"+" /", textMarginLeft, textMarginTop-50);
      }
      else{
        text( "/ "+hourValue%12+" am"+" /", textMarginLeft, textMarginTop-50);
      }
      textAlign(LEFT);
      
    }
    
    popMatrix(); 
  }
 

}
   /*
    pushMatrix();
   
    translate( HRMarginLeft,  HRMarginTop);
    
    int numLine = 0;
    
    for(int i=0; i<dataList.size(); i++){
    
      int type = (Integer)dataList.get(i);
      fill( colors[type] );
      noStroke();
      
      if( i%30==0 )
        numLine++;
     
      beginShape();
      vertex( random(-twistLevel,twistLevel) + numLine*HRBoxWidth*(1.2) , random(-twistLevel, twistLevel)+HRGap*(i%30) );
      vertex( HRBoxWidth+random(-twistLevel, twistLevel) + numLine*HRBoxWidth*(1.2), random(-twistLevel,twistLevel)+HRGap*(i%30) );
      vertex( HRBoxWidth+random(-twistLevel, twistLevel) + numLine*HRBoxWidth*(1.2), HRBoxHeight+random(-twistLevel, twistLevel)+HRGap*(i%30) );
      vertex( random(-twistLevel,twistLevel) + numLine*HRBoxWidth*(1.2), HRBoxHeight+random(-twistLevel, twistLevel)+HRGap*(i%30) );
      endShape(CLOSE);
       
    }
    popMatrix();  
    */
    
    
    
    //String[] dummyNames = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Lee", "Bruce", "Jenny", "Leslie", "Faye"};
//String[] dummySchools = {"Harvard", "Stanford", "UCLA", "USC", "Yale", "MIT", "Boston University"};
//String[] titles = {"Raven", "Kerin", "Schiller", "King"};

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
int color1 = color(  0,125,182); 
int color2 = color(  0,145,178); 
int color3 = color(  0,156,158); 
int color4 = color(  0,167,126);
int color5 = color( 34,178, 76); 
int color6 = color( 96,176, 49); 
int color7 = color(162,182, 46);

int[] colors = { color1,color3,color5,color7,color7,color6,color7 };

int dialogueWidth = 270;
int dialogueHeight = 80;  
int hookPadding = 50;
int hookWidth = 26;
int hookHeight = 20;

int liveSpan = 800;
int dialogueColor = 153;

int ageInit = 28 + 8;
float ageInitExpand = ageInit*0.5f;
float ageInitShrink = ageInit*0.7f;
float scaleInitExpand = 1.2f;
float scaleInitShrink = 0.8f;
int liveSpanBouncing = 24 + 8;
float ageBouncingExpand = liveSpanBouncing*0.8f;
float ageBouncingShrink = liveSpanBouncing*0.95f;

int dTextMargin = 12;
int dTextSize = 18;


class Dialogue{

  String name;
  String organization;
  String message;

  String typeOfEvent;
  
  int dColor;
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
  
    if(random(1)<0.5f)
      isHookLeft = true;
    else
      isHookLeft = false;
      
    x = event.centerX + random(-120,120) - dialogueWidth/2;
    y = random( 100,height-300);
  }

  public void draw(){
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
 
  public void drawBezier(){
    float newRed = red(dColor) + PApplet.parseFloat(age)/PApplet.parseFloat(liveSpan)*(255-red(dColor));
    float newGreen = green(dColor) + PApplet.parseFloat(age)/PApplet.parseFloat(liveSpan)*(255-green(dColor));
    float newBlue = blue(dColor) + PApplet.parseFloat(age)/PApplet.parseFloat(liveSpan)*(255-blue(dColor));
    if(isHookLeft){
      noFill();
      strokeWeight(0.5f);
      stroke(newRed, newGreen, newBlue);
      bezier( x+hookPadding, y+dialogueHeight+hookHeight, x+hookPadding-hookWidth, y+dialogueHeight+hookHeight*2, event.centerX, event.centerY-100, event.centerX, event.centerY);
    }
    else{
      noFill();
      strokeWeight(0.5f);      
      stroke(newRed, newGreen, newBlue);
      bezier( x+dialogueWidth-hookPadding, y+dialogueHeight+hookHeight, x+dialogueWidth-hookPadding+hookWidth, y+dialogueHeight+hookHeight*2, event.centerX, event.centerY-100, event.centerX, event.centerY);
    }
    
  }
  
  public void drawInit(){
    
    // phaseInit
    noStroke();
    
    float newRed = red(dColor) + PApplet.parseFloat(age)/PApplet.parseFloat(liveSpan)*(255-red(dColor));
    float newGreen = green(dColor) + PApplet.parseFloat(age)/PApplet.parseFloat(liveSpan)*(255-green(dColor));
    float newBlue = blue(dColor) + PApplet.parseFloat(age)/PApplet.parseFloat(liveSpan)*(255-blue(dColor));
    
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
      text(name,x+dTextMargin, y+dTextMargin*1.1f, dialogueWidth-dTextMargin*2, 20);
    }
    else{
      text(name+" @ ",x+dTextMargin, y+dTextMargin*1.1f, dialogueWidth-dTextMargin*2, 20);
      float nameWidth = textWidth(name + " @ ");
//      textFont(fontSemiBoldItalic, dTextSize);
      if( nameWidth < dialogueWidth-dTextMargin*2 )
      text( organization, x+dTextMargin+nameWidth, y+dTextMargin*1.1f, dialogueWidth-dTextMargin-nameWidth, 20);
    }
    
    textFont(fontBook, dTextSize);
    
    
    text( message, x+dTextMargin, y+dTextMargin*2.2f+8, dialogueWidth-dTextMargin*2, dialogueHeight - dTextMargin*2.2f);
    popMatrix();
  }

}




float speedFactor = 60.0f;

public void positionAfterRippleEffect(Dialogue dialogue, float newDialogueX, float newDialogueY){
  
  float distance = dist(dialogue.x, dialogue.y, newDialogueX, newDialogueY)+random(1,30);
  
  float speed = speedFactor/distance;

  float deltaX = (dialogue.x - newDialogueX)*sq(speed)*1.8f;
  float deltaY = (dialogue.y - newDialogueY)*sq(speed)*1.1f;
  
  dialogue.toX = dialogue.x + deltaX;
  dialogue.toY = dialogue.y + deltaY;
  
  dialogue.fromX = dialogue.x;
  dialogue.fromY = dialogue.y;
 
  dialogue.isBouncing = true;
  dialogue.ageBouncing = 0;
}


public float scaleForPhaseInit(int age){

  float newScale; 
  
  if(age<ageInitExpand){
    float angle = map(age, 0, ageInitExpand, -PI, 0);
    newScale = map( cos(angle), -1, 1, 0, scaleInitExpand );
  }
  else if(age<ageInitShrink){
    float angle = map(age, ageInitExpand, ageInitShrink, 0, PI);
    newScale = map( cos(angle), 1, -1, scaleInitExpand, scaleInitShrink);
  }
  else{
    float angle = map(age, ageInitShrink, ageInit, -PI/2, 0);
    newScale = map( cos(angle), 0, 1, scaleInitShrink, 1 );
  }
  
  return newScale;
}

public void updatePositionForBouncing(Dialogue dialogue){

  if(dialogue.ageBouncing<ageBouncingExpand){
    float angle = map( dialogue.ageBouncing, 0, ageBouncingExpand, -PI, 0);
    dialogue.x = map( cos(angle), -1, 1, dialogue.fromX, dialogue.toX+(dialogue.toX-dialogue.fromX)*0.2f);
    dialogue.y = map( cos(angle), -1, 1, dialogue.fromY, dialogue.toY+(dialogue.toY-dialogue.fromY)*0.2f);
  }
  else{
    float angle = map( dialogue.ageBouncing, ageBouncingExpand, liveSpanBouncing, 0, PI);
    dialogue.x = map( cos(angle), 1, -1, dialogue.toX+(dialogue.toX-dialogue.fromX)*0.2f, dialogue.toX);
    dialogue.y = map( cos(angle), 1, -1, dialogue.toY+(dialogue.toY-dialogue.fromY)*0.2f, dialogue.toY);
  }
  
  
}
String[] dummyNames = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Lee", "Bruce", "Jenny", "Leslie", "Faye"};
String[] dummySchools = {"Harvard", "Stanford", "UCLA", "USC", "Yale", "MIT", "Boston University"};
String[] titles = {"Raven", "Kerin", "Schiller", "King"};

String[] messages = {"just add a new friend", "purchased a new bundle", "created a note", "just joined the community"};


public void simulatData(){

  if(random(1)<0.02f){
    
    int numOfDialogue = floor(random(1)*3);  

    for(int i=0; i<numOfDialogue; i++){
            
      int typeIndex = floor( random(1)*eventTypes.length );
      String type = eventTypes[typeIndex];
      int nameIndex = floor( random(1)*dummyNames.length );
      String name = dummyNames[nameIndex];
      int schoolIndex = floor( random(1)*dummySchools.length );
      String school = dummySchools[schoolIndex];
      
      createDialogue(messages[typeIndex], name, school, type);
    }
//      ob.put("message", );
//      ob.put("name", name);
//      ob.put("institution", school);
//      ob.put("type", type);
//      
//      createDialogue(ob);
//      
//      try{
//        JSONObject ob = new JSONObject();
//        
//        int typeIndex = floor( random(1)*eventTypes.length );
//        String type = eventTypes[typeIndex];
//        int nameIndex = floor( random(1)*dummyNames.length );
//        String name = dummyNames[nameIndex];
//        int schoolIndex = floor( random(1)*dummySchools.length );
//        String school = dummySchools[schoolIndex];
//        
//        
//        ob.put("message", messages[typeIndex]);
//        ob.put("name", name);
//        ob.put("institution", school);
//        ob.put("type", type);
//        
//        createDialogue(ob);
//      }
//      catch (JSONException e) {
//        println("JSONException");
//        println(e);
//      }
//      
//    }
  }
  
}

//import java.util.Timer;
//import java.util.TimerTask;
//
//public class ToDo  {
//  Timer timer;
//
//  public ToDo ( int seconds )   {
//    timer = new Timer() ;
//    timer.scheduleAtFixedRate ( new ToDoTask () , 1*1000 , seconds*1000) ;
//  }
//
//  class ToDoTask extends TimerTask  {
//
//    public void run (  )   {
//    
//    println(" 5 sec ");
//    
//    if( flagPopJson==false ){
//      
//      println(" do query ");
//      
//      flagPopJson = true;
//      float newTimeStamp = System.currentTimeMillis()/1000-10;
//      String query = URL + "0.0";//newTimeStamp;
//      println(query);
//      getData(query);
//    }
////     timer.cancel( ) ; //Terminate the thread
//    }
//  }
//
//  void getData(String newURL)
//  {
//    try {
//      URL url = new URL(newURL);
//      URLConnection u = url.openConnection();
//         
//      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//         
//      String inputLine;
//      StringBuilder builder = new StringBuilder(); 
//  
//      while ((inputLine = in.readLine()) != null)
//      builder.append(inputLine); 
//      in.close();
//         
//      json = new JSONArray(builder.toString());
//      timeFrame = floor( timeStamp / json.length() * 1000 );
//      flagPopJson = true;
//      
//      println(json);
//      println("length  :  "+json.length());
//      println("timeFrame  :  "+timeFrame);
//     
//    } catch (MalformedURLException e) {
//      println("MalformedURLException");
//      println(e);
//    } catch (IOException e) {               // openConnection() failed
//      println("IOException");
//      println(e);
//    } catch (JSONException e) {               // openConnection() failed
//      println("JSONException");
//      println(e);
//    }
//  }
//  
//  
//}    
//    
///*    
//// Start the thread
//class BasicThread implements Runnable {
//  // This method is called when the thread runs
//  public void run() {
//    
//    
//  }
//  
//  void getData(String newURL)
//  {
//    try {
//      URL url = new URL(newURL);
//      URLConnection u = url.openConnection();
//         
//      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//         
//      String inputLine;
//      StringBuilder builder = new StringBuilder(); 
//  
//      while ((inputLine = in.readLine()) != null)
//      builder.append(inputLine); 
//      in.close();
//         
//      json = new JSONArray(builder.toString());
//      timeFrame = floor( timeStamp / json.length() * 1000 );
//      flagPopJson = true;
//      
//      println(json);
//      println("length  :  "+json.length());
//      println("timeFrame  :  "+timeFrame);
//     
//    } catch (MalformedURLException e) {
//      println("MalformedURLException");
//      println(e);
//    } catch (IOException e) {               // openConnection() failed
//      println("IOException");
//      println(e);
//    } catch (JSONException e) {               // openConnection() failed
//      println("JSONException");
//      println(e);
//    }
//  }
//
//}
//*/

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#ffffff", "inklingFeeds_simulation" });
  }
}
