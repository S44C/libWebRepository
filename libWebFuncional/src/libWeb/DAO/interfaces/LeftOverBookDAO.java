package libWeb.DAO.interfaces;

import java.util.List;

import libWeb.entities.Leftoverbook;

public interface LeftOverBookDAO 
{
	public void save(Leftoverbook overBook);
	public Leftoverbook getLeftOverBook(Leftoverbook overBook);
	public void remove(Leftoverbook overBook);
	public void update(Leftoverbook overBook);
	public List<Leftoverbook> getLibrosUsuatio(int id);
}
