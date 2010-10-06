String[] dummyNames = {"James", "Anne", "Jon", "Lenny", "Steve", "Jay", "Michael", "CJ", "Mary", "Lee", "Bruce", "Jenny", "Leslie", "Faye"};
String[] dummySchools = {"Harvard", "Stanford", "UCLA", "USC", "Yale", "MIT", "Boston University"};
String[] titles = {"Raven", "Kerin", "Schiller", "King"};

String[] messages = {"just add a new friend", "purchased a new bundle", "created a note", "just joined the community"};


void simulatData(){

  if(random(1)<0.02){
    
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

