using System;

namespace game {

	public class Weapon {

		public const int 
			SWORD = 1, 
			SPEAR = 2, 
			AXE = 4, 
			ROD = 8, 
			ARROW = 16;

		private int type;
		public int Type {
			set {
				this.type = value;
			}
			get {
				return this.type;
			}
		}
		public int A {
			get {
				return this.type;
			}
		}

		private String name;
		public String Name {
			set {
				this.name = value;
			}
			get {
				return this.name;
			}
		}

		private int power;
		public int Power {
			set {
				this.power = value;
			}
			get {
				return this.power;
			}
		}

		private int distance;
		public int DISTANCE {
			set {
				this.distance = value;
			}
			get {
				return this.distance;
			}
		}
	}
}