package com.allopathy.management.data.model;

import java.util.UUID;

import com.allopathy.management.constant.ClassIdConstants;

import lombok.Data;

@Data
public class MaterialMedica {

	public MaterialMedica() {
		super();
	}

	public MaterialMedica(String id, String medicalId, Repertory mind,
			Repertory head, Repertory eyes, Repertory ears, Repertory nose,
			Repertory face, Repertory mouth, Repertory tongue, Repertory taste,
			Repertory gums, Repertory teeth, Repertory throat,
			Repertory stomach, Repertory abdomen, Repertory urinary,
			Repertory male, Repertory female, Repertory respiratory,
			Repertory circulatory, Repertory locomotor, Repertory nervous,
			Repertory fever, Repertory skin, Repertory generalities,
			Repertory modalities, String classId) {
		super();
		this.id = id;
		this.medicalId = medicalId;
		this.mind = mind;
		this.head = head;
		this.eyes = eyes;
		this.ears = ears;
		this.nose = nose;
		this.face = face;
		this.mouth = mouth;
		this.tongue = tongue;
		this.taste = taste;
		this.gums = gums;
		this.teeth = teeth;
		this.throat = throat;
		this.stomach = stomach;
		this.abdomen = abdomen;
		this.urinary = urinary;
		this.male = male;
		this.female = female;
		this.respiratory = respiratory;
		this.circulatory = circulatory;
		this.locomotor = locomotor;
		this.nervous = nervous;
		this.fever = fever;
		this.skin = skin;
		this.generalities = generalities;
		this.modalities = modalities;
		this.classId = classId;
	}

	String id;
	
	String medicalId;
	
	Repertory mind;
	
	Repertory head;
	
	Repertory eyes;
	
	Repertory ears;
	
	Repertory nose;
	
	Repertory face;
	
	Repertory mouth;
	
	Repertory tongue;
	
	Repertory taste;
	
	Repertory gums;
	
	Repertory teeth;
	
	Repertory throat;
	
	Repertory stomach;
	
	Repertory abdomen;
	
	Repertory urinary; 
	
	Repertory male; 
	
	Repertory female; 
	
	Repertory  respiratory;
	
	Repertory circulatory;
	
	Repertory locomotor;
	
	Repertory nervous;
	
	Repertory fever;
	
	Repertory skin;
	
	Repertory generalities;
	
	Repertory modalities;
	
	
	String classId = ClassIdConstants.MaterialMedica_Doc;
	
	public void setId() {
		this.id = UUID.randomUUID().toString().replace("-", "");
	}
}
