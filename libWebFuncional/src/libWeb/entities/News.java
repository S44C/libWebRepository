package libWeb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News implements Serializable
{
	private static final long serialVersionUID = 1L;
}
