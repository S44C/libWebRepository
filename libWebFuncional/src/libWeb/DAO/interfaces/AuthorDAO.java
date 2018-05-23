package libWeb.DAO.interfaces;

import libWeb.entities.Author;

public interface AuthorDAO 
{
	public void save(Author author);
	public Author getAuthor(String name);
	public void remove(Author author);
	public void update(Author author);
}
