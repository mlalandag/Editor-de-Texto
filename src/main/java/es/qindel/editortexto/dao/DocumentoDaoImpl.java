package es.qindel.editortexto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.qindel.editortexto.model.Documento;
import es.qindel.editortexto.model.Linea;

@Repository("miDao")
public class DocumentoDaoImpl implements IDocumentoDao {
		
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// Para implementar este Dao utilizamos Spring JDBC que proporciona una capa de abstracción sobre JDBC
	// Permite simplificar el mapeo de parametros a queries, utiliza beans en lugar de resultsets y It used concepts like JDBCTemplate
	// evita el manejo de Excepciones porque estas son convertidas a RuntimeExceptions
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    
	@Override
	public Documento recuperaDocumento(String nombreDocumento) {
		
		logger.info("Obteniendo documento -> {}", nombreDocumento);
		
		String sql = "SELECT * FROM DOCUMENTOS WHERE NOMBRE_DOCUMENTO = ?";
		return (Documento) jdbcTemplate.queryForObject(sql, new Object[] { nombreDocumento}, 
				new RowMapper<Documento>() {
	            	public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		Documento documento = new Documento();
	            		documento.setIdDoc(rs.getInt("ID_DOC"));
	            		documento.setNombreDocumento(rs.getString("NOMBRE_DOCUMENTO"));
	            		documento.setVersion(rs.getInt("VERSION"));
	            		return documento;
	            	}}
			);	
	}
	
	
	public void insertarDocumento(Documento documento) {

		logger.info("Insertando documento -> {}", documento);
	       
		String sql = "INSERT INTO DOCUMENTOS (NOMBRE_DOCUMENTO, VERSION) VALUES(?, ?)";
		jdbcTemplate.update(sql, documento.getNombreDocumento(), documento.getVersion());	
	}
	
	
	public void modificarDocumento(Documento documento) {

		logger.info("Modificando documento -> {}", documento.toString());
	       
		String sql = "UPDATE DOCUMENTOS SET NOMBRE_DOCUMENTO =?, VERSION = ? WHERE ID_DOC = ?";
		jdbcTemplate.update(sql, documento.getNombreDocumento(), documento.getVersion(), documento.getIdDoc());	
	}
	
	
	public int obtenerNumeroDeLineas(String nombreDocumento) {
		
		logger.info("Obteniendo el numero de lineas para el documento -> {}", nombreDocumento);
		
		String sql = "SELECT COUNT(*) FROM DOCUMENTOS A, LINEAS B WHERE A.ID_DOC = B.ID_DOC AND NOMBRE_DOCUMENTO = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {nombreDocumento }, Integer.class);
		
	}

	@Override
	public Linea recuperaLinea(String nombreDocumento, int numeroLinea) {
		
		logger.info("Obteniendo linea -> {} para el documento -> {}", numeroLinea, nombreDocumento);
		
		String sql = "SELECT A.ID_DOC, B.* FROM DOCUMENTOS A, LINEAS B WHERE A.ID_DOC = B.ID_DOC AND NOMBRE_DOCUMENTO = ? AND NUMERO_LINEA= ?";
		return (Linea) jdbcTemplate.queryForObject(sql, new Object[] { nombreDocumento, numeroLinea }, 
				new RowMapper<Linea>() {
	            	public Linea mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		Linea linea = new Linea();
	            		linea.setIdDoc(rs.getInt("ID_DOC"));
	            		linea.setIdLinea(rs.getInt("ID_LINEA"));
	            		linea.setTextoLinea(rs.getString("TEXTO_LINEA"));
	            		linea.setNumeroLinea(rs.getInt("NUMERO_LINEA"));
	            		linea.setVersion(rs.getInt("VERSION"));
	            		linea.setProximaLinea(rs.getInt("PROXIMA_LINEA"));
	            		return linea;
	            	}}
			);	
	}
	
	
	public Linea recuperaLineaPorId(int idLinea) {
		
		logger.info("Obteniendo linea con id -> {}", idLinea);
		
		String sql = "SELECT * FROM LINEAS WHERE ID_LINEA = ?";
		return (Linea) jdbcTemplate.queryForObject(sql, new Object[] { idLinea }, 
				new RowMapper<Linea>() {
	            	public Linea mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		Linea linea = new Linea();
	            		linea.setIdDoc(rs.getInt("ID_DOC"));
	            		linea.setIdLinea(rs.getInt("ID_LINEA"));
	            		linea.setTextoLinea(rs.getString("TEXTO_LINEA"));
	            		linea.setNumeroLinea(rs.getInt("NUMERO_LINEA"));
	            		linea.setVersion(rs.getInt("VERSION"));
	            		linea.setProximaLinea(rs.getInt("PROXIMA_LINEA"));
	            		return linea;
	            	}}
			);	
	}
	
	
	public void insertarLinea(Linea linea) {

		logger.info("Insertando linea -> {}", linea);
	       
		String sql = "INSERT INTO LINEAS (ID_DOC, TEXTO_LINEA, NUMERO_LINEA, VERSION, PROXIMA_LINEA) VALUES(?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, linea.getIdDoc(), linea.getTextoLinea(), linea.getNumeroLinea(), linea.getVersion(), linea.getProximaLinea());	
	}
	
	
	@Override
	public void modificarLinea(String nombreDocumento, int numeroLinea, String textoLinea) {
		
		logger.info("Actualizando linea -> {} para el documento -> {}", numeroLinea, nombreDocumento);
		
		Documento documento = recuperaDocumento (nombreDocumento);
		// Incrementamos la versión del documento
		documento.setVersion(documento.getVersion() + 1);
		
		String sql = "UPDATE LINEAS SET TEXTO_LINEA = ? " +
					 "WHERE ID_LINEA = ? AND ID_DOC = SELECT ID_DOC FROM DOCUMENTOS WHERE NOMBRE_DOCUMENTO = ?";
		jdbcTemplate.update(sql, textoLinea, numeroLinea, nombreDocumento);
		
		// Guardamos la nueva version del documento en el histórico
		salvarHistoricoVersiones(documento);
	}
	
	public void modificarLinea(Linea linea) {

		logger.info("Modificando linea -> {}", linea.toString());
	       
		String sql = "UPDATE LINEAS SET TEXTO_LINEA =?, NUMERO_LINEA = ?, VERSION = ?, PROXIMA_LINEA = ? " +
					 "WHERE ID_DOC = ? AND ID_LINEA = ?";
		jdbcTemplate.update(sql, linea.getTextoLinea(), linea.getNumeroLinea(), linea.getVersion(), linea.getProximaLinea(), linea.getIdDoc(), linea.getIdLinea());	
	}

	@Override
	public String recuperarTextoDocumento(String nombreDocumento) {

		logger.info("Obteniendo texto del documento -> {}", nombreDocumento);
		
		String sql = "SELECT B.TEXTO_LINEA FROM DOCUMENTOS A, LINEAS B WHERE A.ID_DOC = B.ID_DOC AND NOMBRE_DOCUMENTO = ? ORDER BY NUMERO_LINEA";
		List<String> lineas = jdbcTemplate.queryForList(sql, String.class, nombreDocumento);
		return String.join(" ", lineas);
		
	}
	
	@Override
	public String recuperaVersionDocumento(String nombreDocumento, int version) {

		logger.info("Obteniendo texto de la version -> {] del documento -> {}", version, nombreDocumento);
		
		String sql = "SELECT B.TEXTO_LINEA FROM DOCUMENTOS A, LINEAS_HISTORICO B " +
					 "WHERE A.ID_DOC = B.ID_DOC AND NOMBRE_DOCUMENTO = ? AND B.VERSION = ?" +
					 "ORDER BY NUMERO_LINEA";
		List<String> lineas = jdbcTemplate.queryForList(sql, String.class, nombreDocumento, version);
		return String.join(" ", lineas);
		
	}

	@Override
	public void nuevaLineaDocumento(String nombreDocumento, String textoLinea) {
		
		logger.info("Insertando nueva linea en el documento -> {}", nombreDocumento);
		
		// Comprobamos si existe el documento obteniendo el numero de lineas.
		int numlineas = obtenerNumeroDeLineas(nombreDocumento);
		logger.info("Numero de lineas -> {}", numlineas);
		
		Documento documento = new Documento();
		
		// Si no existe crea el documento.
		if (numlineas == 0) {
			documento.setNombreDocumento(nombreDocumento);
			documento.setVersion(0);
			insertarDocumento(documento);
			logger.info("Nuevo Documento Insertado");
		}
		
		documento = recuperaDocumento (nombreDocumento);
		
		// Incrementamos la versión del documento
		documento.setVersion(documento.getVersion() + 1);
		
		// Inserta la linea como ultima linea del documento
		Linea nuevaLinea = new Linea();
		nuevaLinea.setIdDoc(documento.getIdDoc());
		nuevaLinea.setTextoLinea(textoLinea);
		nuevaLinea.setVersion(documento.getVersion());
		nuevaLinea.setNumeroLinea(numlineas + 1);
		nuevaLinea.setProximaLinea(-1);
		insertarLinea(nuevaLinea);
		logger.info("Nueva Linea numero -> {} insertada en el documento -> {}", numlineas, nombreDocumento);
				
		// Si no es la priemra linea actualizamos el enlace de la penultima linea.
		if (numlineas != 0) {
			actualizarEnlaceLineaAnterior(documento, nuevaLinea.getNumeroLinea());
		}
		
		// Guardamos la nueva version del documento en el histórico
		salvarHistoricoVersiones(documento);
		
	}	
	
	
	public void actualizarEnlaces(Documento documento) {
		
		logger.info("Actualizando Enlaces para el documento -> {}", documento.getIdDoc());
		
		// Obtenemos la lista de lineas del documento
		String sql1 = "SELECT * FROM LINEAS WHERE ID_DOC = ? ORDER BY NUMERO_LINEA DESC";
		List<Linea> lineas = jdbcTemplate.queryForList(sql1, Linea.class, documento.getIdDoc());
		
		logger.info("Lista de Lineas -> {}", lineas);
		
		// Recorremos la lista actualizando el campo próxima linea.
		Iterator<Linea> it = lineas.iterator();
		Linea ultimaLinea = it.next();
		int idProximaLinea = ultimaLinea.getIdLinea();
		while (it.hasNext()) {
		 
			Linea linea = it.next();
			
			String sql2 = "UPDATE LINEAS SET PROXIMA_LINEA = ? WHERE ID_LINEA = ? AND ID_DOC = ?";
			jdbcTemplate.update(sql2, idProximaLinea, linea.getIdLinea(), documento.getIdDoc());
		 
		}
	}
	
	public void actualizarEnlaceLineaAnterior(Documento documento, int numeroLinea) {
		
		logger.info("Actualizando Enlace Linea anterior para el documento -> {} y la linea -> {}", documento.getIdDoc(), numeroLinea);
		
		// Obtenemos la lista de lineas del documento
		String sql1 = "SELECT * FROM LINEAS WHERE ID_DOC = ? AND NUMERO_LINEA <= ? ORDER BY NUMERO_LINEA DESC LIMIT 2";
		List<Linea> lineas = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<Linea>(Linea.class), documento.getIdDoc(), numeroLinea);
		
		logger.info("Lista de Lineas -> {}", lineas);
		
		// Recorremos la lista actualizando el campo próxima linea.
		Iterator<Linea> it = lineas.iterator();
		Linea ultimaLinea = it.next();
		Linea penultimaLinea = it.next();
			
		String sql2 = "UPDATE LINEAS SET PROXIMA_LINEA = ? WHERE ID_LINEA = ? AND ID_DOC = ?";
		jdbcTemplate.update(sql2, ultimaLinea.getIdLinea(), penultimaLinea.getIdLinea(), documento.getIdDoc());
	}
	
	
	public void salvarHistoricoVersiones(Documento documento) {
		
		logger.info("salvamos version actual del documento en el histórico");
		
		//Actualizamos el documento y sus lineas con la nueva version
		String sql1 = "UPDATE DOCUMENTOS SET VERSION = ? WHERE ID_DOC = ?";
		jdbcTemplate.update(sql1, documento.getVersion(), documento.getIdDoc());
		
		String sql2 = "UPDATE LINEAS SET VERSION = ? WHERE ID_DOC = ?";
		jdbcTemplate.update(sql2, documento.getVersion(), documento.getIdDoc());
		
		// Insertamos la nueva version del documento en el histórico
		String sql3 = "INSERT INTO LINEAS_HISTORICO SELECT * FROM LINEAS WHERE ID_DOC = ? AND VERSION = ?";
		jdbcTemplate.update(sql3, documento.getIdDoc(), documento.getVersion());
	}

	@Override
	public void rollbackVersionDocumento(String nombreDocumento) {

		logger.info("Haciendo Rollback de la ultima version del documento -> {}", nombreDocumento);
		
		Documento documento = recuperaDocumento (nombreDocumento);
		int versionAnterior = documento.getVersion() - 1;
		
		// Borramos ultima version de las tablas de lineas e historico 
		String sql1 = "DELETE FROM LINEAS WHERE ID_DOC = ?";
		jdbcTemplate.update(sql1, documento.getIdDoc());
		String sql2 = "DELETE FROM LINEAS_HISTORICO WHERE ID_DOC = ? AND VERSION = ?";
		jdbcTemplate.update(sql2, documento.getIdDoc(), documento.getVersion());
		
		// Restauramos version anterior
		String sql3 = "INSERT INTO LINEAS SELECT * FROM LINEAS_HISTORICO WHERE ID_DOC = ? AND VERSION = ?";
		jdbcTemplate.update(sql3, documento.getIdDoc(), versionAnterior);
		
		documento.setVersion(versionAnterior);
		modificarDocumento(documento);
	}


	@Override
	public void insertarLinea(String nombreDocumento, int numeroLinea, String textoLinea) {
		
		logger.info("Insertamos linea en el documento -> {} en la posición -> {}", nombreDocumento, numeroLinea);
		
		// Obtenemos el numero de lineas del documento
		int numlineas = obtenerNumeroDeLineas(nombreDocumento);
		
		// Recuperamos el documento
		Documento documento = recuperaDocumento (nombreDocumento);
		
		// Incrementamos la versión del documento
		documento.setVersion(documento.getVersion() + 1);
		
		// Distinguimos entre tres posibles casos. Que queramos insertar al final del documento,
		// que queramos insertar en la primera posición o en una posición intermedia
		if (numeroLinea > numlineas) {
			
			nuevaLineaDocumento(nombreDocumento, textoLinea);
			
		} else if (numeroLinea == 1) {
			
			Linea lineaPosterior = recuperaLinea(nombreDocumento, numeroLinea);
			
			// Insertamos la linea
			Linea nuevaLinea = new Linea();
			nuevaLinea.setIdDoc(documento.getIdDoc());
			nuevaLinea.setTextoLinea(textoLinea);
			nuevaLinea.setNumeroLinea(0);
			nuevaLinea.setVersion(documento.getVersion());
			nuevaLinea.setProximaLinea(lineaPosterior.getIdLinea());
			insertarLinea(nuevaLinea);
			
			// Renumeramos los numeros de linea utilizando los enlaces
			renumeramosLineas(documento);
			
			// Guardamos la nueva version del documento en el histórico
			salvarHistoricoVersiones(documento);
			
		} else {
		
			// Recuperamos la linea anterior y la posterior (contemplar caso de que se quiera insertar en la primera posición)
			Linea lineaAnterior  = recuperaLinea(nombreDocumento, numeroLinea - 1);
			Linea lineaPosterior = recuperaLinea(nombreDocumento, numeroLinea);
		
			// Insertamos la linea
			Linea nuevaLinea = new Linea();
			nuevaLinea.setIdDoc(documento.getIdDoc());
			nuevaLinea.setTextoLinea(textoLinea);
			nuevaLinea.setNumeroLinea(0);
			nuevaLinea.setVersion(documento.getVersion());
			nuevaLinea.setProximaLinea(lineaPosterior.getIdLinea());
			insertarLinea(nuevaLinea);
			
			// Actualizamos enlace de la linea anterior
			nuevaLinea = recuperaLinea(nombreDocumento, 0);
			lineaAnterior.setProximaLinea(nuevaLinea.getIdLinea());
			modificarLinea(lineaAnterior);
			
			// Renumeramos los numeros de linea utilizando los enlaces
			renumeramosLineas(documento);
			
			// Guardamos la nueva version del documento en el histórico
			salvarHistoricoVersiones(documento);
		}
		
	}


	@Override
	public void borrarLinea(String nombreDocumento, int numeroLinea) {
		
		// Obtenemos el numero de lineas del documento
		int numlineas = obtenerNumeroDeLineas(nombreDocumento);
		
		Documento documento = recuperaDocumento (nombreDocumento);
		
		// Incrementamos la versión del documento
		documento.setVersion(documento.getVersion() + 1);
		
		// Al igual que en el caso de la inserción debemos distinguir entre tres posibles casos. 
		// Que queramos insertar al final del documento, que queramos insertar en la primera posición 
		// o en una posición intermedia
		if (numeroLinea == 1) {

			// Simplemente Borramos la linea
			borradoSimpleLinea(documento.getIdDoc(), numeroLinea);
			
			// Renumeramos los numeros de linea utilizando los enlaces
			renumeramosLineas(documento);
			
		} else if (numeroLinea == numlineas) {
			
			// Recuperamos la linea anterior
			Linea lineaAnterior  = recuperaLinea(nombreDocumento, numeroLinea - 1);
			
			// Borramos la linea
			borradoSimpleLinea(documento.getIdDoc(), numeroLinea);
			
			// Enlazamos las linea anterior con la posterior
			lineaAnterior.setProximaLinea(-1);
			modificarLinea(lineaAnterior);
			
		} else {
			// Recuperamos la linea anterior y la posterior (contemplar los casos de que se quieran borrar la primera o la ultima lineas)
			Linea lineaAnterior  = recuperaLinea(nombreDocumento, numeroLinea - 1);
			Linea lineaPosterior = recuperaLinea(nombreDocumento, numeroLinea + 1);
			
			// Borramos la linea
			borradoSimpleLinea(documento.getIdDoc(), numeroLinea);
			
			// Enlazamos las linea anterior con la posterior
			lineaAnterior.setProximaLinea(lineaPosterior.getIdLinea());
			modificarLinea(lineaAnterior);
			
			// Renumeramos los numeros de linea utilizando los enlaces
			renumeramosLineas(documento);
		}
		
		// Guardamos la nueva version del documento en el histórico
		salvarHistoricoVersiones(documento);
		
	}
	
	public void borradoSimpleLinea(int idDoc, int numeroLinea) {
		
		String sql = "DELETE FROM LINEAS WHERE ID_DOC = ? AND NUMERO_LINEA = ?";
		jdbcTemplate.update(sql, idDoc, numeroLinea);
		
	}


	public void renumeramosLineas(Documento documento) {
		
		logger.info("Renumeramos lineas del documento -> {} utilizando los enlaces", documento.getNombreDocumento());
		
		// Obtenemos el numero de lineas del documento
		int numlineas = obtenerNumeroDeLineas(documento.getNombreDocumento());
		
		// Obtenemos la primera linea (Es aquella que no figura en el campo proxima_linea de ninguna otra
		Linea linea = obtenerPrimeraLinea(documento);
		
		//Inicializamos el contador
		int contador = 0;
		
		while (linea.getProximaLinea() != -1 && contador < numlineas) {
			
			linea.setNumeroLinea(++contador);
			modificarLinea(linea);
			
			// Recuperamos la siguiente linea
			linea = recuperaLineaPorId(linea.getProximaLinea());
		}
		// Modificamos ultima linea
		linea.setNumeroLinea(++contador);
		modificarLinea(linea);
		
	}
	
	public Linea obtenerPrimeraLinea(Documento documento) {
		
		logger.info("Obtenemos la primera linea del documento -> {}", documento.getNombreDocumento());
		String sql = "SELECT A.ID_DOC, B.* FROM DOCUMENTOS A, LINEAS B WHERE A.ID_DOC = B.ID_DOC AND A.ID_DOC = ? " +
					 "AND B.ID_LINEA NOT IN (SELECT C.PROXIMA_LINEA FROM LINEAS C WHERE C.ID_DOC = A.ID_DOC)";
		return (Linea) jdbcTemplate.queryForObject(sql, new Object[] { documento.getIdDoc() }, 
				new RowMapper<Linea>() {
	            	public Linea mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		Linea linea = new Linea();
	            		linea.setIdDoc(rs.getInt("ID_DOC"));
	            		linea.setIdLinea(rs.getInt("ID_LINEA"));
	            		linea.setTextoLinea(rs.getString("TEXTO_LINEA"));
	            		linea.setNumeroLinea(rs.getInt("NUMERO_LINEA"));
	            		linea.setVersion(rs.getInt("VERSION"));
	            		linea.setProximaLinea(rs.getInt("PROXIMA_LINEA"));
	            		return linea;
	            	}}
			);	
	
	}


	@Override
	public List<Linea> obtenerlineasConPalabra(String nombreDocumento, String palabra) {
		
		logger.info("Obtenemos las lineas del documento -> {} que contienen la palabra -> {}", nombreDocumento, palabra);
		
		Documento documento = recuperaDocumento (nombreDocumento);

		String sql = "SELECT * FROM LINEAS WHERE ID_DOC = ? AND TEXTO_LINEA LIKE ?";
		List<Linea> lineas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Linea>(Linea.class), documento.getIdDoc(), "%" + palabra + "%");
		
		return lineas;
	}
	
	

}
