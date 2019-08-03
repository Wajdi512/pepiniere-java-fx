package application.io;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import application.model.Panier;

public interface DocumentInterface extends Serializable{

	public int getId();
	public double getMontant();
	public double getRemise();
	public double getAvance();
	public String getNomClient();
	public String getPrenomClient();
	public String getTelClient();
	public String getChauffeur();
	public String getDirection();
	public String getMatricule();
	public Date getDate();
	public List<Panier> getProduitsAchetes();
	public double getMontantPaye();
}
