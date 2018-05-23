package libWeb.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import libWeb.DAO.implementation.AuthorDAOimpl;
import libWeb.DAO.interfaces.AuthorDAO;
import libWeb.entities.Author;

@ManagedBean
@SessionScoped
public class AuthorBean
{
	private Author autor;
	private DataModel<Author> listaAutores;

	public String prepararAdicionarAutor() 
	{
		autor = new Author();
		return "";
	}

	public String prepararModificarAutor() 
	{
		autor = (Author) (listaAutores.getRowData());
		return "";
	}

	public String eliminarAutor() 
	{
		Author autorTemp = (Author)(listaAutores.getRowData());
		AuthorDAO dao = new AuthorDAOimpl();
		dao.remove(autorTemp);

		return "";
	}

	public String adicionarAutor() 
	{
		AuthorDAO dao = new AuthorDAOimpl();
		dao.save(autor);
		return "";
	}

	public String modificarAutor() 
	{
		AuthorDAO dao = new AuthorDAOimpl();
		dao.update(autor);
		return "";
	}

	public Author getAutor() 
	{
		return autor;
	}

	public void setAutor(Author pAutor) 
	{
		autor = pAutor;
	}

	public DataModel<Author> getListarAutores() 
	{
		List<Author> lista = new AuthorDAOimpl().list();
		listaAutores = new ListDataModel<>(lista);
		return listaAutores;
	}

}
