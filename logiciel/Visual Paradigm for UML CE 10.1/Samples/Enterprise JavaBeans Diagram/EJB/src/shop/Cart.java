package shop;
import shop.Item;
public interface Cart extends javax.ejb.EJBObject {

	public void addItem(Item item, int qty)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public void removeItem(Item item, int qty)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public int itemCount()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public boolean transact()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

}
