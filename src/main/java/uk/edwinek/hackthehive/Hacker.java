package uk.edwinek.hackthehive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.edwinek.hackthehive.config.HackerConfig;
import uk.edwinek.hackthehive.service.BruteForce;

public class Hacker {

    private static Logger LOGGER = LoggerFactory.getLogger(Hacker.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HackerConfig.class);
        BruteForce bruteForce = ctx.getBean(BruteForce.class);
        bruteForce.attemptUserLogin().ifPresent(s -> LOGGER.info("success when attempting password: \"{}\".", s));
    }

}
