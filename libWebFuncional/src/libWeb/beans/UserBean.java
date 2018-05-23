package libWeb.beans;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import libWeb.DAO.interfaces.AuditDAO;
import libWeb.DAO.interfaces.ParameterDAO;
import libWeb.DAO.interfaces.UserDAO;
import libWeb.DAO.implementation.*;
import libWeb.entities.Audit;
import libWeb.entities.Parameter;
import libWeb.entities.User;
import libWeb.util.Correo;
import libWeb.util.Util;

/**
 * Clase que representa el Bean de Usuario (UserBean)
 * @author Daniel Beltrán Penagos
 * @author Santiago Correa Vera 
 * <br><br>
 * <center> <b> Universidad El Bosque<br>
 * Ingeniería de Sistemas - Programación 2<br>
 * Profesor Wilson Rojas Reales <br>
 * Proyecto libWeb</b> </center>
 */
@ManagedBean
@SessionScoped
public class UserBean
{
	/**
	 * Usuario actual en el sistema
	 */
	private User usuario;

	/**
	 * Instancia de usuario para su modificación
	 */
	private User usuarioModificar;

	/**
	 * Lista de usuarios en libWeb
	 */
	private DataModel<User> listaUsuarios;

	/**
	 * Email del usuario seleccionado para el envio 
	 */
	private String emailUser;

	/**
	 * TODO Completar Documentación
	 */
	private int busquedaId;

	/**
	 * Correo del de la sesión del usuario abierta 
	 */
	private String correo;

	/**
	 * Arreglo con el alfabeto
	 */
	private char[] alfabeto = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z'};

	/**
	 * Constructor del Bean<br> 
	 * <b> post:</b> Se inicializó el usuario
	 */
	public UserBean()
	{
		usuario = new User();
	}

	/**
	 * Retorna el correo del usuario
	 * @return String con el correo del usuario
	 */
	public String getCorreo()
	{
		return correo;
	}

	/**
	 * Cambia el correo del usuario<br>
	 * <b> post:</b> Se cambio el correo del usuario<br>
	 * @param pCorreo String con el correo del usuario
	 */
	public void setCorreo(String pCorreo)
	{
		correo = pCorreo;
	}

	/**
	 * TODO Completar Documentación
	 * @return
	 */
	public int getBusquedaId()
	{
		return busquedaId;
	}

	/**
	 * TODO Completar Documentación
	 * @param id
	 */
	public void setBusquedaPorId(int id)
	{
		busquedaId = id;
	}

	/**
	 * Inicializa el usuario para adicionarlo<br>
	 * <b> post:</b> Se inicializo el usuario con Active='A',
	 *  DateLastPassword = Tiempo actual y tipo de usuario = 'Normal'
	 * @return Cadena de texto vacía
	 */
	public String prepararAdicionarUsuario() 
	{
		usuario = new User();
		usuario.setActive("A");
		Calendar c = GregorianCalendar.getInstance();
		usuario.setDateLastPassword(c.getTime());
		usuario.setUserType("Normal");
		usuario.setAddress("No disponible");
		return "";
	}

	/**
	 * Prepara un usuario para modificar<br>
	 * <b> pre:</b> listaUsuarios != null<br>
	 * <b> post:</b> Se inicializó el usuarioModificar como el usuario 
	 *  en la lista a modificar<br>
	 * @return Cadena de texto vacía
	 */
	public String prepararModificarUsuario() 
	{
		usuarioModificar = (User) (listaUsuarios.getRowData());
		return "";
	}

	/**
	 * Activa un usuario<br>
	 * <b> pre:</b> listaUsuarios!=null <br>
	 * <b> post:</b> Se actualizó el usuario en la base de datos 
	 *  y se registró una auditoría en la base de datos<br> 
	 * @return Cadena de texto con la pagína "Gestión de Usuarios" del administrador
	 */
	public String activarUsuario()
	{
		User usuarioTemp = (User) (listaUsuarios.getRowData());

		AuditDAO daoAudit = new AuditDAOImpl();
		UserDAO dao = new UserDAOimpl();

		usuarioTemp.setActive("A");
		dao.update(usuarioTemp);

		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("user");
		audit.setUserId(usuario.getId());
		audit.setTableId(audit.getTableId());
		audit.setOperation("Activó");
		audit.setIp(darDireccionIp());
		daoAudit.save(audit);

		return "gestionAdmon";
	}

