package libWeb.DAO.interfaces;
import libWeb.entities.Parameter;

public interface ParameterDAO 
{
	public void save(Parameter missBook);
	public Parameter getParameter(Parameter missBook);
	public void remove(Parameter missBook);
	public void update(Parameter missBook);
}
