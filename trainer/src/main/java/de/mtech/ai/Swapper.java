package de.mtech.ai;

import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Swapper {

    public static void swap() throws URISyntaxException, IOException {
        Logger logger = Logger.getLogger("Trainer");

        logger.addHandler(new ConsoleHandler() {
            {setOutputStream(System.out);}
        });

        final String trainingDirectory = "phrases";

        List<String> trainingFiles = new ArrayList<>();
        trainingFiles.add("Sentences_50Agree.txt");
        trainingFiles.add("Sentences_66Agree.txt");
        trainingFiles.add("Sentences_75Agree.txt");
        trainingFiles.add("Sentences_AllAgree.txt");

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        for(final String trainingFile : trainingFiles) {
            final String trainingDirAndFile = trainingDirectory + "/" + trainingFile;
            logger.log(Level.INFO, "Training Dir and File: " + trainingDirAndFile);

            File f = new File(Objects.requireNonNull(classloader.getResource(trainingDirAndFile).toURI()));
            File fSwapped = new File(f.getParent() + "/swapped-" + trainingFile);

            if(fSwapped.exists()){
                fSwapped.delete();
            }

            fSwapped.createNewFile();

            BufferedReader file = new BufferedReader(new FileReader(f));
            FileOutputStream os = new FileOutputStream(fSwapped, true);

            String line;
            String input = "";

            while ((line = file.readLine()) != null){
                input = line;

                //input = input.replace(toUpdate, updated);
                final String[] splitted = input.split("@");
                final String swappedString = splitted[1].trim() + " " + splitted[0].trim() + System.lineSeparator();

                //logger.log(Level.INFO, splitted[1] + " - " + splitted[0]);
                //logger.log(Level.INFO, fSwapped.getAbsolutePath());
                logger.log(Level.INFO, swappedString);

                os.write(swappedString.getBytes());
            }

            file.close();
            os.close();
        }

    }

}
