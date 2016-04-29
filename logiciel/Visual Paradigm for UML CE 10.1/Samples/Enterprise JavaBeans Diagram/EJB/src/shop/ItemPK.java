package shop;
public class ItemPK implements java.io.Serializable {

	public int barcode;
	public ItemPK() {

	}
	/**
	 * This method will overwrite during Code Generation.
	 */
	public boolean equals(Object other) {
		boolean equal = false;
		if (other instanceof ItemPK) {
			final ItemPK otherItemPK = (ItemPK) other;
			boolean areEqual = true;
			areEqual &= (otherItemPK.barcode == barcode);

			equal = areEqual;
		}
		return equal;
	}
	/**
	 * This method will overwrite during Code Generation.
	 */

	public int hashCode() {
		int value = 0;

		value += String.valueOf(barcode).hashCode();

		return value;

	}
	public ItemPK(int barcode) {
		this.barcode = barcode;

	}
}
