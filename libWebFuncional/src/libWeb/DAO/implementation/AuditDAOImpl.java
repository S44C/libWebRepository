package libWeb.DAO.implementation;
import libWeb.entities.Audit;
import libWeb.DAO.interfaces.AuditDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import libWeb.util.HibernateUtil;

public class AuditDAOImpl implements AuditDAO 
{

	@Override
	public void save(Audit auditoria) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(auditoria);
		t.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audit> list() 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Audit").list();
		t.commit();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audit> listUser(int pUserId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Audit where userId =" + pUserId).list();
		t.commit();
		return lista;
	}
	
	

}