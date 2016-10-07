package org.quintessens.model;

import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DaoXml implements Serializable, DaoI {
	private static final long serialVersionUID = 1L;

	private static final String FILE_NAME = "c:/Domino/data/heroes.xml";

	Map<String, Hero> allHeroes = new HashMap<String, Hero>();

	private final Heroes app;

	public DaoXml(Heroes squad) throws Exception {
		this.app = squad;

		File squadFile = new File(FILE_NAME);
		if (!squadFile.isFile())
			return;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(squadFile);
		Element squadElement = document.getDocumentElement();
		Node node = squadElement.getFirstChild();
		while (node != null) {
			if ("hero".equals(node.getNodeName())) {

				NamedNodeMap attributes = node.getAttributes();

				Hero animal = squad.getInstanceOfHero();
				animal.setId(attributes.getNamedItem("id").getNodeValue());
				animal.setSpecies(attributes.getNamedItem("species")
						.getNodeValue());
				animal.setName(attributes.getNamedItem("name").getNodeValue());
				animal.setDescription(attributes.getNamedItem("description")
						.getNodeValue());
				animal.setPower(attributes.getNamedItem("power")
						.getNodeValue());
				allHeroes.put(animal.getId(), animal);

			}
			node = node.getNextSibling();
		}
	}

	public Heroes getApp() {
		return app;
	}

	public List<Hero> getAllHeroes() throws Exception {
		List<Hero> result = new ArrayList<Hero>();
		for (Entry<String, Hero> entry : allHeroes.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}

	public Hero getHeroesById(String id) throws Exception {
		return allHeroes.get(id);
	}

	public void saveHero(Hero model) throws Exception {
		String id = model.getId();

		if (!allHeroes.containsKey(id)) {
			allHeroes.put(id, model);
		}

		writeXmlFile();
	}

	public void removeHero(Hero model) throws Exception {
		String id = model.getId();
		if (allHeroes.containsKey(id)) {
			allHeroes.remove(id);
		}
		writeXmlFile();
	}

	private void writeXmlFile() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("squad");
		doc.appendChild(root);
		for (Hero animal : getAllHeroes()) {
			Element node = doc.createElement("hero");
			node.setAttribute("id", animal.getId());
			node.setAttribute("species", animal.getSpecies());
			node.setAttribute("name", animal.getName());
			node.setAttribute("description", animal.getDescription());
			node.setAttribute("power", animal.getPower());
			root.appendChild(node);
		}
		File squadFile = new File(FILE_NAME);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(squadFile);
		transformer.transform(source, result);
	}

	

}
