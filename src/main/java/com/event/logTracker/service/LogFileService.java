package com.event.logTracker.service;


import com.event.logTracker.model.LogEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface LogFileService {
        CompletableFuture<Iterable<LogEvent>> saveEvents() throws Exception;
        List getEventJsonFromFile();
    }

