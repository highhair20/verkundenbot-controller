package com.glolabs.verkundenbot.device.integration;

import com.glolabs.verkundenbot.device.service.ListenerExecutor;
//import com.glolabs.verkundenbot.device.service.ListenerService;

import org.junit.Test;
//import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@ActiveProfiles("test")
@SpringBootTest
public class RunApplicationIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(ListenerExecutor.class);

//    @SpyBean
//    private ListenerService listenerService;

    @SpyBean
    private ListenerExecutor listenerExecutor;

    @Test
    public void whenContextLoads_thenRunnersRun() throws Exception {
        logger.info("whenContextLoads_thenRunnersRun BEGIN");
        logger.info("listener", listenerExecutor);
//        MockitoAnnotations.initMocks(this);
        verify(listenerExecutor).run(any());
    }
}
