package libWeb.DAO.interfaces;
import java.util.List;
import libWeb.entities.Audit;

public interface AuditDAO
{
	public void save(Audit auditoria);
	public List<Audit> list();
	public List<Audit> listUser(int userId);
}
