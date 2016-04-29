package game;

public class Weapon {
	
	public static final int
		SWORD = 1, 
		SPEAR = 2, 
		AXE = 4, 
		ROD = 8, 
		ARROW = 16;
	
	private int type;
	private String name;
	private int power;
	private int distance;
	
	public Weapon(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	public int getPower() {
		return this.power;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getDistance() {
		return this.distance;
	}
}
