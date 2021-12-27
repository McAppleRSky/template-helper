package ru.mrs.main;

import org.apache.logging.log4j.core.Logger;
import ru.mrs.haml.Haml;

public class Main extends MainConfiguration {

    private static final Logger LOGGER = configureLogger(Main.class);

    public static void main(String[] args) {
        Haml haml = new Haml();
        for (Integer exitCode : haml.convert()) {
            LOGGER.info("Exit code : " + exitCode);
        }

    }

}
