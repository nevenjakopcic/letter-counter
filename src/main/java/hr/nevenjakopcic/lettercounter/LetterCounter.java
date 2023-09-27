package hr.nevenjakopcic.lettercounter;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class LetterCounter {

    private static final String vowels = "aeiou";
    private static final String consonants = "qwrtzpšđsdfghjklčćžyxcvbnm";

    public static LetterCount count(String filename) throws FileNotFoundException {
        File file = new File(filename);

        if (!file.isFile()) {
            throw new FileNotFoundException("System could not find specified file: " + filename);
        }

        if (filename.endsWith(".txt")) {
            return countInTxt(file);
        } else if (filename.endsWith(".xml")) {
            return countInXml(file);
        } else {
            throw new IllegalArgumentException("Unsupported file extension.");
        }
    }

    private static LetterCount countInTxt(File file) throws FileNotFoundException {
        int vowelCount = 0;
        int consonantCount = 0;

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            for (char c : line.toLowerCase().toCharArray()) {
                if (vowels.indexOf(c) != -1) {
                    vowelCount++;
                }

                if (consonants.indexOf(c) != -1) {
                    consonantCount++;
                }
            }
        }

        return new LetterCount(vowelCount, consonantCount);
    }

    private static LetterCount countInXml(File file) throws FileNotFoundException {
        int vowelCount = countVowelsInXml(file);
        int consonantCount = countInTxt(file).consonants();

        return new LetterCount(vowelCount, consonantCount);
    }

    private static int countVowelsInXml(File file) {
        int vowelCount = 0;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodes = document.getElementsByTagName("*");

            // count vowels in elements
            vowelCount += countVowels(nodes.item(0).getTextContent());

            // count vowels in attributes
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                NamedNodeMap attributes = node.getAttributes();

                for (int j = 0; j < attributes.getLength(); j++) {
                    vowelCount += countVowels(attributes.item(j).getNodeValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vowelCount;
    }

    private static int countVowels(String source) {
        int vowelCount = 0;

        for (char c : source.toLowerCase().toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                vowelCount++;
            }
        }

        return vowelCount;
    }

    private LetterCounter() {}
}
