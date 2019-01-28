package es.qindel.editortexto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.springframework.web.context.WebApplicationContext;
 
import es.qindel.editortexto.controller.DocumentoController;
import es.qindel.editortexto.model.NumLineas;
import es.qindel.editortexto.service.DocumentoService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EditorTextoApplicationTests {

	MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;
	
	@Autowired
	DocumentoController documentoController;   
	
	@Autowired
	private DocumentoService documentoService;

    @Test
    public void contextLoads() {
          assertThat(documentoController).isNotNull();
          assertThat(documentoService).isNotNull();             
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.documentoController).build();
    }

    @Test
    public void serviceObtenerNumeroDeLineas() {
        NumLineas numLineasOut = documentoService.obtenerNumeroDeLineas("DocPrueba");
        System.out.println(numLineasOut.toString());         
        assertThat(numLineasOut).isNotNull();
    }        

    @Test
    public void ObtenerNumeroDeLineas() throws Exception {

        mockMvc.perform(get("http://localhost:8080/editor-texto/1.0.0/documentos/DocPrueba/numlineas")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreDocumento").value("DocPrueba"));       
    }

}