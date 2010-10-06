
int HRBoxWidth = 20;
int HRBoxHeight = 5;
int HRGap = 2+HRBoxHeight;

int HRMarginLeft = 300;
int HRMarginTop = height/2;

float twistLevel = 1.2;

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
  
  void addEventType(int type){
    
    dataList.add(type);
    eventCount[type]++;
    newType = type;
  }
  
  void draw(){
 
    // circle 
    pushMatrix();
    translate( HRMarginLeft,  height/2);
    
    float currentAngle = 0;//-PI/2;
    
    for(int j=0; j<eventCount.length; j++){
      int count = eventCount[j];
     
      fill(colors[j]);
      noStroke();
      
      float radius = 150 ;//+dataList.size()*0.2;
      float arcAngle = float(count)/dataList.size() * (PI*2);
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
