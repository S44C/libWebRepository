package libWeb.DAO.implementation;
import libWeb.DAO.interfaces.UserDAO;
import libWeb.entities.User;
import libWeb.util.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAOimpl implements UserDAO
{

	@Override
	public void save(User usuario) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(usuario);
		t.commit();
	}

	public User getUsuario(int id)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (User) session.load(User.class, id);
	}

	@Override
	public void update(User usuario) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(usuario);
		t.commit();
	}

	@Override
	public void remove(User usuario) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(usuario);
		t.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list() 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from User").list();
		t.commit();
		return lista;
	}

	@Override
	public User getUsuarioLogin(String userName, String password)
	{
		User rta = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from User where userName='"+userName+"' AND password='"+password+"'").list();
		t.commit();

		if(!lista.isEmpty())
		{
			rta = (User)lista.get(0);
		}

		return rta;
	}

	@Override
	public User getUsuarioPorNombre(String userName)
	{
		User rta = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from User where userName='"+userName+"'").list();
		t.commit();

		if(!lista.isEmpty())
		{
			rta = (User)lista.get(0);
		}

		return rta;
	}

	@Override
	public int darUltimaConsulta() 
	{
		int rta= 0;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		User user = (User) session.createQuery("from User ORDER by id desc").list().get(0);
		t.commit();

		if(user != null)
		{
			rta = user.getId();
		}

		return rta;
	}

	@Override
	public User getUsuarioCorreo(String correo) 
	{
		User rta = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from User where emailAddress='"+correo+"'").list();
		t.commit();

		if(!lista.isEmpty())
		{
			rta = (User)lista.get(0);
		}

		return rta;
	}

	@Override
	public User getUsuarioPorId(int id) 
	{
		User rta = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from User where id='"+id+"'").list();
		t.commit();

		if(!lista.isEmpty())
		{
			rta = (User)lista.get(0);
		}

		return rta;
	}


}
