package shop;
import shop.ItemPK;
import shop.Item;
import java.util.Collection;
public interface ItemHome extends javax.ejb.EJBHome {

	public Collection findByPrice(double minPrice, double maxPrice)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException,
		javax.ejb.FinderException;

	public Item findByPrimaryKey(ItemPK primaryKey)
		throws javax.ejb.EJBException,
		java.rmi.RemoteException,
		javax.ejb.FinderException;

}
