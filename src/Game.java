import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Game implements KeyListener{
	
	public static void main(String[] args){
		Game s = new Game();
		s.run();
	}
	
	private int k =0;
	private int m =1;
	private long time;
	private long score;
	boolean running=true;
	private Animation hero;
	private Animation devil;
	private Animation thing;
	private Animation back;
	private ScreenManager s1;
	private sprite sprite;
	private sprite rod;
	private sprite evil;
	private sprite bock;
	private sprite bockn;
	Image pic6= new ImageIcon("images\\bg_tree.jpg").getImage();
	Image pic7= new ImageIcon("images\\bg.jpg").getImage();
	private static final DisplayMode modes1[] ={
			new DisplayMode(1920,1080,32,0),
			new DisplayMode(1920,1080,24,0),
			new DisplayMode(1920,1080,16,0),
	};
	
	public void loadPics(){
		Image pic1 = new ImageIcon("images\\sprite_1.png").getImage();
		Image pic2= new ImageIcon("images\\sprite_2.png").getImage();
		Image pic8= new ImageIcon("images\\sprite_3.png").getImage();
		Image pic3= new ImageIcon("images\\devil_1.png").getImage();
		Image pic4= new ImageIcon("images\\devil_3.png").getImage();
		Image pic5= new ImageIcon("images\\obstacle_1.png").getImage();
		hero= new Animation();
		devil = new Animation();
		thing = new Animation();
		back = new Animation();
		hero.addScene(pic1, 150);
		hero.addScene(pic2, 150);
		hero.addScene(pic8, 150);
		devil.addScene(pic3, 200);
		devil.addScene(pic4, 200);
		thing.addScene(pic5, 200);
		back.addScene(pic6, 50);
		sprite = new sprite(hero);
		evil = new sprite(devil);
		rod = new sprite(thing);
		bock = new sprite(back);
		bockn = new sprite(back);
		
		rod.setx(900);
		bock.setx(0f);
		bockn.setx(1920);
		bock.setdx(-0.3f);
		bockn.setdx(-0.3f);
		rod.setdx(-0.3f);
		evil.sety(600);
		evil.setx(1920);
		sprite.sety(evil.gety()+evil.getH()-sprite.getH());
		rod.sety(evil.gety()+evil.getH()-rod.getH());
		sprite.setdx(0);
		evil.setdx(-0.3f);
		sprite.setdy(0);
		evil.setdy(0);
	}
	
public void run(){
		
		s1 = new ScreenManager();
		s1.getFrame().addKeyListener(this);
			
		try{
			DisplayMode dm = s1.findFirstMode(modes1);
			s1.setFullScreen(dm);
			loadPics();
			movieLoop();
		}finally{
			s1.restoreScreen();
		}
		
	}


	public void restore(){
		s1.restoreScreen();		
	}

	//main movie loop

	public void movieLoop(){
		long startingTime = System.currentTimeMillis();
		long cumTime = startingTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime+=timePassed;
			if(k==0){
			time += timePassed;
			score=time/100;
			}
			update(timePassed);
			Graphics2D g = s1.getGraphics();
			s1.getFrame().setFont(new Font("Arial", Font.BOLD, 36));
			draw(g);
			g.dispose();
			s1.update();
			
			try{
				Thread.sleep(50);
			}catch(Exception exc){}
			
		}
		
		
	}
	

	public void draw(Graphics g){
		if(k==0){
		g.drawImage(bock.getsImage(),Math.round(bock.getx()),Math.round(bock.gety()),null);
		g.drawImage(bockn.getsImage(),Math.round(bockn.getx()),Math.round(bockn.gety()),null);
		g.drawImage(sprite.getsImage(),Math.round(sprite.getx()),Math.round(sprite.gety()), null);
		g.drawImage(rod.getsImage(),Math.round(rod.getx()),Math.round(rod.gety()), null);
		g.drawImage(evil.getsImage(),Math.round(evil.getx()),Math.round(evil.gety()), null);
		g.drawString("SCORE : "+score, 100, 100);
		}
		else{
			g.drawImage(pic6,0,0,null);
			g.drawString("YOUR SCORE IS " + score, s1.getW()/2-300,s1.getH()/2-200);
			g.drawString("PRESS ESCAPE TO EXIT", s1.getW()/2-300, s1.getH()/2);
		}
		}	
	
	public void update(long timePassed){
		if(/*k==1&&*/sprite.getx()+(5*sprite.getW())/6 >= evil.getx() && sprite.getx() + sprite.getW()/3 <=evil.getx() + evil.getW() && sprite.gety() + (sprite.getH()/3) >= evil.gety()){
			k=2;
			try{
				Thread.sleep(2000);
			}catch(Exception wow){}
			//stop();
		}
		if(/*k==1&&*/sprite.getx()+(5*sprite.getW())/6>= rod.getx() && sprite.getx() + sprite.getW()/3 <=rod.getx() + rod.getW() && sprite.gety() + (sprite.getH()/2) >= rod.gety()){
			k=2;
			try{
				Thread.sleep(2000);
			}catch(Exception wow){}
			//stop();
		}
		if((score)%100==0){
			float f = 1.06f*evil.getdx();
			evil.setdx(f);
			rod.setdx(f);
			bock.setdx(f);
			bockn.setdx(f);
		}
		if(sprite.gety() <= 150){
			sprite.setdy(-(sprite.getdy()));
			sprite.setx(100);
			m=0;
		}
		if(sprite.gety() <= 300){
			//sprite.setdy(-(sprite.getdy()));
			sprite.setx(50);
			m=0;
		}
		if(sprite.gety()>=evil.gety()+evil.getH()-sprite.getH() && m==0){
				sprite.sety(evil.gety()+evil.getH()-sprite.getH());
				sprite.setdy(0);
				m=1;
			}
		if(evil.getx() + evil.getW() <= 0)
		{
			evil.setx(1920);
			sprite.setx(0);
		}
		if(bock.getx() + bock.getW() <= 0)
		{
			bock.setx(1920);
		}
		if(bockn.getx() + bockn.getW() <= 0)
		{
			bockn.setx(1920);
		}
		if(rod.getx() + rod.getW() <= 0)
		{
			rod.setx(1920);
			sprite.setx(0);
		}
		
		sprite.update(timePassed);
		evil.update(timePassed);
		rod.update(timePassed);
		bock.update(timePassed);
		bockn.update(timePassed);
	}
	
	
	public void Jump(){
		if(m==1){
		sprite.setdy(-0.6f);
		}
	}
	
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ESCAPE){
			//restore();
			running = false;
		}
		else if(keyCode == KeyEvent.VK_SPACE){
			Jump();
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		e.consume();
	}
	
	public void keyTyped(KeyEvent e){
		e.consume();
	}
}
