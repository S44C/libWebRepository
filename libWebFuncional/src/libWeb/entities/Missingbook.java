package libWeb.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the missingbooks database table.
 * 
 */
@Entity
@Table(name="missingbooks")
public class Missingbook implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;

	private String author;

	private String date;

	private int edition;

	private String editorial;

	private String name;

	private int userId;

	public Missingbook() 
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
	public String getDate()
	{
		return this.date;
	}

	public void setDate(String date)
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
}