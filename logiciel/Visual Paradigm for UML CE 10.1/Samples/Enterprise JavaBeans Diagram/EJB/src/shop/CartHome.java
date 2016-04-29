package shop;
import shop.Cart;
public interface CartHome extends javax.ejb.EJBHome {

	public Cart create()
		throws javax.ejb.CreateException,
		javax.ejb.EJBException,
		java.rmi.RemoteException,
		java.sql.SQLException;

}
