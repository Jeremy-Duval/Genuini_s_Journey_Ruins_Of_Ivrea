package shop;
import shop.Item;
import javax.ejb.SessionContext;
public class CartBean implements javax.ejb.SessionBean {

	private SessionContext ctx;
	public void ejbCreate()
		throws javax.ejb.CreateException,
		javax.ejb.EJBException,
		java.sql.SQLException {
		throw new UnsupportedOperationException();
	}
	public void addItem(Item item, int qty) throws javax.ejb.EJBException {
		throw new UnsupportedOperationException();
	}
	public void removeItem(Item item, int qty) throws javax.ejb.EJBException {
		throw new UnsupportedOperationException();
	}
	public int itemCount() throws javax.ejb.EJBException {
		throw new UnsupportedOperationException();
	}
	public boolean transact() throws javax.ejb.EJBException {
		throw new UnsupportedOperationException();
	}
	public CartBean() {

	}
	public void setSessionContext(SessionContext ctx)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException {
		this.ctx = ctx;
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
		java.rmi.RemoteException {

	}
}
