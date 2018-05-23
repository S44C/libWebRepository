package libWeb.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the leftoverbooks database table.
 * 
 */
@Entity
@Table(name="leftoverbooks")
public class Leftoverbook implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;

	private String author;

	private Date date;

	private int edition;

	private String editorial;

	private String name;

	private int userId;
	
	private String userName;

	public Leftoverbook() 
	{
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() 
	{
		return this.id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	@Column(name = "author")
	public String getAuthor()
	{
		return this.author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	@Column(name = "date")
	public Date getDate()
	{
		return this.date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	@Column(name = "edition")
	public int getEdition()
	{
		return this.edition;
	}

	public void setEdition(int edition)
	{
		this.edition = edition;
	}

	@Column(name = "editorial")
	public String getEditorial() 
	{
		return this.editorial;
	}

	public void setEditorial(String editorial)
	{
		this.editorial = editorial;
	}

	@Column(name = "name")
	public String getName()
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	@Column(name = "userId")
	public int getUserId()
	{
		return this.userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public String getUserName() 
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
}