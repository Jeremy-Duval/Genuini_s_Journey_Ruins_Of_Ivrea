package shop;
public interface Item extends javax.ejb.EJBObject {

	public String getName()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public void setName(String name)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public double getPrice()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public void setPrice(double price)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

	public int getBarcode()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException;

}
