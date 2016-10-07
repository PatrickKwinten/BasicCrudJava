package org.quintessens.model;

import java.io.Serializable;
import java.util.List;

public class Heroes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Heroes() {
	}
	
	public List<Hero> getAllHeroes() throws Exception {
		return getDao().getAllHeroes();
	}

	public Hero getHeroById(String id) throws Exception {
		return getDao().getHeroesById(id);
	}

	public Hero createHero() throws Exception {
		Hero model = getInstanceOfHero();
		model.generateNewId();
		return model;
	}

	public Hero getInstanceOfHero() {
		return new Hero(this);
	}
	
	
	/*
	 * DAO object
	 */

	private DaoI dao = null;
	public DaoI getDao() throws Exception {
		if (dao == null) {
			dao = new DaoNsf(this);
			//dao = new DaoXml(this);
		}
		return dao;
	}
}



