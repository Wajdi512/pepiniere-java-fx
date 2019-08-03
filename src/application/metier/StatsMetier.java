package application.metier;

import application.dao.AbstractCommonCriteria;
import application.statsbeans.StatsBean;

public interface StatsMetier {

	 StatsBean getStatsBeanFacture(AbstractCommonCriteria abs);
	 StatsBean getStatsBeanBons(AbstractCommonCriteria abs);

}
