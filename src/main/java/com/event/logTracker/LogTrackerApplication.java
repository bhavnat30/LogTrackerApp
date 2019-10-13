package com.event.logTracker;

import com.event.logTracker.model.LogEvent;
import com.event.logTracker.model.LogEventJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@SpringBootApplication
public class LogTrackerApplication implements CommandLineRunner {

	private List<LogEvent> events;
	private static final Logger logger = LoggerFactory.getLogger(LogTrackerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LogTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info(
				"Application started with command-line arguments: {} ",args);
		if(args.length == 0){
			logger.error("Event Log file path not given.");
			System.exit(1);
		}

		String filePath = args[0];
		Path eventFilePath = Paths.get(filePath);

		logger.info("PATH:" + eventFilePath);
		System.setProperty("filePath", filePath);


	}



}
