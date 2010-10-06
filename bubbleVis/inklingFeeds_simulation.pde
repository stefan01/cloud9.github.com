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

float timeStamp = 10.0;
float timeFrame;

JSONArray json;
boolean flagPopJson;

int startDate;
int startHour;

int displayMode; // 1: balloon  2: chart

HourRecord currentHR;

void setup(){
  
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


void draw(){
  
//  background(255,255,250);
  background(255);
  
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
void createDialogue(String message, String name, String school, String type){


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

void keyPressed() {
  
  switch(key){
    case '1':
      displayMode = 1;
      break;
    case '2':
      displayMode = 2;
      break;      
  
  }
}

