package org.quintessens;

/*

<!-- 
Copyright 2010 Tim Tripcony
Modified by Tommy Valand - Added some helper functions. No copyright on my parts. Google/Eclipse gave me all the answers.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License
-->

*/

/**
* 
*/
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;
import javax.servlet.http.HttpServletResponse;

import lotus.domino.Database;
import lotus.domino.Session;

import com.ibm.xsp.application.DesignerApplicationEx;
import com.ibm.xsp.component.UIViewRootEx;
import com.ibm.xsp.designer.context.ServletXSPContext;
import com.ibm.xsp.designer.context.XSPContext;

public class UtilsJsf {

	private UtilsJsf() {
	}

	public static Object getBindingValue(String ref) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		return application.createValueBinding(ref).getValue(context);
	}

	public static String getUserName() {
		return (String) getBindingValue("#{context.user.name}");
	}

	public static DesignerApplicationEx getApplication() {
		return (DesignerApplicationEx) getFacesContext().getApplication();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getApplicationScope() {
		return getServletContext().getApplicationMap();
	}

	public static UIComponent getComponent(String id) {
		return getComponent(getViewRoot(), id);
	}

	@SuppressWarnings("unchecked")
	private static UIComponent getComponent(UIComponent component, String id) {
		if (id.equals(component.getId())) {
			return component;
		}

		Iterator<UIComponent> children = component.getFacetsAndChildren();
		while (children.hasNext()) {
			UIComponent child = children.next();
			UIComponent found = getComponent(child, id);
			if (found != null) {
				return found;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getCompositeData() {
		return (Map<String, Object>) resolveVariable("compositeData");
	}

	public static ServletXSPContext getContext() {
		return (ServletXSPContext) resolveVariable("context");
	}

	public static Database getCurrentDatabase() {
		return (Database) resolveVariable("database");
	}

	public static Session getCurrentSession() {
		return (Session) resolveVariable("session");
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@SuppressWarnings("unchecked")
	public static String getParameter(String name) {
		Map<String, String> parameters = (Map<String, String>) resolveVariable("param");
		return parameters.get(name);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getRequestScope() {
		return getServletContext().getRequestMap();
	}

	public static ExternalContext getServletContext() {
		return getFacesContext().getExternalContext();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSessionScope() {
		return getServletContext().getSessionMap();
	}

	private static VariableResolver getVariableResolver() {
		return getApplication().getVariableResolver();
	}

	public static UIViewRootEx getViewRoot() {
		return (UIViewRootEx) getFacesContext().getViewRoot();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getViewScope() {
		return getViewRoot().getViewMap();
	}

	public static XSPContext getXSPContext() {
		return XSPContext.getXSPContext(getFacesContext());
	}

	@SuppressWarnings("unchecked")
	public static void resetComponentAndChildren(UIComponent component) {
		if (component instanceof EditableValueHolder) {
			// Reset value bindings on component
			EditableValueHolder valueHolder = (EditableValueHolder) component;
			valueHolder.setSubmittedValue(null);
			valueHolder.setValue(null);
			valueHolder.setLocalValueSet(false);
		}

		for (UIComponent child : (List<UIComponent>) component.getChildren()) {
			resetComponentAndChildren(child);
		}
	}

	public static String resolveBundleProperty(String bundleName, String propertyName) {
		ResourceBundle bundle = (ResourceBundle) resolveVariable(bundleName);
		return bundle.getString(propertyName);
	}

	public static Object resolveVariable(String name) {
		return getVariableResolver().resolveVariable(FacesContext.getCurrentInstance(), name);
	}

	public static void sendRedirect(String url) throws IOException {
		HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
		response.sendRedirect(url);
	}

}

