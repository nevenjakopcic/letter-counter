package hr.nevenjakopcic;

import hr.nevenjakopcic.lettercounter.LetterCount;
import hr.nevenjakopcic.lettercounter.LetterCounter;

import java.io.FileNotFoundException;

public class VowelCounter {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Filename argument not provided.");
            return;
        }

        String filename = args[0];

        try {
            LetterCount result = LetterCounter.count(filename);

            System.out.println("Vowels: " + result.vowels());
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}