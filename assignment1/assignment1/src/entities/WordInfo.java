// Made with love by Bruno 🧡

package entities;

public class WordInfo {
    private final String word;
    private String meaning;

    public WordInfo(String _word) {
        this.word = _word.toLowerCase();
        this.meaning = "Undefined";
    }

    public WordInfo(String _word, String _meaning) {
        this.word = _word.toLowerCase();
        this.meaning = _meaning;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isGreaterThan(WordInfo _other) {
        if (_other == null) return false;
        return this.word.compareToIgnoreCase(_other.getWord()) > 0;
    }

    public boolean isLessThan(WordInfo _other) {
        if (_other == null) return false;
        return this.word.compareToIgnoreCase(_other.getWord()) < 0;
    }

    public boolean isGreaterThan(String _other) {
        return this.word.compareToIgnoreCase(_other) > 0;
    }

    public boolean isLessThan(String _other) {
        return this.word.compareToIgnoreCase(_other) < 0;
    }
}