	/**
	 * Inactiva a un usuario (Diferente al administrador)<br>
	 * <b> pre:</b> listaUsuarios != null <br>
	 * <b> post:</b> Se inactivó el usuario y se actualizaron sus propiedades en la base de datos,
	 *  también se registró una auditoría en la base de datos
	 * @return Cadena de texto con la pagína "Gestión de Usuarios" del administrador
	 */
	public String eliminarUsuario()
	{
		User usuarioTemp = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOimpl();
		AuditDAO daoAudit = new AuditDAOImpl();

		usuario = usuarioTemp;

		if(!usuario.getUserType().trim().equals("ADMIN"))
		{
			usuarioTemp.setActive("I");
			dao.update(usuarioTemp);

			Audit audit = new Audit();
			audit.setCreateDate(new Date());
			audit.setTableName("user");
			audit.setUserId(usuarioTemp.getId());
			audit.setTableId(audit.getTableId());
			audit.setOperation("Eliminó");
			audit.setIp(darDireccionIp());
			daoAudit.save(audit);
		}

		return "gestionAdmon";
	}

	/**
	 * Obtiene el ultimo registro de la tabla de auditorias
	 * @return ultimo registro de la tabla de auditorias 
	 */
	public int darUltimoIdRegistro()
	{
		UserDAO daoUser = new UserDAOimpl();
		return daoUser.darUltimaConsulta();
	}

	/**
	 * Adiciona un usuario al sistema<br>
	 * <b> post:</b> Se agregó un usuario a la base de datos y 
	 *  se registró una auditoría de registro<br>
	 * @return Cadena de texto con la página de inicio
	 */
	public void adicionarUsuario() 
	{
		try
		{
			UserDAO dao = new UserDAOimpl();
			AuditDAO daoAudit = new AuditDAOImpl();

			String tmpContrasena = generarContrasena();
			String passHex = Util.getStringMessageDigest(tmpContrasena, Util.MD5);
			usuario.setPassword(passHex);
			usuario.setUserType("NUEVO");
			dao.save(usuario);

			Audit audit = new Audit();
			audit.setCreateDate(new Date());
			audit.setTableName("user");
			audit.setUserId(usuario.getId());
			audit.setTableId(audit.getTableId());
			audit.setIp(darDireccionIp());
			audit.setOperation("Registro");
			daoAudit.save(audit);

			String cuerpo = "Bienvenido a libWeb " + usuario.getFullName() + ", \n"
					+ "El siguiente pasó para tu registro es ingresar y cambiar la contraseña, para eso diseñamos una clave única para tí. Puedes"
					+ " ingresar con esta contraseña  " + tmpContrasena + " a libWeb y empezar a disfrutar del trueque de libros. Si tienes algún problema"
					+ " escribenos a admonlibweb@gmail.com. \n \n Gracias por registrarte. \n Atentamente, Equipo de libWeb" ;

			Correo.enviarCorreo(usuario.getEmailAddress(), "Registro libWeb", cuerpo);

			usuario = new User();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../usuario/index.xhtml");

		}
		catch (Exception e)
		{
			
		}
	}

	/**
	 * Modifica el usuario<br>
	 * <b> post:</b> Se modificó el usuario y se actualizó en la base de datos, 
	 *  también se agregó una auditoría en la base de datos
	 * @return 
	 */
	public String modificarUsuario() 
	{
		UserDAO dao = new UserDAOimpl();
		dao.update(usuarioModificar);

		AuditDAO daoAudit = new AuditDAOImpl();
		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("user");
		audit.setUserId(usuarioModificar.getId());
		audit.setTableId(audit.getTableId());
		audit.setIp(darDireccionIp());
		audit.setOperation("Modificó");

		daoAudit.save(audit);

		return "gestionAdmon";
	}

	/**
	 * Retorna el usuario actual en el sistema
	 * @return User  usuario actual en el sistema
	 */
	public User getUsuario() 
	{
		return usuario;
	}

