package de.mtech.ai;

import opennlp.tools.doccat.*;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.util.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {

        Logger logger = Logger.getLogger("Main");

        logger.addHandler(new ConsoleHandler() {
            {setOutputStream(System.out);}
        });

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        //Swapper.swap();
        //Trainer.train();

        InputStream is = classloader.getResourceAsStream("nlp-model/trained-model.bin");
        DoccatModel doccatModel = new DoccatModel(is);
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(doccatModel);

        final String input = "The oil is very expensive.";

        String[] inputText = input.split(" ");
        double[] outcomes = myCategorizer.categorize(inputText);
        String category = myCategorizer.getBestCategory(outcomes);

        logger.log(Level.INFO, category + " -> " + input);


    }
}