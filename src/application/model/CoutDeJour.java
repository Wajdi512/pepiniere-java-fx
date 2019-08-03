package application.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="cout_jours")
public class CoutDeJour {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="cout")
	private Double cout;
	@Column(name="date_creation")
	private Date dateCreation;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ouvrier_id")
	private Ouvrier ouvrier;
	@JoinColumn(name="cout_id")
	@OneToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
	private List<Absence> joursTravaile;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getCout() {
		return cout;
	}
	public void setCout(Double cout) {
		this.cout = cout;
	}
	public Ouvrier getOuvrier() {
		return ouvrier;
	}
	public void setOuvrier(Ouvrier ouvrier) {
		this.ouvrier = ouvrier;
	}
	public List<Absence> getJoursTravaile() {
		return joursTravaile;
	}
	public void setJoursTravaile(List<Absence> joursTravaile) {
		this.joursTravaile = joursTravaile;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

}
