package org.quintessens.controller;

import java.util.Map;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.quintessens.Utils;
import org.quintessens.UtilsJsf;

public class AppController implements PhaseListener {
	
	private static final long serialVersionUID = 1L;

	public void beforePhase(PhaseEvent phase) {
		String url = UtilsJsf.getXSPContext().getHistoryUrl(0);
		String page = Utils.strLeft(
				url.substring(url.lastIndexOf("/") + 1, url
				.length()), ".");

		String packageName = "org.quintessens.view";
		String className = page + "Bean";
		System.out.println("beforePhase, className:" + className);
		Class<?> cl = null;
		Object pb = null;
		try {
			cl = Class.forName(packageName + "." + className);
			pb = cl.newInstance();

		} catch (Exception e) {
		}
		Map<String, Object> viewScope = UtilsJsf.getViewScope();
		viewScope.put("page", pb);
	}

	public void afterPhase(PhaseEvent phase) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}



