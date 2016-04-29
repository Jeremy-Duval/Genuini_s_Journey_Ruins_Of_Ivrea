package game.renderer;

import game.Actor;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class NormalActorRenderer implements ActorRenderer {
	
	public void render(Graphics g, Actor actor) {
		g.drawImage(
			new ImageIcon(actor.getImage()).getImage(), 
			0, 0, null
		);
	}
}
