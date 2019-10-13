package com.event.logTracker.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class LogFileControllerTest {
    private MockMvc mockMvc;

    private LogFileController logFileController;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(logFileController).build();

    }
    @Test
    public void testGetLargestEventByDuration() throws Exception
    {
        this.mockMvc.perform(get("/api/log/events"))
                .andExpect(status().isOk());
    }


}
