package libWeb.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import libWeb.DAO.implementation.ParameterDAOimpl;
import libWeb.DAO.interfaces.ParameterDAO;
import libWeb.entities.Parameter;

@ManagedBean
@SessionScoped
public class ParameterBean
{
	private Parameter parametro;

	private DataModel<Parameter> listaParametros;

	public ParameterBean()
	{
		parametro = new Parameter();
	}
	
	public String prepararAdicionarParametro() 
	{
		parametro = new Parameter();
		return "";
	}

	public String prepararModificarParametro() 
	{
		parametro = (Parameter) (listaParametros.getRowData());
		return "";
	}

	public String eliminarParametro()
	{
		Parameter ParametroTemp = (Parameter) (listaParametros.getRowData());
		ParameterDAO dao = new ParameterDAOimpl();
		dao.update(ParametroTemp);
		return "";
	}

	public String adicionarParametro() 
	{
		ParameterDAO dao = new ParameterDAOimpl();
		dao.save(parametro);
		return "";
	}

	public String modificarParametro() 
	{
		ParameterDAO dao = new ParameterDAOimpl();
		dao.update(parametro);
		return "";
	}

	public Parameter getParametro() 
	{
		return parametro;
	}

	public void setParametro(Parameter pParametro) 
	{
		parametro = pParametro;
	}

	public DataModel<Parameter> getListarParametros() 
	{
		List<Parameter> lista = new ParameterDAOimpl().list();
		listaParametros = new ListDataModel<>(lista);
		return listaParametros;
	}

}
