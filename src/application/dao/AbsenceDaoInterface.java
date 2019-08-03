package application.dao;

import java.util.Date;
import java.util.List;

import application.model.Absence;

public interface AbsenceDaoInterface {

	public  List<Absence> getAbsenceByCdj(int id);
	public  List<Absence> getAbsenceByOuvrierByDate(Date start,Date end,int id);
	public void addAbsence(Absence absence);
	public long countPresence(Date start,Date end,int id);
	List<Absence> listAbsenceBetwenDate(Date start, Date end, Integer[] ids);
	void removeAbsence(int id);
	List<Absence> listAbsence(Integer[] ids);

}
