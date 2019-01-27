package es.qindel.editortexto.model;

import io.swagger.annotations.ApiModelProperty;

public class Linea {
	
	@ApiModelProperty(notes = "Identificador único de la linea")
	private int    idLinea;
	@ApiModelProperty(notes = "Identificador único del documento")
	private int    idDoc;
	@ApiModelProperty(notes = "Texto de la linea")
	private String textoLinea;
	@ApiModelProperty(notes = "Número de la linea")
	private int    numeroLinea;
	@ApiModelProperty(notes = "Versión del documento")
	private int    version;
	@ApiModelProperty(notes = "Enlace a la linea siguiente")
	private int    proximaLinea;
	
	public Linea (int idLinea,  int idDoc, String textoLinea, int numeroLinea, int version, int proximaLinea) {
		
		this.idLinea 		= idLinea;
		this.idDoc 			= idDoc;
		this.textoLinea 	= textoLinea;
		this.numeroLinea 	= numeroLinea;
		this.version 		= version;
		this.proximaLinea 	= proximaLinea;
		
	}

	public Linea() {
	}

	public int getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}
	
	public int getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	public String getTextoLinea() {
		return textoLinea;
	}

	public void setTextoLinea(String textoLinea) {
		this.textoLinea = textoLinea;
	}

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(int ordenLinea) {
		this.numeroLinea = ordenLinea;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getProximaLinea() {
		return proximaLinea;
	}

	public void setProximaLinea(int proximaLinea) {
		this.proximaLinea = proximaLinea;
	}
	
	

}
