package application.dao;

import java.util.Date;
import java.util.List;

import application.model.Avance;

public interface AvanceDaoInterface {

	public List<Avance> getAllAvanceByOuvrier(int idOuvrier);
	public void addAvance(Avance avance);
	public List<Avance> getAvanceOuvrierByMonth(Date startDate,Date endDate,int idOuvrier);
	public Double getSommeAvance(Date startDate,Date endDate,int idOuvrier);
	void removeAvance(int id);
}
