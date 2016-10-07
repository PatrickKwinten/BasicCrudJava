package org.quintessens.view;

import java.io.IOException;
import java.io.Serializable;

import org.quintessens.UtilsJsf;
import org.quintessens.model.Hero;
import org.quintessens.model.Heroes;

public class HeroBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String OVERVIEW_XSP = "Heroes.xsp";

	private final Hero model;

	public HeroBean() throws Exception {
		System.out.println("HeroBean contructor");
		String id = UtilsJsf.getParameter("id");
		if (id == null || "".equals(id)) {
			model = getApp().createHero();
		} else {
			model = getApp().getHeroById(id);
			if (model == null) {
				throw new Exception("model id not found (" + id + ")");
			}
		}
	}

	protected Heroes getApp() {
		return (Heroes) UtilsJsf.resolveVariable("app");
	}

	public Hero getModel() {
		return model;
	}

	public void clickSave() throws Exception {
		model.save();
		redirectToOverview();
	}

	public void clickBack() throws Exception {
		redirectToOverview();
	}

	private void redirectToOverview() throws IOException {
		UtilsJsf.sendRedirect(OVERVIEW_XSP);
	}
}
