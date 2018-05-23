package libWeb.beans;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import libWeb.DAO.implementation.AuditDAOImpl;
import libWeb.DAO.implementation.LeftOverBookDAOimpl;
import libWeb.DAO.implementation.UserDAOimpl;
import libWeb.DAO.interfaces.AuditDAO;
import libWeb.DAO.interfaces.LeftOverBookDAO;
import libWeb.DAO.interfaces.UserDAO;
import libWeb.entities.Audit;
import libWeb.entities.Leftoverbook;
import libWeb.entities.User;

@ManagedBean
@SessionScoped
public class LeftOverBookBean
{
	private Leftoverbook libroSobrante;
	private DataModel<Leftoverbook> listaSobrantes;
	private String direccion;


	@ManagedProperty("#{userBean}")
	private UserBean userBean;

	private String emailUser;
	
	private String libro;

	private User user;

	public LeftOverBookBean()
	{
		libroSobrante = new Leftoverbook();
	}

	public UserBean getUserBean()
	{
		return userBean;
	}

	public void setUserBean(UserBean user)
	{
		userBean = user;
	}

	public String getEmailUser()
	{
		return emailUser;
	}

	public void setEmailUser(String pCorreo)
	{
		emailUser = pCorreo;
	}
	
	public String getLibro()
	{
		return libro;
	}

	public String prepararAdicionarLibroSobrante() 
	{
		libroSobrante = new Leftoverbook();
		return "agregarLibro";
	}

	public String prepararModificarLibroSobrante() 
	{
		libroSobrante = (Leftoverbook) (listaSobrantes.getRowData());
		return "editarLibro";
	}

	public String eliminarLibroSobrante() 
	{
		Leftoverbook LibroSobranteTemp = (Leftoverbook)(listaSobrantes.getRowData());
		LeftOverBookDAO dao = new LeftOverBookDAOimpl();
		dao.remove(LibroSobranteTemp);
		return "";
	}

	public String adicionarLibroSobrante() 
	{
		LeftOverBookDAO dao = new LeftOverBookDAOimpl();
		libroSobrante.setDate(new Date());
		libroSobrante.setUserName(userBean.getUsuario().getUserName());
		libroSobrante.setUserId(userBean.getUsuario().getId());
		dao.save(libroSobrante);

		AuditDAO daoAudit = new AuditDAOImpl();
		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("leftOverBook");
		audit.setUserId(userBean.getUsuario().getId());
		audit.setTableId(audit.getTableId());
		audit.setIp(userBean.darDireccionIp());
		audit.setOperation("AgregoLibro");

		daoAudit.save(audit);

		return "misLibros";
	}

	public String modificarLibroSobrante() 
	{
		LeftOverBookDAO dao = new LeftOverBookDAOimpl();
		libroSobrante.setDate(new Date());
		dao.update(libroSobrante);

		AuditDAO daoAudit = new AuditDAOImpl();
		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("leftOverBook");
		audit.setUserId(userBean.getUsuario().getId());
		audit.setTableId(audit.getTableId());
		audit.setIp(userBean.darDireccionIp());
		audit.setOperation("ModificoLibro");

		daoAudit.save(audit);

		return "misLibros";
	}

	public Leftoverbook getLibroSobrante() 
	{
		return libroSobrante;
	}

	public void setLibroSobrante(Leftoverbook pLibroSobrante) 
	{
		libroSobrante = pLibroSobrante;
	}

	public DataModel<Leftoverbook> getListarLibroSobrantes() 
	{
		List<Leftoverbook> lista = new LeftOverBookDAOimpl().list();
		listaSobrantes = new ListDataModel<>(lista);
		return listaSobrantes;
	}

	public DataModel<Leftoverbook> getLibrosUser() 
	{
		LeftOverBookDAO leftOver = new LeftOverBookDAOimpl();
		listaSobrantes = new ListDataModel<>(leftOver.getLibrosUsuatio(userBean.getUsuario().getId()));
		return listaSobrantes;
	}

	public String enviarCorreo()
	{
		int idUsuario = listaSobrantes.getRowData().getUserId();
		libro = listaSobrantes.getRowData().getName();
		UserDAO daoUser = new UserDAOimpl();
		User user = daoUser.getUsuarioPorId(idUsuario);
		emailUser = user.getEmailAddress();

		return "enviarCorreoUser";
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User usuario)
	{
		user = usuario;
	}

	public String getDireccion() 
	{
		return direccion;
	}

	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}


	public String prepararDireccion()
	{
		direccion = userBean.getUsuario().getAddress();
		System.out.println(direccion);
		return "geocode";
	}
	
	public String eliminarLibro()
	{
		Leftoverbook book = listaSobrantes.getRowData();
		
		LeftOverBookDAO daoBook = new LeftOverBookDAOimpl();
		daoBook.remove(book);
		
		AuditDAO daoAudit = new AuditDAOImpl();
		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("leftOverBook");
		audit.setUserId(userBean.getUsuario().getId());
		audit.setTableId(audit.getTableId());
		audit.setIp(userBean.darDireccionIp());
		audit.setOperation("eliminoLibro");
		daoAudit.save(audit);
		
		return "misLibros";
	}
	
	public String darDireccionIp()
	{
		String rta = "";
		try 
		{
			InetAddress direccion = InetAddress.getLocalHost();
			rta = direccion.getHostAddress();
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		return rta;
	}
}
