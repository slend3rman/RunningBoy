import java.awt.Image;

public class sprite {
	private Animation a;
	private float x;
	private float y;
	private float dx;
	private float dy;
	
	public sprite(Animation a){
		this.a=a;
	}
	
	//change postion
	public void update(long timePassed){
		x+=dx*timePassed;
		y+=dy*timePassed;
		a.update(timePassed);
	}
	
	//get x position
	public float getx(){
		return x;
	}
	
	//get y position
	public float gety(){
		return y;
	}	
	
	//set x postion
	public void setx(float x){
		this.x=x;
	}
	
	//set y postion
	public void sety(float y){
		this.y=y;
	}
	
	//get width
	public int getW(){
		return a.getImage().getWidth(null);
	}
	
	//get width
	public int getH(){
		return a.getImage().getHeight(null);
	}
	
	//get x vel
	public float getdx(){
		return dx;
	}
	
	//get y vel
	public float getdy(){
		return dy;
	}	
	
	//set x vel
	public void setdx(float dx){
		this.dx=dx;
	}
	
	//set y vel
	public void setdy(float dy){
		this.dy=dy;
	}
	
	//get sprite Image
	public Image getsImage(){
		return a.getImage();
	}
	
}
