package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
public class PlayMusic implements Runnable{
	File file;
	SourceDataLine mLine =null;
	AudioInputStream ais = null;
	AudioFormat audioFormat =null;
	DataLine.Info info;
	
	int bufferSize = 0;
	byte[] buffer;
	int bytesRead = 0;
	@Override
	public void run(){
	
			try{
				file = new File(String.format("%s","./src/test/"+Window.musicName));
				ais = AudioSystem.getAudioInputStream(file);
				audioFormat = ais.getFormat();
				if(audioFormat.getEncoding()!=
						AudioFormat.Encoding.PCM_SIGNED){
					AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							audioFormat.getSampleRate(),
							16,
							audioFormat.getChannels(),
							audioFormat.getChannels()*2,
							audioFormat.getSampleRate(),
							false);
					System.out.println("Converting audio format to"
							+newFormat);
					AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat,ais);
					audioFormat = newFormat;
					ais = newStream;
				}
				info = new DataLine.Info(SourceDataLine.class,audioFormat);
				mLine = (SourceDataLine) AudioSystem.getLine(info);
				mLine.open(audioFormat,mLine.getBufferSize());
				mLine.start();
				bufferSize = (int)audioFormat.getSampleRate()*audioFormat.getFrameSize();
				buffer = new byte[bufferSize];
				bytesRead = 0;
				while(bytesRead >= 0){
					bytesRead = ais.read(buffer,0,buffer.length);
					if(bytesRead >= 0){
						mLine.write(buffer, 0, bytesRead);
					}
					if(Window.closeFlag == true){
						break;
					}
				}
				mLine.drain();
				mLine.close();
				Window.startFlag = false;
				Window.closeFlag = true;
			}catch(Exception e){
				e.printStackTrace();
		}
	}
	public void musicStop(){
		mLine.stop();
	}
	public void musicPlay(){
		mLine.start();
	}

}
class PlayTwo{
	InputStream in;
	AudioInputStream ais;
	Clip clip;
	public void play(String fileName){
		try{
			in = new FileInputStream(fileName);
			try{
				try {
					ais = AudioSystem.getAudioInputStream(in);
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				try {
					clip.open(ais);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clip.start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public void musicStop(){
		clip.stop();
	}
	public void musicClose(){
		clip.close();
	}
}
