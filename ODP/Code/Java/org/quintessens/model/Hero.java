package org.quintessens.model;

import java.io.Serializable;
import java.util.UUID;

public class Hero implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final Heroes app;

	private String id = null;
	private String species = null;
	private String name = null;
	private String description = null;
	private String power = null;

	public Hero(Heroes app) {
		this.app = app;
	}

	protected Heroes getApp() {
		return app;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void generateNewId() {
		id = UUID.randomUUID().toString();
	}

	public void save() throws Exception {
		getApp().getDao().saveHero(this);
	}

	public void remove() throws Exception {
		getApp().getDao().removeHero(this);
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

}


