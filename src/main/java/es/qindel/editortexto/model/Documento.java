package es.qindel.editortexto.model;

import io.swagger.annotations.ApiModelProperty;

public class Documento {

	@ApiModelProperty(notes = "Identificador único del documento")
	private int 	idDoc;
	@ApiModelProperty(notes = "Nombre del Documento")
	private String 	nombreDocumento;
	@ApiModelProperty(notes = "Versión del Documento")
	private int 	version;
	

	public Documento() {
	}

	public Documento (int idDoc, String nombreDocumento, int version) {
		
		this.idDoc 				= idDoc;
		this.nombreDocumento 	= nombreDocumento;
		this.version   	= version;
		
	}

	public int getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
    @Override
    public String toString() {
        return String.format("Documento [idDoc=%s, nombreDocumento=%s, version=%s]", idDoc, nombreDocumento, version);
    }
	
}
