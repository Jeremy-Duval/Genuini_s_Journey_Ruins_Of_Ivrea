using System;

namespace game {

	public class Actor {

		private String name;
		public String Name {
			set {
				this.name = value;
			}
			get {
				return this.name;
			}
		}

		private String image;
		public String Image {
			set {
				this.image = value;
			}
			get {
				return this.image;
			}
		}

		private int hp;
		public int HP {
			set {
				this.hp = value;
			}
			get {
				return this.hp;
			}
		}

		private int mp;
		public int MP {
			set {
				this.mp = value;
			}
			get {
				return this.mp;
			}
		}

		private int str;
		public int STR {
			set {
				this.str = value;
			}
			get {
				return this.str;
			}
		}

		private int vit;
		public int VIT {
			set {
				this.vit = value;
			}
			get {
				return this.vit;
			}
		}

		private int agi;
		public int AGI {
			set {
				this.agi = value;
			}
			get {
				return this.agi;
			}
		}

		private Weapon weapon;
		public Weapon Weapon {
			set {
				this.weapon = value;
			}
			get {
				return this.weapon;
			}
		}
	}
}