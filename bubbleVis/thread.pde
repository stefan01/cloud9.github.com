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

