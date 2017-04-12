import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
//import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
public class ScreenManager {
	
	private GraphicsDevice vc;
	
	//give vc access to monitor screen
	public ScreenManager(){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = e.getDefaultScreenDevice();
	}
	
	//get all compatible dm
	public DisplayMode[] getCompatibleDisplayModes(){
		return vc.getDisplayModes();
	}
	
	public DisplayMode findFirstMode(DisplayMode Modes[]){
		DisplayMode goodModes[] = vc.getDisplayModes();
			for(int x=0;x<Modes.length;x++){
				for(int y=0;y<goodModes.length;y++){
					if(displayModesMatch(Modes[x],goodModes[y])){
						return Modes[x];
					}
				}
			}
			return null;
	}
	
	//get current display mode
	public DisplayMode getCurrMode(){
		return vc.getDisplayMode();
	}
	
	public boolean displayModesMatch(DisplayMode m1,DisplayMode m2){
		if(m1.getWidth()!=m2.getWidth() || m1.getHeight()!=m2.getHeight()){
			return false;
		}
		
		if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()){
			return false;
		}
		
		if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate() ){
			return false;
		}
		
		return true;
	}
	
	private JFrame f = new JFrame();
	
	public JFrame getFrame(){
		return f;
	}
	
	public void setFullScreen(DisplayMode dm){
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		vc.setFullScreenWindow(f);
		
		if(dm != null && vc.isDisplayChangeSupported()){
			try{
				vc.setDisplayMode(dm);
			}catch(Exception ex){}
		}
		
		f.createBufferStrategy(2);
	}
	
	//graphic object equal to this function
	public Graphics2D getGraphics(){
		Window w = vc.getFullScreenWindow();
		if(w!=null){
			BufferStrategy s = w.getBufferStrategy();
			return(Graphics2D)s.getDrawGraphics();
		}else{
			return null;
		}
	}
	
	public void update(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			if(!s.contentsLost()){
				s.show();
			}
		}
	}
	
	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}
	
	public int getW(){
		Window w = vc.getFullScreenWindow();
		if(w!=null){
			return w.getWidth();
		}else{
			return 0;
		}
	}
	
	public int getH(){
		Window w = vc.getFullScreenWindow();
		if(w!=null){
			return w.getHeight();
		}else{
			return 0;
		}
	}
	
	public void restoreScreen(){			//cuz we don't want a full screen for the rest of our lives
		Window w = vc.getFullScreenWindow();
		if(w!=null){
			w.dispose();
		}
		vc.setFullScreenWindow(null); 
	}
	
	//create image compatible with monitor
	public BufferedImage createImage(int w, int h, int t){
		Window win = vc.getFullScreenWindow();
		if(win != null){
			GraphicsConfiguration gc = win.getGraphicsConfiguration();
			return gc.createCompatibleImage(w,h,t);
		}
		return null;
	}

}
