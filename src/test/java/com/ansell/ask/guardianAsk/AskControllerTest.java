package com.ansell.ask.guardianAsk;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.activation.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import com.ansell.ask.controller.ASKController;
import com.ansell.ask.service.interfaces.IASKService;
import com.ansell.ask.service.interfaces.IAskCommonsService;
import com.ansell.ask.service.interfaces.IGenericGridsService;

/*@RunWith(SpringRunner.class)
@WebMvcTest(ASKController.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@WebAppConfiguration("WebContent")*/
public class AskControllerTest {
	
	/*@Autowired
    private MockMvc mvc;
	
	@MockBean
	private IASKService askService = null;

	@MockBean
	private IAskCommonsService askCommonsService;
	
	@MockBean
	private IGenericGridsService genericGridsService;
	
	@MockBean
	private JdbcTemplate jdbcTemplate;
	
	@MockBean
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private Environment env;*/
	 
	/*@Autowired
	private TilesConfigurer tilesConfigurer;*/
	/*@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        return (DataSource) dataSource;
    }
	
	@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }*/
	
	/*@Bean
	public TilesConfigurer configurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles/tiles-def.xml"});
		return tilesConfigurer;
	}*/
	
	/*@Test
	public void welcomePageTestForStatus200() throws Exception {	 
	    mvc.perform(get("/welcome")
	      .contentType(MediaType.TEXT_HTML))
	      .andExpect(status().isOk())
	      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}*/
	
	
	

}
