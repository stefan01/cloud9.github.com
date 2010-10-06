
float speedFactor = 60.0;

void positionAfterRippleEffect(Dialogue dialogue, float newDialogueX, float newDialogueY){
  
  float distance = dist(dialogue.x, dialogue.y, newDialogueX, newDialogueY)+random(1,30);
  
  float speed = speedFactor/distance;

  float deltaX = (dialogue.x - newDialogueX)*sq(speed)*1.8;
  float deltaY = (dialogue.y - newDialogueY)*sq(speed)*1.1;
  
  dialogue.toX = dialogue.x + deltaX;
  dialogue.toY = dialogue.y + deltaY;
  
  dialogue.fromX = dialogue.x;
  dialogue.fromY = dialogue.y;
 
  dialogue.isBouncing = true;
  dialogue.ageBouncing = 0;
}


float scaleForPhaseInit(int age){

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

void updatePositionForBouncing(Dialogue dialogue){

  if(dialogue.ageBouncing<ageBouncingExpand){
    float angle = map( dialogue.ageBouncing, 0, ageBouncingExpand, -PI, 0);
    dialogue.x = map( cos(angle), -1, 1, dialogue.fromX, dialogue.toX+(dialogue.toX-dialogue.fromX)*0.2);
    dialogue.y = map( cos(angle), -1, 1, dialogue.fromY, dialogue.toY+(dialogue.toY-dialogue.fromY)*0.2);
  }
  else{
    float angle = map( dialogue.ageBouncing, ageBouncingExpand, liveSpanBouncing, 0, PI);
    dialogue.x = map( cos(angle), 1, -1, dialogue.toX+(dialogue.toX-dialogue.fromX)*0.2, dialogue.toX);
    dialogue.y = map( cos(angle), 1, -1, dialogue.toY+(dialogue.toY-dialogue.fromY)*0.2, dialogue.toY);
  }
  
  
}
