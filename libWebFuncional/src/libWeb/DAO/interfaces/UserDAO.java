package libWeb.DAO.interfaces;

import java.util.List;

import libWeb.entities.User;

public interface UserDAO 
{
	public void save(User usuario);
	public User getUsuario(int id);
	public void remove(User usuario);
	public void update(User usuario);
	public User getUsuarioLogin(String userName, String password);
	public User getUsuarioCorreo(String correo);
	public User getUsuarioPorNombre(String userName);
	public List<User> list();
	public int darUltimaConsulta();
	public User getUsuarioPorId(int id);
}
