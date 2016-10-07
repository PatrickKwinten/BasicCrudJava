package org.quintessens.model;

import java.util.List;

public interface DaoI {

	abstract Heroes getApp();		

	abstract List<Hero> getAllHeroes() throws Exception;
	abstract Hero getHeroesById(String id) throws Exception;
	abstract void saveHero(Hero model) throws Exception;
	abstract void removeHero(Hero model) throws Exception;
}