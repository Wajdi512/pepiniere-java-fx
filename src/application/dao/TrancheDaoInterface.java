package application.dao;

import java.util.List;

import application.model.Tranche;

public interface TrancheDaoInterface {

	public List<Tranche> getTranchePaye();
	public List<Tranche> getTrancheByIdFacture(int id);
	public List<Tranche> getTrancheByIdBon(int id);
	public void addTranche(Tranche t);
	public void supprimerTranche(int id);
}
