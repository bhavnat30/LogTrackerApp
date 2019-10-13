package com.event.logTracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= LogTrackerApplication.class)
@ActiveProfiles("test")
public class LogTrackerApplicationTests {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Autowired
	LogTrackerApplication lt;
	@Test
	public void runLogTrackApp() throws Exception {
         lt.run("file.json");


	}



}
