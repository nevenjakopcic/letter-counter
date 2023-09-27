package hr.nevenjakopcic;

import hr.nevenjakopcic.lettercounter.LetterCount;
import hr.nevenjakopcic.lettercounter.LetterCounter;

import java.io.FileNotFoundException;

public class VowelCounter {
    public static void main(String[] args) {
        LetterCount result;

        try {
            result = LetterCounter.count("D:/Desktop/dummy.xml");

            System.out.println(result);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}