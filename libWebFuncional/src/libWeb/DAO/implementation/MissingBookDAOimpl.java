package libWeb.DAO.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import libWeb.DAO.interfaces.MissingBookDAO;
import libWeb.entities.Missingbook;
import libWeb.util.HibernateUtil;

public class MissingBookDAOimpl implements MissingBookDAO
{
	@Override
	public void save(Missingbook missBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(missBook);
		t.commit();
	}

	@Override
	public Missingbook getMissingBook(Missingbook missBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Missingbook) session.load(Missingbook.class, missBook);
	}

	@Override
	public void remove(Missingbook missBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(missBook);
		t.commit();
	}

	@Override
	public void update(Missingbook missBook) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(missBook);
		t.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Missingbook> list() 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Missingbook").list();
		t.commit();
		return lista;
	}

}