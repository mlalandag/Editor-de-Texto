package es.qindel.editortexto.model;

import io.swagger.annotations.ApiModelProperty;

public class TextoDocumento {
	
	@ApiModelProperty(notes = "Nombre del Documento")
	private String 	nombreDocumento;
	@ApiModelProperty(notes = "Texto completo del Documento")
	private String 	textoDocumento;
	
	
	public TextoDocumento() {
	}

	public TextoDocumento(String nombreDocumento, String textoDocumento) {
		this.nombreDocumento = nombreDocumento;
		this.textoDocumento = textoDocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getTextoDocumento() {
		return textoDocumento;
	}

	public void setTextoDocumento(String textoDocumento) {
		this.textoDocumento = textoDocumento;
	}

}
