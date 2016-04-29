package game;

public class Actor {
	
	private String name;
	private String image;
	
	private int hp;
	private int mp;
	private int str;
	private int vit;
	private int agi;
	
	private Weapon weapon;
	
	public Actor(String name, String image) {
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	public Weapon getWeapon() {
		return this.weapon;
	}
}