	/**
	 * Cambia el usuario actual en el sistema
	 * @param usuario Nuevo User a reemplazar
	 */
	public void setUsuario(User usuario) 
	{
		this.usuario = usuario;
	}

	/**
	 * Retorna un DataModel con la lista de usuarios del sistema
	 * @return DataModel con los usuarios del sistema 
	 */
	public DataModel<User> getListarUsuarios() 
	{
		List<User> lista = new UserDAOimpl().list();
		listaUsuarios = new ListDataModel<>(lista);
		return listaUsuarios;
	}

	/**
	 * TODO Completar Documentación
	 * @return
	 */
	public DataModel<User> buscarUsuarioId()
	{
		UserDAO userdao = new UserDAOimpl();
		User u = userdao.getUsuario(busquedaId);

		ArrayList<User> list = new ArrayList<>();
		list.add(u);
		List<User> listaActualizada = list;

		listaUsuarios = new ListDataModel<>(listaActualizada);


		return listaUsuarios;
	}

	/**
	 * Cambia la contraseña de un usuario<br>
	 * <b> post:</b> Se cambió y acualizó en la base de datos la contraseña del usuario, 
	 * su tipo a normal y su DateLastPassword por el tiempo actual, también se registro 
	 * una auditoria de tipo CambioCon 
	 */
	public void cambioContraseña()
	{
		String pass1 = usuario.getPassword();
		String passHex = Util.getStringMessageDigest(pass1, Util.MD5);

		boolean hasUppercase = !usuario.getPassword().equals(usuario.getPassword().toLowerCase());
		boolean hasLowercase = !usuario.getPassword().equals(usuario.getPassword().toUpperCase());

		if(!(hasLowercase||hasUppercase))
		{
			/**
			 * TODO Falta colocar las excepciones y actulizar los redireccionamientos
			 */
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("errorContrasenia.xhtml");
			}
			catch (Exception e) 
			{

			}
		}
		else
		{
			/**
			 * TODO Falta colocar las excepciones y actulizar los redireccionamientos
			 */
			try
			{
				UserDAO dao = new UserDAOimpl();
				usuario.setPassword(passHex);
				usuario.setActive("A");
				usuario.setUserType("NORMAL");
				usuario.setDateLastPassword(new Date());
				dao.update(usuario);

				AuditDAO daoAudit = new AuditDAOImpl();
				Audit audit = new Audit();
				audit.setCreateDate(new Date());
				audit.setTableName("user");
				audit.setUserId(usuario.getId());
				audit.setTableId(usuario.getId());
				audit.setOperation("CambioCon");
				audit.setIp(darDireccionIp());
				daoAudit.save(audit);

				FacesContext.getCurrentInstance().getExternalContext().redirect("perfilUsuario.xhtml");
			}
			catch (Exception e) 
			{

			}
		}
	}


	/**
	 * Valida la entrada de un usuario con repspecto al nickName y password alojados en la
	 * base de datosbr>
	 * <b> post <b> redirecciona a la página correspondiente al tipo de usuario 
	 */
	public void validarEntrada()
	{
		UserDAO dao = new UserDAOimpl();

		String passHex = libWeb.util.Util.getStringMessageDigest(usuario.getPassword(), "MD5");
		User user = dao.getUsuarioLogin(usuario.getUserName(), passHex);

		/*
		 * TODO Arreglar intentos - cambiar para que salga en un pop up y no en otra página 
		 */
		if(user != null)
		{
			if(user.getActive().equals("I"))
			{
				try 
				{
					FacesContext.getCurrentInstance().getExternalContext().redirect("bloqueo.xhtml");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				return;
			}
			if(user.getUserType().equals("NUEVO"))
			{
				try
				{
					usuario = user;
					FacesContext.getCurrentInstance().getExternalContext().redirect("cambioContrasena.xhtml");
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			else
			{
				if(user.getUserType().equals("ADMIN") && passHex.equals(user.getPassword()))
				{	
					try
					{
						AuditDAO daoAudit = new AuditDAOImpl();

						Audit audit = new Audit();
						audit.setCreateDate(new Date());
						audit.setTableName("user");
						audit.setUserId(user.getId());
						audit.setTableId(user.getId());
						audit.setOperation("Ingresar");
						audit.setIp(darDireccionIp());
						daoAudit.save(audit);

						user.setAttempts(0);
						dao.update(user);
						usuario = user;
						FacesContext.getCurrentInstance().getExternalContext().redirect("../administrador/principalAdmon.xhtml");
					}
					catch (IOException e) 
					{
						Logger logger = Logger.getLogger("Problema de Login");
						logger.error("error en el login");
					}
				}
				else
				{
					Date date = user.getDateLastPassword();
					@SuppressWarnings("deprecation")
					int day = date.getDate();
					int dayActuali = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

					if(user.getUserType().equals("NUEVO") &&(dayActuali - day) >= 3)
					{
						String tmpContra = generarContrasena();
						String passHex2 = libWeb.util.Util.getStringMessageDigest(tmpContra, "MD5");
						user.setPassword(passHex2);
						dao.update(user);

						ParameterDAO daoParameter = new ParameterDAOimpl();
						Parameter param = new Parameter();
						param.setParameterType("U");
						param.setParameterCode("CLAVE");
						param.setDescriptionParameter("Cambiar Clave");
						param.setTextValue("ACTIVO");
						param.setNumberValue(3);
						daoParameter.save(param);

						Correo.enviarCorreo(user.getEmailAddress(), "Cambio de contraseña", "Hola " + user.getFullName() + " por tu seguridad es necesario cambiar la contraseña,  te enviamos una contraseña temporal para que la puedas cambiar : " + tmpContra);
					}

					else
					{
						usuario = user;

						if(usuario.getUserType().equals("NORMAL") && passHex.equals(user.getPassword()))
						{
							try
							{
								usuario = user;

								AuditDAO daoAudit = new AuditDAOImpl();

								Audit audit = new Audit();
								audit.setCreateDate(new Date());
								audit.setTableName("user");
								audit.setUserId(user.getId());
								audit.setTableId(user.getId());
								audit.setOperation("Ingresar");
								audit.setIp(darDireccionIp());
								daoAudit.save(audit);

								user.setAttempts(0);
								dao.update(user);
								FacesContext.getCurrentInstance().getExternalContext().redirect("../usuario/mundoUser.xhtml");
							}
							catch (IOException e) 
							{
								e.printStackTrace();
							}
						}
					}
				}
			}	
		}
		else
		{
			User vali = dao.getUsuarioPorNombre(usuario.getUserName());

			if(vali != null)
			{
				verificarIntentos(vali);
				aumentarIntentos(vali);
				dao.update(vali);
			}
		}

	}

	/**
	 * Cambia el email del usuario seleccinado
	 * <b> post <b> cambio del valor del atributo
	 * @param email email a cambiar 
	 */
	public void setEmailUser(String email)
	{
		emailUser = email;
	}


	/**
	 * Obtiene el email el email del ususario a enviar el correo<b>
	 * @return email del usuario seleccionado
	 */
	public String getEmailUser()
	{
		return emailUser;
	}

	/**
	 * Redirecciona a la página para el envio del correo al usuario seleccionado<br>
	 * <b> pre <b> Selección de un usuario
	 * <b> post <b> redireccionó a la página correspondiente
	 */
	public void enviarCorreo()
	{

		/**
		 * TODO falta realizar el mensaje de la excepción y cambiar el redireccionamiento 
		 */
		try
		{
			emailUser = listaUsuarios.getRowData().getEmailAddress();
			FacesContext.getCurrentInstance().getExternalContext().redirect("enviarCorreo.xhtml");
		}
		catch (Exception e) 
		{

		}
	}

	/**
	 * Retorna el arreglo de caracteres con el alfabeto
	 * @return char[] Arreglo con el alfabeto
	 */
	public char[] darAlfaveto()
	{
		return alfabeto;
	}

	/**
	 * Genera una contraseña aleatoria con diez digitos alfanumericos
	 * @return String con la contraseña
	 */
	public String generarContrasena()
	{
		String contrasena = "";

		int numLetra = 0;
		int numero = 0;

		int i = 0;

		while(i < 5)
		{
			numLetra = (int)(Math.random() * (24-0+1) + 0);
			numero = (int)(Math.random() * (9-0+1) + 0);

			char letra = alfabeto[numLetra];

			contrasena +=  numero + String.valueOf(letra);

			i++;
		}

		return contrasena;
	}

	/**
	 * Retorna la dirección ip del usuario
	 * @return String con la dirección ip del usuario
	 */
	public String darDireccionIp()
	{
		String rta = "";
		try 
		{
			InetAddress direccion = InetAddress.getLocalHost();
			rta = direccion.getHostAddress();

			/*
			 * TODO Verificar si la dirección ip si es la del usuario
			 */
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		return rta;
	}

	/**
	 * Retorna el usuario
	 * @return User usuario
	 */
	public User darUsuario()
	{
		return usuario;
	}

	/**
	 * Cierra la sesión (Devuelve al usuario como uno nuevo) y retorna la página de inicio
	 */
	public void cerrarSesion()
	{
		/**
		 * TODO Falta el mensaje de la excepción
		 */
		try 
		{
			usuario = new User();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../usuario/index.xhtml");
		}
		catch (Exception e)
		{

		}
	}

	/**
	 * Aumenta los intentos de un usuario al entrar<br>
	 * <b> post:</b> Se aumento en uno los intentos del usuario<br>
	 * @param usuario User al cual se le aumentarán los intentos
	 */
	public void aumentarIntentos(User usuario)
	{
		if(!(usuario.getUserType().equals("ADMIN") || usuario.getUserType().equals("admin") ))
		{
			usuario.setAttempts(usuario.getAttempts()+1);
		}
	}

	/**
	 * Verifica los intetos de acceso al sistema, al tercer intento bloquea al usuario
	 * @param usuario Usuario en el momento del login
	 */
	public void verificarIntentos(User usuario)
	{
		int intentos = usuario.getAttempts();

		if(intentos >= 3 && !(usuario.getUserType().equals("ADMIN")))
		{
			usuario.setActive("I");
			usuario.setAttempts(0);

			AuditDAO daoAudit = new AuditDAOImpl();

			Audit audit = new Audit();
			audit.setCreateDate(new Date());
			audit.setTableName("user");
			audit.setUserId(usuario.getId());
			audit.setTableId(usuario.getId());
			audit.setOperation("Bloqueo");
			audit.setIp(darDireccionIp());
			daoAudit.save(audit);
		}
	}

	/**
	 * TODO Arreglar para mostrar la recuperación en un pop up
	 * @return
	 */
	public String recuperarContrasena()
	{
		return "recuperarContrasena";
	}

	/**
	 * Envia un correo al usuario para realizar el cambio de la contraseña pertinente<br>
	 * <b> post <b> Se envia al correo una contraseña temporal para el cambio de esta.
	 */
	public void olvidarContrasena()
	{
		UserDAO userDAO = new UserDAOimpl();
		User user = userDAO.getUsuarioCorreo(correo);

		if(user != null)
		{
			try
			{
				user.setUserType("NUEVO");

				String contra = generarContrasena();
				String passHex = Util.getStringMessageDigest(contra, Util.MD5);
				user.setPassword(passHex);
				userDAO.update(user);

				AuditDAO daoAudit = new AuditDAOImpl();

				Audit audit = new Audit();
				audit.setCreateDate(new Date());
				audit.setTableName("user");
				audit.setUserId(user.getId());
				audit.setTableId(user.getId());
				audit.setOperation("SolCambioContra");
				audit.setIp(darDireccionIp());
				daoAudit.save(audit);

				Correo.enviarCorreo(user.getEmailAddress(), "Cambio de Contraseña", "Hola "+ user.getFullName() +", \n Solicitaste el cambio de tu contraseña y "
						+ " por eso te enviamos una temporal para que ingreses a libWeb. \nTu contraseña temporal es " + contra +"\n \nSi no solicitaste el cambio por favor "
						+ "comunicate a admonlibweb@gmail.com para revertir el proceso. \nFeliz trueque de libros! \nAtentamente, \nEquipo de libWeb");

				FacesContext.getCurrentInstance().getExternalContext().redirect("../usuario/index.xhtml");
			}
			catch (Exception e) 
			{

			}
		}

	}
}