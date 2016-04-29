package shop;
public interface ItemLocal extends javax.ejb.EJBLocalObject {

	public String getName() throws javax.ejb.EJBException;

	public void setName(String name) throws javax.ejb.EJBException;

	public double getPrice() throws javax.ejb.EJBException;

	public void setPrice(double price) throws javax.ejb.EJBException;

	public int getBarcode() throws javax.ejb.EJBException;

}
