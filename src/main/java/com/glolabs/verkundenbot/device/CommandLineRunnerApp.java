package com.glolabs.verkundenbot.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class CommandLineRunnerApp {

    private static Logger logger = LoggerFactory.getLogger(CommandLineRunnerApp.class);

//    @Autowired
//    private Environment env;

    public static void main(String[] args) {
        logger.info("STARTING THE APPLICATION");

        logger.info("EXECUTING : command line runner");
        for (int i = 0; i < args.length; ++i) {
            logger.info("args[{}]: {}", i, args[i]);
        }

        SpringApplication.run(CommandLineRunnerApp.class, args);
        logger.info("APPLICATION FINISHED");
    }

}