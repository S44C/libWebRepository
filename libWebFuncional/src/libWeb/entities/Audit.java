package libWeb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the audit database table.
 * 
 */
@Entity
@Table(name = "audit")
public class Audit implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private String operation;

	private int tableId;

	private String tableName;

	private int userId;
	
	private String ip;

	public Audit() {
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

	@Column(name = "createDate")
	public Date getCreateDate() 
	{
		return this.createDate;
	}

	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}

	@Column(name = "operation")
	public String getOperation()
	{
		return this.operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	@Column(name = "tableId")
	public int getTableId()
	{
		return this.tableId;
	}

	public void setTableId(int tableId)
	{
		this.tableId = tableId;
	}

	@Column(name = "tableName")
	public String getTableName()
	{
		return this.tableName;
	}

	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
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
	
	@Column(name ="ip")
	public String getIp()
	{
		return ip;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
}