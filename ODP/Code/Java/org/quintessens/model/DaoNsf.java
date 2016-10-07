package org.quintessens.model;

import static org.quintessens.Utils.recycleDominoObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.View;
import org.quintessens.UtilsJsf;

public class DaoNsf implements Serializable, DaoI {
	private static final long serialVersionUID = 1L;

	private static final String LOOKUP_VIEW_SH = "heroes";

	private final Heroes app;	

	public DaoNsf(Heroes squad) {
		this.app = squad;
	}
	

	public Heroes getApp() {
		return app;
	}
	
	
	public List<Hero> getAllHeroes() throws Exception {
		View view = UtilsJsf.getCurrentDatabase().getView(LOOKUP_VIEW_SH);
		Document doc = null;
		List<Hero> result = new ArrayList<Hero>();

		try {
			doc = view.getFirstDocument();

			while (doc != null) {

				Hero model = app.getInstanceOfHero();

				readModelValuesFromDoc(doc, model);

				result.add(model);

				Document nextdoc = view.getNextDocument(doc);
				recycleDominoObjects(doc);
				doc = nextdoc;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			recycleDominoObjects(doc, view);
		}
		return result;
	}

	private void readModelValuesFromDoc(Document doc, Hero model)
			throws NotesException {
		model.setId(doc.getItemValueString("ID"));
		model.setName(doc.getItemValueString("Name"));
		model.setSpecies(doc.getItemValueString("Species"));
		model.setDescription(doc.getItemValueString("Description"));
		model.setPower(doc.getItemValueString("Superpower"));
	}

	private void saveModelValuesToDoc(Hero model, Document doc)
			throws NotesException {
		doc.replaceItemValue("ID", model.getId());
		doc.replaceItemValue("Name", model.getName());
		doc.replaceItemValue("Species", model.getSpecies());
		doc.replaceItemValue("Description", model.getDescription());
		doc.replaceItemValue("Superpower", model.getPower());
		doc.save();
	}

	public Hero getHeroesById(String id) throws Exception {
		View view = UtilsJsf.getCurrentDatabase().getView(LOOKUP_VIEW_SH);
		Document doc = null;
		Hero model = null;

		try {
			doc = view.getDocumentByKey(id);
			if (doc != null) {
				model = app.getInstanceOfHero();
				readModelValuesFromDoc(doc, model);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			recycleDominoObjects(doc, view);
		}
		return model;
	}

	public void saveHero(Hero model) throws Exception {
		View view = null;
		Document doc = null;
		String id = model.getId();

		try {
			view = UtilsJsf.getCurrentDatabase().getView(LOOKUP_VIEW_SH);
			doc = view.getDocumentByKey(id);

			if (doc == null) {
				doc = UtilsJsf.getCurrentDatabase().createDocument();
				doc.replaceItemValue("Form", "Superheroe");
			}
			saveModelValuesToDoc(model, doc);

		} catch (Exception e) {
			throw e;
		} finally {
			recycleDominoObjects(view);
		}
	}

	public void removeHero(Hero model) throws Exception {
		String id = model.getId();

		View view = UtilsJsf.getCurrentDatabase().getView(LOOKUP_VIEW_SH);
		Document doc = null;

		try {
			doc = view.getDocumentByKey(id);
			if (doc != null) {
				doc.remove(true);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			recycleDominoObjects(doc, view);
		}
	}
	
	
	
	
	

}
