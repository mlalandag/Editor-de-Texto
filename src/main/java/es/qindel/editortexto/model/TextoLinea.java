package es.qindel.editortexto.model;

import io.swagger.annotations.ApiModelProperty;

public class TextoLinea {
	
	@ApiModelProperty(notes = "Nombre del Documento")
	private String 	nombreDocumento;
	@ApiModelProperty(notes = "Numero de la linea")
	private int numeroLinea;
	@ApiModelProperty(notes = "Texto de la linea")
	private String 	textoLinea;
	
	
	public TextoLinea() {
	}

	public TextoLinea(String nombreDocumento, int numeroLinea, String textoLinea) {
		this.nombreDocumento = nombreDocumento;
		this.numeroLinea = numeroLinea;
		this.textoLinea = textoLinea;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public int getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public String getTextoLinea() {
		return textoLinea;
	}

	public void setTextoLinea(String textoLinea) {
		this.textoLinea = textoLinea;
	}

}
