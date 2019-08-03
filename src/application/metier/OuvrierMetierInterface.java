package application.metier;

import java.util.Date;
import java.util.List;

import application.dao.AbstractCommonCriteria;
import application.model.Absence;
import application.model.Avance;
import application.model.CoutDeJour;
import application.model.Ouvrier;

public interface OuvrierMetierInterface {

	public void ajouterOuvrier(Ouvrier o);

	public void mettreAjour(Ouvrier o);

	public void supprimerOuvrier(int id);

	public List<Ouvrier> listeOuvrier();

	public List<Ouvrier> listeOuvrierByCriteria(AbstractCommonCriteria criteria);

	public List<Absence> listAbsencesCdj(int idOuvrier);

	public List<Avance> listAvances(int idOuvrier);

	public void ajouterAvance(Avance avance);

	public void supprimerAvance(Avance avance);

	public List<Avance> listAvancesByMonth(Date startDate, Date endDate, int idOuvrier);

	public List<Absence> listAbsencesByDate(Date startDate, Date endDate, int idOuvrier);

	public void ajouterAbsence(Absence absence);

	public void supprimerAbsence(Absence absence);

	public long nombreDeJourTravailler(Date startDate, Date endDate, int idOuvrier);

	public double getSommeAvance(Date startDate, Date endDate, int idOuvrier);

	public void addCoutJourOuvrier(CoutDeJour cdj);

	public List<CoutDeJour> getCoutJourOuvrier(Ouvrier o);

	public List<CoutDeJour> getCoutDeJourByOuvrierBetwenDate(int id, Date start, Date end);

	public List<Absence> listAbsenceBetwenDate(Date start, Date end, Integer[] id);

	public List<Absence> listAbsence(Integer[] id);

}
