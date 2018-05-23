package libWeb.DAO.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import libWeb.DAO.interfaces.AuthorDAO;
import libWeb.entities.Author;
import libWeb.util.HibernateUtil;

public class AuthorDAOimpl implements AuthorDAO
{

	@Override
	public void save(Author author)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(author);
		t.commit();
	}

	@Override
	public Author getAuthor(String name) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Author) session.load(Author.class, name);
	}

	@Override
	public void remove(Author author)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(author);
		t.commit();
	}

	@Override
	public void update(Author author) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(author);
		t.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Author> list() 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Author").list();
		t.commit();
		return lista;
	}
}