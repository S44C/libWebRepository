package libWeb.beans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import libWeb.DAO.implementation.AuditDAOImpl;
import libWeb.DAO.interfaces.AuditDAO;
import libWeb.entities.Audit;
import libWeb.util.Correo;

@ManagedBean
public class CorreoBean 
{
	private String subject;
	private String bodyText;
	
	@ManagedProperty("#{userBean}")
	private UserBean userBean;
	
	public CorreoBean()
	{
	}
	
	public UserBean getUserBean()
	{
		return userBean;
	}
	
	public void setUserBean(UserBean bean)
	{
		userBean = bean;
	}
	
	public String getSubject()
	{
		return subject;
	}
	
	public String getBodyText()
	{
		return bodyText;
	}
	
	public void setSubject(String sub)
	{
		subject = sub;
	}
	
	public void setBodyText(String body)
	{
		bodyText = body;
	}
	
	public String enviarCorreo()
	{
		
		AuditDAO daoAudit = new AuditDAOImpl();

		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("user");
		audit.setUserId(userBean.darUsuario().getId());
		audit.setTableId(userBean.darUsuario().getId());
		audit.setOperation("EnvCorreo");
		audit.setIp(userBean.darDireccionIp());
		daoAudit.save(audit);
		
		Correo.enviarCorreo(userBean.getEmailUser(), subject, bodyText);
		
		return "gestionUsuarios";
	}
}