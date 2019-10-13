package com.event.logTracker.controller;

import com.event.logTracker.model.LogEvent;
import com.event.logTracker.model.LogEventJson;
import com.event.logTracker.repository.LogEventJpaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/log/events")
public class LogFileController {
    private static final Logger logger = LoggerFactory.getLogger(LogFileController.class);


    @Autowired
    private LogEventJpaRepository logEventJpaRepository;


    @GetMapping
    public ResponseEntity getLargestEventByDuration() throws Exception {
        String filePath =  System.getProperty("filePath");
        Path eventFilePath = Paths.get(filePath);

        File file = new File(eventFilePath.toString());
        if (!Files.exists(eventFilePath)) {
            logger.error("Event Log file doest not exist for the path:" + filePath);
            System.exit(2);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, LogEventJson> eventJsonMap = new ConcurrentHashMap<>();
        List<LogEvent> eventList = new ArrayList<>();

        try (Stream linesStream = Files.lines(file.toPath())) {
            linesStream.forEach(line -> {
                try {
                    LogEventJson convertedObject = objectMapper.readValue(line.toString(), LogEventJson.class);
                    if(eventJsonMap.containsKey(convertedObject.getId())){
                        eventList.add(getList(eventJsonMap,convertedObject));
                        eventJsonMap.remove(convertedObject.getId());
                    }else{
                        eventJsonMap.put(convertedObject.getId(),convertedObject);
                    }

                } catch (IOException e) {
                    logger.error("An error occurred in while converting to JSON {} "+e.getLocalizedMessage());
                }
            });
        } catch (IOException e) {
            logger.error("Internal error occurred during file read {} "+e.getLocalizedMessage());
        }
        logger.info("Event Log list with unique event id and duration {}",eventList);
        Iterable<LogEvent> eventIterable = saveEvents(eventList);

        return  ResponseEntity.ok().body(saveEvents(eventList));
    }




    @Async
    public Iterable<LogEvent> saveEvents(List logList) throws Exception {
        final long start = System.currentTimeMillis();

        logger.info("Saving a list of Event Logs of size {} records", logList.size());
        Iterable<LogEvent> eventIterable = logEventJpaRepository.saveAll(logList);

        logger.info("Elapsed time: {}", (System.currentTimeMillis() - start));
        return eventIterable;
    }

    public LogEvent getList(Map<String,LogEventJson> eventJsonMap, LogEventJson newEventJson){
        LogEventJson previousEventLog = eventJsonMap.get(newEventJson.getId());
        return checkEventDuration(previousEventLog,newEventJson);
    }

    private LogEvent checkEventDuration(LogEventJson previousLog,LogEventJson newEventJson){
        long timeDiff;
        if(Long.parseLong(previousLog.getTimestamp()) < Long.parseLong(newEventJson.getTimestamp())){
            timeDiff = Math.subtractExact(Long.parseLong(newEventJson.getTimestamp()),Long.parseLong(previousLog.getTimestamp()));
        }
        else{
            timeDiff = Math.subtractExact(Long.parseLong(previousLog.getTimestamp()),Long.parseLong(newEventJson.getTimestamp()));
        }
        return convertIntoLogEvent(newEventJson,timeDiff);
    }

    private LogEvent convertIntoLogEvent(LogEventJson eventJson, long timeDiff){
        LogEvent event = new LogEvent();
        event.setId(eventJson.getId());
        event.setSpan(timeDiff);
        if(!StringUtils.isEmpty(eventJson.getHost())){
            event.setHost(eventJson.getHost());
        }
        if(!StringUtils.isEmpty(eventJson.getState())){
            event.setType(eventJson.getType());
        }
        if(timeDiff > 4L){
            event.setAlert(true);
        }
        return event;
    }

}
