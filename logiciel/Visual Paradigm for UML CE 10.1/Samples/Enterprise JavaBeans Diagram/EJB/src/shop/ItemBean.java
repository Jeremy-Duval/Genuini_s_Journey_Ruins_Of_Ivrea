package shop;
import javax.ejb.EntityContext;
public abstract class ItemBean implements javax.ejb.EntityBean {

	private EntityContext ctx;
	public ItemBean() {

	}
	public void setEntityContext(EntityContext ctx)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {
		this.ctx = ctx;
	}
	public void unsetEntityContext()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {
		this.ctx = null;
	}
	public void ejbActivate()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {

	}
	public void ejbPassivate()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {

	}
	public void ejbRemove()
		throws javax.ejb.EJBException,
		javax.ejb.RemoveException {

	}
	public void ejbStore()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {

	}
	public void ejbLoad()
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {

	}
	public abstract String getName() throws javax.ejb.EJBException;

	public abstract void setName(String name) throws javax.ejb.EJBException;

	public abstract double getPrice() throws javax.ejb.EJBException;

	public abstract void setPrice(double price) throws javax.ejb.EJBException;

	public abstract int getBarcode() throws javax.ejb.EJBException;

	public abstract void setBarcode(int barcode) throws javax.ejb.EJBException;

}
