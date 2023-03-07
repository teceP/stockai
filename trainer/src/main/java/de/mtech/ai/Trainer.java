package de.mtech.ai;

import opennlp.tools.doccat.*;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trainer {

    public static void train() throws IOException, URISyntaxException {
        Logger logger = Logger.getLogger("Trainer");

        logger.addHandler(new ConsoleHandler() {
            {setOutputStream(System.out);}
        });

        final String trainingDirectory = "phrases";
        List<String> trainingFiles = new ArrayList<>();
        trainingFiles.add("swapped-Sentences_50Agree.txt");
        trainingFiles.add("swapped-Sentences_66Agree.txt");
        trainingFiles.add("swapped-Sentences_75Agree.txt");
        trainingFiles.add("swapped-Sentences_AllAgree.txt");

        int minNgramSize = 1;
        int maxNgramSize = 6;
        DoccatFactory customFactory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator(), new NGramFeatureGenerator(minNgramSize, maxNgramSize)});

        TrainingParameters mlParams = new TrainingParameters();
        mlParams.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
        mlParams.put(TrainingParameters.TRAINER_TYPE_PARAM, EventTrainer.EVENT_VALUE);
        mlParams.put(TrainingParameters.ITERATIONS_PARAM, 15);
        mlParams.put(TrainingParameters.CUTOFF_PARAM, 2);

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        File outputFile = new File("/Users/mario/IdeaProjects/StockAI/trainer/src/main/resources/nlp-model/trained-model.bin");

        if(outputFile.exists()){
            outputFile.delete();
        }

        outputFile.createNewFile();

        System.out.println(outputFile.getAbsoluteFile());

        for(final String trainingFile : trainingFiles){
            final String trainingDirAndFile = trainingDirectory + "/" + trainingFile;
            logger.log(Level.INFO, "Training Dir and File: " + trainingDirAndFile);

            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(Objects.requireNonNull(classloader.getResource(trainingDirAndFile).toURI())));
            logger.log(Level.INFO, "Input" + trainingDirAndFile);

            ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleObjectStream = new DocumentSampleStream(lineStream);

            DoccatModel doccatModel = DocumentCategorizerME.train("en", sampleObjectStream, mlParams, customFactory);

            try (FileOutputStream outputFileStream = new FileOutputStream(outputFile, true)){
                doccatModel.serialize(outputFileStream);
            }
        }

    }
}
