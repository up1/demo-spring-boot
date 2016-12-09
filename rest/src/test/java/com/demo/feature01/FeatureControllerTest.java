package com.demo.feature01;

import com.demo.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FeatureControllerTest {

    private MediaType contentType = new MediaType(APPLICATION_JSON.getType(),
            APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.userRepository.delete(Long.valueOf(1));
        this.userRepository.save(new User(Long.valueOf(1), "Somkiat", "Pui"));
    }
    @Test
    public void for_flow_01() throws Exception {
        this.mockMvc.perform(get("/feature01/flow01/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Somkiat")))
                .andExpect(jsonPath("$.lastname", is("Pui")));
    }

    @Test
    public void for_flow_01_data_not_found() throws Exception {
        this.mockMvc.perform(get("/feature01/flow01/2"))
                .andExpect(status().isNotFound());
    }

}