package es.qindel.editortexto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.qindel.editortexto.model.Documento;
import es.qindel.editortexto.model.Linea;
import es.qindel.editortexto.model.NumLineas;
import es.qindel.editortexto.model.TextoDocumento;
import es.qindel.editortexto.model.TextoLinea;
import es.qindel.editortexto.service.DocumentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("editor-texto/1.0.0/documentos")
@Api(value="Editor Texto", description="Operaciones para editar documentos orientados a lineas")
public class DocumentoController {
	
	private static final Gson gson = new Gson();
	
	private DocumentoService documentoService;

	@Autowired
	public DocumentoController(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}
	
	@ApiOperation(value = "Devuelve el numero de lineas de un documento", response = NumLineas.class)
	@RequestMapping(
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/numlineas")
	public NumLineas obtenerNumeroDeLineas(
			@ApiParam(value = "Nombre del documento", required = true) 
			@PathVariable("nombreDocumento") String nombreDocumento) {
		return documentoService.obtenerNumeroDeLineas(nombreDocumento);
	}
	
	@ApiOperation(value = "Devuelve el texto de una determinada linea de un documento", response = TextoLinea.class)
	@RequestMapping(
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/lineas/{numeroLinea}")
	public TextoLinea recuperarLinea(
			@ApiParam(value = "Nombre del documento", required = true) 
			@PathVariable("nombreDocumento") String nombreDocumento, 
			@ApiParam(value = "Numero de Linea", required = true) 
			@PathVariable("numeroLinea") int numeroLinea) {
		return documentoService.recuperaLinea(nombreDocumento, numeroLinea);
	}
	
	@ApiOperation(value = "Devuelve el texto completo de un documento concatenando todas sus lineas", response = TextoDocumento.class)
	@RequestMapping(
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}")
	public TextoDocumento recuperarTextoDocumento(
			@ApiParam(value = "Nombre del documento", required = true) 
			@PathVariable("nombreDocumento") String nombreDocumento) {
		return documentoService.recuperarTextoDocumento(nombreDocumento);
	}
	
	@ApiOperation(value = "Modifica el contenido de una linea", response = ResponseEntity.class)
	@RequestMapping(
			method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/lineas/{numeroLinea}")
	public ResponseEntity modificarLinea(
			@ApiParam(value = "Nombre del documento", required = true) 
			@PathVariable("nombreDocumento") String nombreDocumento,
			@ApiParam(value = "Numero de Linea", required = true) 
			@PathVariable("numeroLinea") int numeroLinea,
			@RequestBody TextoLinea textoLinea) {
		documentoService.modificarLinea(nombreDocumento, numeroLinea, textoLinea.getTextoLinea());
		return new ResponseEntity(gson.toJson("Linea Modificada Correctamente"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Inserta una nueva linea al final del documento", response = ResponseEntity.class)
	@RequestMapping(
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/nuevalinea")
	public ResponseEntity modificarLinea(
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("nombreDocumento") String nombreDocumento,
			@RequestBody TextoLinea textoLinea) {
		documentoService.nuevaLineaDocumento(nombreDocumento, textoLinea.getTextoLinea());
		return new ResponseEntity(gson.toJson("Linea añadida Correctamente"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Recupera el texto completo de una determinada versión del documento", response = TextoDocumento.class)
	@RequestMapping(
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/versiones/{version}")
	public TextoDocumento recuperaVersionDocumento(
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("nombreDocumento") String nombreDocumento, 
			@ApiParam(value = "Versión del documento", required = true)
			@PathVariable("version") int version) {
		return documentoService.recuperaVersionDocumento(nombreDocumento, version);
	}
	
	@ApiOperation(value = "Elimina la última version del documento", response = ResponseEntity.class)
	@RequestMapping(
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/rollback")
	public ResponseEntity rollbackVersionDocumento(
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("nombreDocumento") String nombreDocumento) {
		documentoService.rollbackVersionDocumento(nombreDocumento);
		return new ResponseEntity(gson.toJson("Rollback realizado"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Inserta una linea en la posición indicada", response = ResponseEntity.class)
	@RequestMapping(
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/lineas/{numeroLinea}")
	public ResponseEntity insertarLinea(
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("nombreDocumento") String nombreDocumento,
			@ApiParam(value = "Numero de Linea", required = true) 
			@PathVariable("numeroLinea") int numeroLinea,
			@RequestBody TextoLinea textoLinea) {
		documentoService.insertarLinea(nombreDocumento, numeroLinea, textoLinea.getTextoLinea());
		return new ResponseEntity(gson.toJson("Linea insertada correctamente"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Borra una determinada linea de un documento", response = ResponseEntity.class)
	@RequestMapping(
			method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/lineas/{numeroLinea}")
	public ResponseEntity borrarLinea(
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("nombreDocumento") String nombreDocumento,
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("numeroLinea") int numeroLinea) {
		documentoService.borrarLinea(nombreDocumento, numeroLinea);
		return new ResponseEntity(gson.toJson("Linea borrada correctamente"), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Devuelve las lineas de un documento que contienen una determinada palabra", response = ResponseEntity.class)
	@RequestMapping(
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			path = "{nombreDocumento}/lineas")
	public List<Linea> obtenerlineasConPalabra(
			@ApiParam(value = "Nombre del documento", required = true)
			@PathVariable("nombreDocumento") String nombreDocumento,
			@ApiParam(value = "Palabra a Encontrar", required = true)
			@RequestParam("palabra") String palabra) {
		return documentoService.obtenerlineasConPalabra(nombreDocumento, palabra);
	}
	
}
