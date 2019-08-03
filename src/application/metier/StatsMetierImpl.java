package application.metier;

import application.dao.AbstractCommonCriteria;
import application.dao.BonDeLivraisonDaoImpl;
import application.dao.BonDeLivraisonDaoInterface;
import application.dao.FactureDao;
import application.dao.FactureDaoImpl;
import application.statsbeans.StatsBean;

public class StatsMetierImpl implements StatsMetier {

	private FactureDao factureDao;
	private BonDeLivraisonDaoInterface bonDao;

	@Override
	public StatsBean getStatsBeanFacture(AbstractCommonCriteria abc) {
		StatsBean stat = new StatsBean();
		stat.setNomClient(abc.getClient());
		stat.setFacture(factureDao.getFactureCriteria(abc));
		Double total = factureDao.getTotalCriteria(abc);
		double paye = factureDao.getMontantPayeCriteria(abc);
		stat.setTotalPaye(paye);
		stat.setTotal(total);
		stat.setCredit(total - factureDao.getMontantPayeCriteria(abc));
		// TODO Auto-generated method stub
		return stat;
	}

	@Override
	public StatsBean getStatsBeanBons(AbstractCommonCriteria abc) {
		// TODO Auto-generated method stub
		StatsBean stat = new StatsBean();
		stat.setNomClient(abc.getClient());
		stat.setBons(bonDao.getBonDeLivraisonCriteria(abc));
		Double total = bonDao.getTotalCriteria(abc);
		stat.setTotal(total);
		double paye = bonDao.getMontantPayeCriteria(abc);
		stat.setTotalPaye(paye);
		stat.setCredit(total - paye);
		// TODO Auto-generated method stub
		return stat;
	}

	public StatsMetierImpl() {
		super();
		this.factureDao = new FactureDaoImpl();
		this.bonDao = new BonDeLivraisonDaoImpl();
	}

}
