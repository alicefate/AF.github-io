/*
 * File AudioPlayer.java
 * Copyright 2004 Dmitry Gakhovich
 *
 * Demonstrates playback of a simple audio file (au, midi).  
 * The path and name of the audio file is specified by
 * the user as a parameter to a constructor or StartPlay method.
 * No GUI interface.
 *
 * Tested using SDK 1.4.2 under Windows 2000
 *
*/

import java.io.*;
import java.applet.*;
import java.util.ArrayList;
import javax.sound.sampled.spi.AudioFileReader;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.net.URL;  


public class AudioPlayer extends Thread {//implements ActionListener
	

  public boolean playing = false;
  private boolean looping = false; 
  private String filename;	// audio file name 
  private URL filenameurl;
  private File audioFile; // = new File(filename);
  private static AudioClip theSound; // playing using AudioClip Class
  private boolean playList;
  private ArrayList clips;   
  //-------------------------------------------//

  public AudioPlayer(String filename){//constructor
    //this.filename = getClass().getResource(filename).toString();	
    this.filename = filename;
  }//end constructor
  //-------------------------------------------//

  public AudioPlayer(URL filenameurl,String filename){//constructor
    this.filenameurl = filenameurl;	
    this.filename = filename;	
  }//end constructor
  //-------------------------------------------//

  public AudioPlayer(){//constructor
    this.filename = "";
  }//end constructor  
  //-------------------------------------------//

     public void run() {	//overide the method

		if(playList){
			int listSize = clips.size();
			for(int i = 0; i<listSize; i++){
				filename = (String)clips.get(i);
				play();
			}	
		}
		else{
			play();
		}
    }//end run()
    
    public void stopPlay(){
    	
        if(theSound!=null){
        	theSound.stop();        
        }
        else 
    	theSound = null;        
        playing = false;             
    }
    
    //method to start playing a single file
    public void startPlay(String filename, boolean looping){
    	playList = false;
       this.filename = filename;
       this.looping = looping;      
       this.start();
    }
    
    //method to start playing a list of files
    public void startPlay(ArrayList clips){
    	playList = true;
       	this.clips = clips;
       	this.looping = false;   
       	this.start();
    }
    
    public void play(){
            audioFile = new File(filename);

            try {
                theSound = Applet.newAudioClip(audioFile.toURL());                 
                        
            } 
            catch (java.net.MalformedURLException e) {
                theSound = null;
                return;
            }    
            if (theSound != null) {
				 playing = true;             	
                 if( looping)
                    theSound.loop();
                 else{                    
                     theSound.play();                
                 }
            }

            long time = getInfo();
			// sleep until AudioClip is finished
        	try{
        		this.sleep(time);
        	}
        	catch(Exception e){}
        	
			playing = false;
          	stopPlay(); // stop and destroy AudioClip if exists     	
    }
    public void setLooping(boolean looping){ 
       this.looping = looping; 
    }
    
    public long getInfo(){//use for sampled audio
    	
    	long playTime = 0;
    	// check if it's 'au' file
    	try{
			playTime = getSampledInfo(); 
			//System.out.println("\nSampled file\n");
        	return playTime; 				
    	}
    	catch(UnsupportedAudioFileException e){
			//System.out.println("\nNot sampled file\n");   		
    	}
    	catch(IOException ioe){
			System.out.println("\nFile Not Found\n");     		    		
    	}
        // System.out.println("Play time1 = " + playTime + " ms\n");    	
    	// check if it's 'midi' file
    	try{
			playTime  = getMidiInfo();
        	return playTime;			    		
    	}
    	catch(InvalidMidiDataException me){
			//System.out.println("\nNot midi file\n");   			
    	}
    	catch(IOException ioe){
			System.out.println("\nFile Not Found\n");    		    		
    	}
	
        return playTime;      
    }
    
    public long getSampledInfo() throws  UnsupportedAudioFileException, IOException{//use for sampled 'au'
    	long playTime = 0;
		audioFile = new File(filename);
    	AudioFileFormat aff = AudioSystem.getAudioFileFormat(audioFile) ;
    	AudioFormat  af = aff.getFormat();
    	AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile) ;
    	long frameLength = ais.getFrameLength() ;
    	
		//System.out.println("Frame Length = " + frameLength);
		float frameRate = af.getFrameRate();
		//System.out.println("Frame Rate = " + frameRate);		
		float time = frameLength / frameRate;//seconds

		playTime = (long)(time *1000 + 1100);//milliseconds			
		//System.out.println("sampled\n playTime = " + playTime);						
		return playTime;    	
    } 
    public long getMidiInfo() throws InvalidMidiDataException, IOException{//use for midi
    	long playTime = 0;    	
		Sequence seq = MidiSystem.getSequence(audioFile);
		long time = seq.getMicrosecondLength(); 
		playTime = time/1000 + 1100; //milliseconds			  		
		//System.out.println("Midi file\n playTime = "+ playTime);     		
        return playTime;    
	}
  
}//end class AudioPlayer.java
