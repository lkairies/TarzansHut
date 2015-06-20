package tarzanshut;

public class Main {

	public static void main(String[] args) {
		Vine vine = new Vine();
		int APECOUNT = 100;
		
		
		for(int i = 0; i < APECOUNT; i++){
			Ape ape = new Ape(Ape.Position.BOTTOM, vine);
			ape.start();
		}
		
	}

}
