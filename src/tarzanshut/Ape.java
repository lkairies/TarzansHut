package tarzanshut;

public class Ape extends Thread{
	public enum Position {
		TOP, BOTTOM;
	}
	
	private Position position;
	private Vine vine;
	
	public Ape(Position startpos, Vine vine){
		this.position = startpos;
		this.vine = vine;
	}
	
	public void move(){
		try {
		if(position == Position.TOP){
			//move down
			vine.climb(Vine.Direction.DOWN); // enqueue in direction down
			sleep(1000);
			vine.leave();
			position = Position.BOTTOM;
		} else {
			// move up
			vine.climb(Vine.Direction.UP); // enqueue in direction up
			sleep(1000);
			vine.leave();
			position = Position.TOP;
		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(true){
			try {
				// do nothing
				sleep((long)( (1000) + Math.random()*20000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			move();
		}
	}
	
}
