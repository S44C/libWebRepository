package libWeb.beans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import libWeb.DAO.implementation.AuditDAOImpl;
import libWeb.DAO.interfaces.AuditDAO;
import libWeb.entities.Audit;
import libWeb.util.Correo;

@ManagedBean
public class CorreoBeanUser 
{
	private String subject;
	
	@ManagedProperty("#{leftOverBookBean}")
	private LeftOverBookBean leftOverBookBean;
	
	public CorreoBeanUser()
	{
	}
	
	public LeftOverBookBean getLeftOverBookBean()
	{
		return leftOverBookBean;
	}
	
	public void setLeftOverBookBean(LeftOverBookBean bean)
	{
		leftOverBookBean = bean;
	}
	
	public String getSubject()
	{
		return subject;
	}
	
	public void setSubject(String sub)
	{
		subject = sub;
	}
	
	public String enviarCorreo()
	{
		
		AuditDAO daoAudit = new AuditDAOImpl();

		Audit audit = new Audit();
		audit.setCreateDate(new Date());
		audit.setTableName("user");
		audit.setUserId(leftOverBookBean.getUserBean().getUsuario().getId());
		audit.setTableId(leftOverBookBean.getUserBean().getUsuario().getId());
		audit.setOperation("EnvCorreo");
		audit.setIp(leftOverBookBean.darDireccionIp());
		daoAudit.save(audit);
		
		String cuerpo ="enviado por:" + leftOverBookBean.getUserBean().getUsuario().getUserName() + " " + " estoy interesado en este libro: " + leftOverBookBean.getLibro() +"\n Email de contacto: " + leftOverBookBean.getUserBean().getUsuario().getEmailAddress();
		Correo.enviarCorreo(leftOverBookBean.getEmailUser(), subject, cuerpo);
		
		return "librosMundiales";
	}
}
