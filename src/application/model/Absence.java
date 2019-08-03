package application.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="absences")
public class Absence {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="date_absence")
	private Date dateAbsence;
	@JoinColumn(name="cout_id")
	@OneToOne(cascade=CascadeType.DETACH,fetch=FetchType.EAGER)
	private CoutDeJour coutDeJour;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateAbsence() {
		return dateAbsence;
	}
	public void setDateAbsence(Date dateAbsence) {
		this.dateAbsence = dateAbsence;
	}
	public CoutDeJour getCoutDeJour() {
		return coutDeJour;
	}
	public void setCoutDeJour(CoutDeJour coutDeJour) {
		this.coutDeJour = coutDeJour;
	}

}
