import javax.media.opengl.*;
import processing.opengl.*;


String[] names = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Bruce", "Jenny"};
HashMap playersHashMap;

color bgColor = color(14,40,59);

void setup(){
  size(800, 600, OPENGL);
  background(30);

  
  playersHashMap = new HashMap();
  
  // initiate Player Info
  for(int i=0; i<names.length; i++){
    color randomColor = color(random(255),random(255),random(255));
    Person person = new Person(names[i], randomColor);
    playersHashMap.put( names[i], person);
  }
  
  smooth();
}

void draw(){
  
  noStroke();
  fill(255,5);
  rect(0,0,width,height);
   
  Iterator i = playersHashMap.entrySet().iterator();  // Get an iterator

  while (i.hasNext()) {
    
    Map.Entry entry = (Map.Entry)i.next();
    Person person = (Person)entry.getValue();
    
    person.draw();
  }
  
}

void keyPressed() {
  if (key == 's') {
    saveFrame(); 
  }
}
