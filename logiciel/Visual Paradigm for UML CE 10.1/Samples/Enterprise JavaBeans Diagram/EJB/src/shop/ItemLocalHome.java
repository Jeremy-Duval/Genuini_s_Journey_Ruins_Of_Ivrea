package shop;
import shop.ItemPK;
import shop.ItemLocal;
import java.util.Collection;
public interface ItemLocalHome extends javax.ejb.EJBLocalHome {

	public Collection findByPrice(double minPrice, double maxPrice)
		throws javax.ejb.EJBException,
		javax.ejb.FinderException;

	public ItemLocal findByPrimaryKey(ItemPK primaryKey)
		throws javax.ejb.EJBException,
		javax.ejb.FinderException;

}
