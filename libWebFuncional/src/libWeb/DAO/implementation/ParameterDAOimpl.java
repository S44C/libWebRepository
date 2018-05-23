package libWeb.DAO.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import libWeb.DAO.interfaces.ParameterDAO;
import libWeb.entities.Parameter;
import libWeb.util.HibernateUtil;

public class ParameterDAOimpl implements ParameterDAO
{
	@Override
	public void save(Parameter pParam) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(pParam);
		t.commit();
	}

	@Override
	public Parameter getParameter(Parameter pParam) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Parameter) session.load(Parameter.class, pParam);
	}

	@Override
	public void remove(Parameter pParam) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(pParam);
		t.commit();
	}

	@Override
	public void update(Parameter pParam) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(pParam);
		t.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Parameter> list() 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Parameter").list();
		t.commit();
		return lista;
	}

}
