package libWeb.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the author database table.
 * 
 */
@Entity
@Table(name = "author")
public class Author implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;

	private String fullName;

	private String nationality;

	public Author() {
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

	@Column(name = "fullName")
	public String getFullName()
	{
		return this.fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	@Column(name = "nationality")
	public String getNationality()
	{
		return this.nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}
}