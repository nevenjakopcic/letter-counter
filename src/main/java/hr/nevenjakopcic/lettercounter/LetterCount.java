package hr.nevenjakopcic.lettercounter;

public class LetterCount {

    private final int vowels;
    private final int consonants;

    public LetterCount(int vowels, int consonants) {
        this.vowels = vowels;
        this.consonants = consonants;
    }

    public int vowels() {
        return vowels;
    }

    public int consonants() {
        return consonants;
    }

    public int total() {
        return vowels + consonants;
    }

    @Override
    public String toString() {
        return String.format(
                "Letter count: vowels = %d; consonants = %d; total = %d",
                vowels(), consonants(), total()
        );
    }
}
