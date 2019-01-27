package es.qindel.editortexto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.qindel.editortexto.dao.IDocumentoDao;
import es.qindel.editortexto.model.Documento;
import es.qindel.editortexto.model.Linea;
import es.qindel.editortexto.model.NumLineas;
import es.qindel.editortexto.model.TextoDocumento;
import es.qindel.editortexto.model.TextoLinea;

@Service
public class DocumentoService {
	
	private IDocumentoDao documentoDao;

	@Autowired
	public DocumentoService(@Qualifier("miDao") IDocumentoDao documentoDao) {
		this.documentoDao = documentoDao;
	}

	//Devuelve el numero de lineas de un documento
	public NumLineas obtenerNumeroDeLineas(String nombreDocumento) {
		int numeroLineas = documentoDao.obtenerNumeroDeLineas(nombreDocumento);
		return new NumLineas(nombreDocumento, numeroLineas);
	}

	//Devuelve el texto de una determinada linea de un documento
	public TextoLinea recuperaLinea(String nombreDocumento, int numeroLinea) {
		Linea linea = documentoDao.recuperaLinea(nombreDocumento, numeroLinea);
		return new TextoLinea(nombreDocumento, numeroLinea, linea.getTextoLinea());
	}
	
	//Modifica el contenido de una linea
	public void modificarLinea(String nombreDocumento, int numeroLinea, String textoLinea) {
		documentoDao.modificarLinea(nombreDocumento, numeroLinea, textoLinea);
	}
	
	//Inserta una nueva linea al final del documento
	public void nuevaLineaDocumento(String nombreDocumento, String textoLinea) {
		documentoDao.nuevaLineaDocumento(nombreDocumento, textoLinea);
	}
	
	//Inserta una linea en la posición indicada
	public void insertarLinea(String nombreDocumento, int numeroLinea, String textoLinea) {
		documentoDao.insertarLinea(nombreDocumento, numeroLinea, textoLinea);
	}
	
	//Borra una determinada linea de un documento
	public void borrarLinea(String nombreDocumento, int numeroLinea) {
		documentoDao.borrarLinea(nombreDocumento, numeroLinea);
	}
	
	//Devuelve el texto completo de un documento concatenando todas sus lineas
	public TextoDocumento recuperarTextoDocumento(String nombreDocumento) {
		String textoDocumento = documentoDao.recuperarTextoDocumento(nombreDocumento);
		return new TextoDocumento(nombreDocumento, textoDocumento);
	}
	
	//Recupera el texto completo de una determinada versión del documento
	public TextoDocumento recuperaVersionDocumento(String nombreDocumento, int version) {
		String textoDocumento = documentoDao.recuperaVersionDocumento(nombreDocumento, version);
		return new TextoDocumento(nombreDocumento, textoDocumento);
	}
	
	//Elimina la última version del documento
	public void  rollbackVersionDocumento(String nombreDocumento) {
		documentoDao.rollbackVersionDocumento(nombreDocumento);
	}
	
	//Devuelve las lineas de un documento que contienen una determinada palabra
	public List<Linea> obtenerlineasConPalabra(String nombreDocumento, String palabra) {
		return documentoDao.obtenerlineasConPalabra(nombreDocumento, palabra);
	}
	
}
