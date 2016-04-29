package game.controller;

import game.Actor;

public interface ActorController {
	public void decidePressed(Actor actor);
	public void cancelPressed(Actor actor);
}
