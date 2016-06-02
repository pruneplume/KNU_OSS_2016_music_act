package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Listener;

import test.LeapMotionListener;
public class Window {
	public HashMap<Integer,Boolean> select = new HashMap<Integer,Boolean>(); 
	JFrame frame;
	public PointMotion point;
	ButtonListener buttonListener = new ButtonListener();
	JButton startButton;
	JButton stopButton;
	
	public static boolean startFlag = false;
	public static boolean playFlag = false;
	public static boolean closeFlag = false;
	public static boolean musicStop = false;
	public static String musicName = "music.mp3";
	static trueOrFalse  tof = new trueOrFalse();
	PlayMusic play = new PlayMusic();
	Thread musicThread;
	Thread tofThread;
	public Clip clip;
	public Window(){
		initialize();
	}
	public void initialize(){
		frame= new JFrame();
		frame.setTitle("music");
		frame.setBounds(100,100,800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblLeapInstrument = new JLabel("Mution Band"); // JLabel
		// "leap Instrument"
		lblLeapInstrument.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblLeapInstrument.setBounds(330, 6, 187, 45);
		frame.getContentPane().add(lblLeapInstrument);
		point = new PointMotion();
		point.setBounds(0, 0, 800, 600);
		frame.getContentPane().add(point);
		startButton = new JButton();
		startButton.setText("start");
		startButton.setBounds(300, 70, 100, 50);
		frame.getContentPane().add(startButton);
		startButton.addActionListener(buttonListener);
		stopButton = new JButton();
		stopButton.setBounds(400, 70, 100, 50);
		stopButton.setText("stop");
		frame.getContentPane().add(stopButton);
		stopButton.addActionListener(buttonListener);
	}
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Thread leapListenerThread;
			if(e.getSource() == startButton){
				if(Window.startFlag == false){
				Window.startFlag = true;
				Window.playFlag = true;
				Window.closeFlag = false;
				leapListenerThread = new Thread(new MyThread());
				musicThread= new Thread(play);
				//tofThread = new Thread(new trueOrFalse());
				//tofThread.start();
				musicThread.start();
				leapListenerThread.start();
				//if(Window.playFlag == false&&Window.musicStop==false){
				musicName = "music.mp3";

				}
			//	}
			//	if(Window.musicStop == true){
					
			//	}

			}
			if(e.getSource() == stopButton){
				trueOrFalse.currentState = 0;
				trueOrFalse.beforeState = 0;
				trueOrFalse.count = 0;
				LeapMotionListener.beforeX = 0;
				LeapMotionListener.beforeY = 0;
				LeapMotionListener.currentX = 0;
				LeapMotionListener.currentY = 0;
				LeapMotionListener.startX = 0;
				LeapMotionListener.startY = 0;
				Window.startFlag = false;
				Window.closeFlag = true;
				System.out.println(Window.startFlag);
			}
		}
	}
}
class MyThread implements Runnable{
	
	public void run(){
		LeapMotionListener listener = new LeapMotionListener();
		Controller controller = new Controller();
		controller.addListener(listener);
		while(true){
			System.out.printf("");
			if(Window.startFlag == false){
				System.out.println("end");
				controller.removeListener(listener);
				break;
			}
		}
	}
}
class PointMotion extends JComponent{
	int currentX = -10;
	int currentY = -10;
	public PointMotion(){
		
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		//setOpaque(false);
		Point2D center = new Point2D.Float(20,20);
		float radius = 20;
		float[] distribution = {0.0f, 1.0f};
		
		Color[] colors = {Color.black, Color.orange};
		RadialGradientPaint gradient = new RadialGradientPaint(center,radius,
				distribution,colors);
		if(currentX!=0&&currentY!=0){
			g2d.setPaint(gradient);
			g2d.fillOval(currentX, currentY, 20, 20);
		}
	}
	public int getCurrentX() {
		return currentX;
	}
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	public int getCurrentY() {
		return currentY;
	}
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
}
class trueOrFalse implements Runnable{
	static int action = 0;
	public static int count = 0;
	public static int actionNum = 0;
	public static int currentState = 0;
	public static int beforeState = 0;
	public static HashMap<Integer,Boolean> actionList = new HashMap<Integer,Boolean>();
	@Override
	public void run(){
		while(Window.startFlag != false){
			if(actionNum == 5){
				actionNum = 1;
				count++;
			}
//			actionNum = 0;
//			for(int i = 1; i<=4; i++){
//				actionList.put(i, false);
//			}
//			if(action == 1){
//				if(actionNum ==0){
//				actionNum ++;
//				actionList.put(1, true);
//				}
//				while(action == 1){
//					if(Window.startFlag==false)
//						break;
//					;}
//			}
//			if(actionList.get(1) == true&&action==2){
//				if(actionNum ==1){
//					actionNum ++;
//					actionList.put(2, true);
//					actionList.put(1,false);
//				}
//				while(action == 2){
//					if(Window.startFlag==false)
//						break;
//					;}
//			}
//			if(actionList.get(2) == true&&action==3){
//				if(actionNum == 2)
//				actionNum ++;
//				actionList.put(2, false);
//				actionList.put(3, true);
//				while(action == 3){
//					if(Window.startFlag==false)
//						break;
//					;
//				}
//			}
//			if(Window.startFlag==false)
//				break;
//			if(actionList.get(3) == true&&action==4){
//				if(actionNum ==3){
//				actionNum ++;
//				actionList.put(3, false);
//				actionList.put(4, true);
//				}
//				while(action == 4){
//					if(Window.startFlag==false)
//					break;
//					;
//				}
//			}
//			if(actionNum == 4){
//				actionNum = 0;
//				count++;
//			}

		}
		System.out.println(1);
		count = 0;
	}
}
