package ac.za.dirisa.sst.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Institution {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String name;
    private String abbreviation;
    
	public Institution() {}
	
	public Institution(Long id, String name, String abbreviation) {
		super();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		return "Institution [id=" + id + ", name=" + name + ", abbreviation=" + abbreviation + "]";
	}
}
