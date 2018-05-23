package libWeb.DAO.implementation;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import libWeb.DAO.interfaces.LeftOverBookDAO;
import libWeb.entities.Leftoverbook;
import libWeb.util.HibernateUtil;

public class LeftOverBookDAOimpl implements LeftOverBookDAO
{
	@Override
	public void save(Leftoverbook overBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(overBook);
		t.commit();
	}

	@Override
	public Leftoverbook getLeftOverBook(Leftoverbook overBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Leftoverbook) session.load(Leftoverbook.class, overBook);
	}

	@Override
	public void remove(Leftoverbook overBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(overBook);
		t.commit();
	}

	@Override
	public void update(Leftoverbook overBook)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(overBook);
		t.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Leftoverbook> list() 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Leftoverbook").list();
		t.commit();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Leftoverbook> getLibrosUsuatio(int id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List librosUsuario = session.createQuery("from Leftoverbook where userId='"+id+"'").list();
		t.commit();

		return librosUsuario;
	}
	
}
