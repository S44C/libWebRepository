package libWeb.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import libWeb.DAO.implementation.MissingBookDAOimpl;
import libWeb.DAO.interfaces.MissingBookDAO;
import libWeb.entities.Missingbook;

@ManagedBean
@SessionScoped
public class MissingBookBean
{
	private Missingbook libroFaltante;
	private DataModel<Missingbook> listaFaltantes;

	public String prepararAdicionarlibroFaltante() 
	{
		libroFaltante = new Missingbook();
		return "";
	}

	public String prepararModificarlibroFaltante() 
	{
		libroFaltante = (Missingbook) (listaFaltantes.getRowData());
		return "";
	}

	public String eliminarlibroFaltante() 
	{
		Missingbook libroFaltanteTemp = (Missingbook)(listaFaltantes.getRowData());
		MissingBookDAO dao = new MissingBookDAOimpl();
		dao.remove(libroFaltanteTemp);
		return "";
	}

	public String adicionarlibroFaltante() 
	{
		MissingBookDAO dao = new MissingBookDAOimpl();
		dao.save(libroFaltante);
		return "";
	}

	public String modificarlibroFaltante() 
	{
		MissingBookDAO dao = new MissingBookDAOimpl();
		dao.update(libroFaltante);
		return "";
	}

	public Missingbook getlibroFaltante() 
	{
		return libroFaltante;
	}

	public void setlibroFaltante(Missingbook plibroFaltante) 
	{
		libroFaltante = plibroFaltante;
	}

	public DataModel<Missingbook> getListarlibroFaltantes() 
	{
		List<Missingbook> lista = new MissingBookDAOimpl().list();
		listaFaltantes = new ListDataModel<Missingbook>(lista);
		return listaFaltantes;
	}
	

}
