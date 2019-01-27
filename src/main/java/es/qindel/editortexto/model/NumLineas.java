package es.qindel.editortexto.model;

import io.swagger.annotations.ApiModelProperty;

public class NumLineas {

	@ApiModelProperty(notes = "Nombre del Documento")
	private String 	nombreDocumento;
	@ApiModelProperty(notes = "Numero de lineas que contiene el Documento")
	private int 	numeroLineas;
	

	public NumLineas() {
	}

	public NumLineas(String nombreDocumento, int numeroLineas) {
		this.nombreDocumento = nombreDocumento;
		this.numeroLineas = numeroLineas;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public int getNumeroLineas() {
		return numeroLineas;
	}

	public void setNumeroLineas(int numeroLineas) {
		this.numeroLineas = numeroLineas;
	}
	
}
