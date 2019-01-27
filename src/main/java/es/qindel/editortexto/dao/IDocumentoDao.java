package es.qindel.editortexto.dao;

import java.util.List;

import es.qindel.editortexto.model.Documento;
import es.qindel.editortexto.model.Linea;

public interface IDocumentoDao {
	
	   //Devuelve un objeto documento buscándolo por nombre de documento.
	   Documento recuperaDocumento (String nombreDocumento);
	
		//Devuelve el numero de lineas de un documento
	   int obtenerNumeroDeLineas(String nombreDocumento);

	   //Devuelve el texto de una determinada linea de un documento
	   Linea recuperaLinea (String nombreDocumento, int ordenLinea);
	   
	   //Modifica el contenido de una linea
	   void modificarLinea(String nombreDocumento, int numeroLinea, String textoLinea);
	   
	   //Inserta una nueva linea al final del documento
	   void nuevaLineaDocumento(String nombreDocumento, String textoLinea);
	   
	   //Inserta una linea en la posición indicada
	   void insertarLinea(String nombreDocumento, int numeroLinea, String textoLinea);
	   
	   //Borra una determinada linea de un documento
	   void borrarLinea(String nombreDocumento, int numeroLinea);
	   
	   //Devuelve el texto completo de un documento concatenando todas sus lineas
	   String recuperarTextoDocumento(String nombreDocumento);
	   
	   //Recupera el texto completo de una determinada versión del documento	   
	   String recuperaVersionDocumento(String nombreDocumento, int version);
	   
	   //Elimina la última version del documento
	   void rollbackVersionDocumento(String nombreDocumento);
	   
	   //Devuelve las lineas de un documento que contienen una determinada palabra
	   List<Linea> obtenerlineasConPalabra(String nombreDocumento, String palabra);
}
