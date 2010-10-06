

class Event{

  float x;
  float y;
  
  float centerX;
  float centerY;
  
  color eventColor;
  String eventType;
  int dSize;
  
  Event(String _eventType, color _eventColor, float _x, float _y){
    eventType = _eventType;
    x = _x;
    y = _y;
    eventColor = _eventColor; 
    dSize = 0;
  }
  
  void draw(){
       
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
     
      y += (targetY-y)*0.1;
    }
    
    centerX = x + textWidth(eventType)/2;
    centerY = y - textAscent()-6;

  }

}


void groupingTypesOfDialogues(){
   
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
    float variant = 0.001;
    float deltaX = (medianX[index] - dialogue.x)*variant*dialogue.offsetX + (dialogue.event.centerX*dialogue.offsetX-dialogue.x-dialogueWidth)*variant*0.1;
    float deltaY = (medianY[index] - dialogue.y)*variant*dialogue.offsetY; //+ (dialogue.event.centerY+300-dialogue.y)*variant*dialogue.offsetY*0.01;
    
    dialogue.x += deltaX ;
    dialogue.y += deltaY ;
  }
  
}

