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

/**
 * This class provides a single public method for counting consonants and vowels in a given
 * .txt or .xml file, represented in the returned {@link LetterCount} object.
 */
public final class LetterCounter {

    /**
     * A {@link String} representation of expected (lower case) vowels.
     */
    private static final String vowels = "aeiou";
    /**
     * A {@link String} representation of expected (lower case) consonants.
     */
    private static final String consonants = "qwrtzpšđsdfghjklčćžyxcvbnm";

    /**
     * <p>Counts the number of vowels and consonants in a given file.
     * The file should be either of .txt or .xml format, otherwise,
     * an {@link IllegalArgumentException} will be raised.
     * </p>
     * <p>If the provided file is of .txt format, all occurrences of vowels and consonants
     * will be counted. However, if the file provided is of .xml format, only vowels in
     * the file's elements and attributes will be counted, while vowels in their names will not be.
     * Conversely, all consonants in the file will be counted.</p>
     * @param filename A {@link String} representation of the name or path of the file
     * @return A {@link LetterCount} object representing the counted vowels and consonants
     * @throws FileNotFoundException Raised if the specified file is not found
     */
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

    /**
     * Counts the number of vowels and consonants in a given .txt file.
     * @param file The given .txt {@link File}
     * @return A {@link LetterCount} object representing the counted vowels and consonants
     * @throws FileNotFoundException Raised if the specified file isn't found
     */
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

    /**
     * Counts the number of vowels and consonants in a given .xml file.
     * Only counts vowels in values of elements and attributes. Consonants are counted
     * throughout the entire file, using the {@link LetterCounter#countInTxt(File)} method.
     * @param file The given .txt {@link File}
     * @return A {@link LetterCount} object representing the counted vowels and consonants
     * @throws FileNotFoundException Raised if the specified file isn't found
     */
    private static LetterCount countInXml(File file) throws FileNotFoundException {
        int vowelCount = countVowelsInXml(file);
        int consonantCount = countInTxt(file).consonants();

        return new LetterCount(vowelCount, consonantCount);
    }

    /**
     * Counts the number of vowels in the element and attribute values in a given .xml file.
     * @param file The given .xml {@link File}
     * @return number of vowels counted
     */
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

    /**
     * Counts the number of vowels in a given {@link String}.
     * @param source The given {@link String}
     * @return number of vowels counted
     */
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
