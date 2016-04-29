using System;
using System.Drawing;

using game;

namespace game.renderer {

	public interface ActorRenderer {

		void renderer(Graphics g, Actor actor);
	}
}