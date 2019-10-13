package com.event.logTracker.controller;


import com.event.logTracker.service.LogFileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
public class LogFileControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private LogFileController logFileController;

    @Mock
    private LogFileService logFileService;


    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(logFileController).build();

    }
    @Test
    public void testEvent() throws Exception {
        try {
            ResponseEntity responseEntityCompletableFuture =  logFileController.getLargestEventByDuration();
            Assert.assertNotNull(responseEntityCompletableFuture);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




    @Test
    public void testGetLargestEventByDuration() throws Exception
    {
        this.mockMvc.perform(get("/api/log/events"))
                .andExpect(status().isOk());
    }





}
