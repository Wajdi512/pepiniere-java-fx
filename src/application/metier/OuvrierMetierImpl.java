package application.metier;

import java.util.Date;
import java.util.List;

import application.dao.AbsenceDaoImpl;
import application.dao.AbsenceDaoInterface;
import application.dao.AbstractCommonCriteria;
import application.dao.AvanceDaoImpl;
import application.dao.AvanceDaoInterface;
import application.dao.CoutDeJourDaoImpl;
import application.dao.CoutJourDaoInterface;
import application.dao.OuvrierDaoImpl;
import application.dao.OuvrierDaoInterface;
import application.model.Absence;
import application.model.Avance;
import application.model.CoutDeJour;
import application.model.Ouvrier;

public class OuvrierMetierImpl implements OuvrierMetierInterface {

	private OuvrierDaoInterface ouvrierImpl;
	private AbsenceDaoInterface absenceImpl;
	private AvanceDaoInterface avanceImpl;
	private CoutJourDaoInterface coutJour;
	public OuvrierMetierImpl() {
		super();
		// TODO Auto-generated constructor stub
		this.ouvrierImpl = new OuvrierDaoImpl();
		this.absenceImpl = new AbsenceDaoImpl();
		this.avanceImpl = new AvanceDaoImpl();
		this.coutJour = new CoutDeJourDaoImpl();
	}

	@Override
	public void ajouterOuvrier(Ouvrier o) {
		// TODO Auto-generated method stub
		ouvrierImpl.ajouterOuvrier(o);
	}

	@Override
	public void mettreAjour(Ouvrier o) {
		// TODO Auto-generated method stub
		ouvrierImpl.update(o);
	}

	@Override
	public void supprimerOuvrier(int id) {
		// TODO Auto-generated method stub
		ouvrierImpl.supprimerOuvrier(id);
	}

	@Override
	public List<Ouvrier> listeOuvrier() {
		// TODO Auto-generated method stub
		return ouvrierImpl.listeOuvrier();
	}

	@Override
	public List<Absence> listAbsencesCdj(int idCdj) {
		// TODO Auto-generated method stub
		return absenceImpl.getAbsenceByCdj(idCdj);
	}

	@Override
	public List<Avance> listAvances(int idOuvrier) {
		// TODO Auto-generated method stub
		return avanceImpl.getAllAvanceByOuvrier(idOuvrier);
	}

	@Override
	public void ajouterAvance(Avance avance) {
		// TODO Auto-generated method stub
		avanceImpl.addAvance(avance);
	}

	@Override
	public List<Avance> listAvancesByMonth(Date startDate, Date endDate, int idOuvrier) {
		// TODO Auto-generated method stub
		return avanceImpl.getAvanceOuvrierByMonth(startDate, endDate, idOuvrier);
	}

	@Override
	public List<Absence> listAbsencesByDate(Date startDate, Date endDate, int idOuvrier) {
		// TODO Auto-generated method stub
		return absenceImpl.getAbsenceByOuvrierByDate(startDate, endDate, idOuvrier);
	}

	@Override
	public void ajouterAbsence(Absence absence) {
		absenceImpl.addAbsence(absence);
	}

	@Override
	public long nombreDeJourTravailler(Date startDate, Date endDate, int idOuvrier) {
		// TODO Auto-generated method stub
		return absenceImpl.countPresence(startDate, endDate, idOuvrier);
	}

	@Override
	public double getSommeAvance(Date startDate, Date endDate, int idOuvrier) {
		// TODO Auto-generated method stub
		return avanceImpl.getSommeAvance(startDate, endDate, idOuvrier);
	}

	@Override
	public void addCoutJourOuvrier(CoutDeJour cdj) {
		// TODO Auto-generated method stub
		coutJour.add(cdj);
	}

	@Override
	public List<CoutDeJour> getCoutJourOuvrier(Ouvrier o) {
		// TODO Auto-generated method stub
		return coutJour.getCoutDeJourByOuvrier(o.getId());
	}

	@Override
	public List<CoutDeJour> getCoutDeJourByOuvrierBetwenDate(int id, Date start, Date end) {
		return getCoutDeJourByOuvrierBetwenDate(id, start, end);
	}

	@Override
	public List<Absence> listAbsenceBetwenDate(Date start, Date end, Integer[] ids) {
		// TODO Auto-generated method stub
		return absenceImpl.listAbsenceBetwenDate(start, end, ids);
	}

	@Override
	public void supprimerAbsence(Absence absence) {
		// TODO Auto-generated method stub
		absenceImpl.removeAbsence(absence.getId());
	}

	@Override
	public void supprimerAvance(Avance avance) {
		// TODO Auto-generated method stub
		avanceImpl.removeAvance(avance.getId());
	}

	@Override
	public List<Ouvrier> listeOuvrierByCriteria(AbstractCommonCriteria criteria) {
		// TODO Auto-generated method stub
		return ouvrierImpl.listeOuvrierByCriteria(criteria);
	}

	@Override
	public List<Absence> listAbsence(Integer[] id) {
		// TODO Auto-generated method stub
		return absenceImpl.listAbsence(id);
	}

}
