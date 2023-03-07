package de.mtech.ai;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        String sentence = "Hallo ich bin Mario. " +
                "Was mir sehr schwer fällt sind sehr komisch gesungende Entenbeine. " +
                "Was mir schwer fällt sind Steine. " +
                "Hi. " +
                "" + " ((( ATP. ";

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("models/opennlp-de-ud-gsd-tokens-1.0-1.9.3.bin");

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        Arrays.stream(tokenizer.tokenize(sentence)).forEach(System.out::println);

        WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
        Arrays.stream(whitespaceTokenizer.tokenize(sentence)).forEach(System.out::println);

        TokenizerModel tokenizerModel = new TokenizerModel(is);
        TokenizerME tokenizerME = new TokenizerME(tokenizerModel);
        Arrays.stream(tokenizerME.tokenize(sentence)).forEach(System.out::println);


    }
}