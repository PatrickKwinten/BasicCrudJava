package org.quintessens.view;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.quintessens.UtilsJsf;
import org.quintessens.model.Hero;
import org.quintessens.model.Heroes;

public class HeroesBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String HERO_XSP = "Hero.xsp";

	public HeroesBean() throws Exception{
		System.out.println("HeroesBean contructor");
	}
	
	protected Heroes getApp() {
		return (Heroes) UtilsJsf.resolveVariable("app");
	}

	public List<Hero> getAllHeroes() throws Exception {
		List<Hero> all = getApp().getAllHeroes();

		Collections.sort(all, new Comparator<Hero>() {

			public int compare(Hero a1, Hero a2) {
				String sortValue1 = a1.getSpecies() + " " + a1.getName();
				String sortValue2 = a2.getSpecies() + " " + a2.getName();
				return sortValue1.compareTo(sortValue2);
			}

		});
		return all;
	}

	public String getDisplayText(Hero hero) {
		return hero.getName();
	}
	
	public void clickNew() throws Exception {
		UtilsJsf.sendRedirect(HERO_XSP);
	}

	public void clickRemove(Hero model) throws Exception {
		model.remove();
	}

	public String getUrl(Hero model) {
		return HERO_XSP + "?id=" + model.getId();
	}
}
