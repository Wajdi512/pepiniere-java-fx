package application.dao;

import java.util.Date;
import java.util.List;

import application.model.CoutDeJour;

public interface CoutJourDaoInterface {

	public void add(CoutDeJour cdj);

	public void delete(int id);

	public List<CoutDeJour> getCoutDeJourByOuvrier(int id);

	public List<CoutDeJour> getCoutDeJourByOuvrierBetwenDate(int id, Date start, Date end);

}
