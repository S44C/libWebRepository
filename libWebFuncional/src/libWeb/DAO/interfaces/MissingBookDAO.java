package libWeb.DAO.interfaces;

import libWeb.entities.Missingbook;

public interface MissingBookDAO 
{
	public void save(Missingbook missBook);
	public Missingbook getMissingBook(Missingbook missBook);
	public void remove(Missingbook missBook);
	public void update(Missingbook missBook);
}
