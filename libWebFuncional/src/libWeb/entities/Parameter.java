package libWeb.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parameter database table.
 * 
 */
@Entity
@Table(name = "parameter")
public class Parameter implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;

	private String descriptionParameter;

	private int numberValue;

	private String parameterCode;

	private String parameterType;

	private String textValue;

	public Parameter() 
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

	@Column(name = "descriptionParameter")
	public String getDescriptionParameter()
	{
		return this.descriptionParameter;
	}

	public void setDescriptionParameter(String descriptionParameter)
	{
		this.descriptionParameter = descriptionParameter;
	}

	@Column(name = "numberValue")
	public int getNumberValue()
	{
		return this.numberValue;
	}

	public void setNumberValue(int numberValue)
	{
		this.numberValue = numberValue;
	}

	@Column(name = "parameterCode")
	public String getParameterCode() 
	{
		return this.parameterCode;
	}

	public void setParameterCode(String parameterCode) 
	{
		this.parameterCode = parameterCode;
	}

	@Column(name = "parameterType")
	public String getParameterType()
	{
		return this.parameterType;
	}

	public void setParameterType(String parameterType) 
	{
		this.parameterType = parameterType;
	}

	@Column(name = "textValue")
	public String getTextValue() 
	{
		return this.textValue;
	}

	public void setTextValue(String textValue) 
	{
		this.textValue = textValue;
	}
}