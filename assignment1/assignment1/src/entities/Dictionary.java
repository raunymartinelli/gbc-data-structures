// Made with love by Bruno 🧡

package entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class Dictionary {
    private final int MAX_SIZE = 1500;
    private final WordInfo[] dictionary;
    private int current;

    public Dictionary() {
        this.dictionary = new WordInfo[MAX_SIZE];
        this.current = 0;
    }

    public boolean add(String _word) {
        return add(_word, "Undefined");
    }

    public boolean add(String _word, String _meaning) {
        if (this.current >= this.MAX_SIZE) return false;
        if (exists(_word)) return false;

        dictionary[this.current++] = new WordInfo(_word, _meaning);
        quickSort(0, current - 1);
        return true;
    }

    public boolean delete(String _word) {
        _word = _word.toLowerCase();
        int item_index = binSearch(_word);
        if (item_index == -1) return false;

        dictionary[item_index] = dictionary[--current];
        quickSort(0, current - 1);
        return true;
    }

    public boolean exists(String _word) {
        _word = _word.toLowerCase();
        return binSearch(_word) != -1;
    }

    public String getMeaning(String _word) {
        _word = _word.toLowerCase();
        int item_index = binSearch(_word);
        if (item_index == -1) return "Word not found!";

        return this.dictionary[item_index].getMeaning();
    }

    public int getCount() {
        return this.current;
    }

    public String printWordList() {
        StringBuilder sb = new StringBuilder();
        stringDictionaryIterator(x -> sb.append(x.getWord()).append("\n"));
        return sb.toString();
    }

    public String printDictionary() {
        StringBuilder sb = new StringBuilder();
        stringDictionaryIterator(x -> sb.append(x.getWord()).append(" - ").append(x.getMeaning()).append("\n"));
        return sb.toString();
    }

    private void stringDictionaryIterator(Consumer<WordInfo> _action) {
        for (int i = 0; i < this.current; i++) {
            _action.accept(this.dictionary[i]);
        }
    }

    private int binSearch(String _word) {
        int hi = current - 1, lo = 0, mid;
        while (lo <= hi) {
            mid = (hi + lo) / 2;
            if (dictionary[mid].isGreaterThan(_word)) hi = mid - 1;
            else if (dictionary[mid].isLessThan(_word)) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    private void quickSort(int _lo, int _hi) {
        if (_lo > _hi) return;

        int mid = partition(_lo, _hi);
        quickSort(mid + 1, _hi);
        quickSort(_lo, mid - 1);
    }

    private int partition(int _lo, int _hi) {
        int ordered = _lo - 1;

        for (int i = _lo; i < _hi; i++) {
            if (this.dictionary[i].isLessThan(this.dictionary[_hi])) {
                swap(i, ++ordered);
            }
        }

        swap(_hi, ++ordered);
        return ordered;
    }

    private void swap(int _prev, int _new) {
        WordInfo tmp = this.dictionary[_prev];
        this.dictionary[_prev] = this.dictionary[_new];
        this.dictionary[_new] = tmp;
    }

    public static Dictionary loadDictionary() {
        Dictionary dict = new Dictionary();
        Path filePath = Path.of("/words.txt");
        String[] content;
        try {
            content = Files.readString(Path.of("./out/production/assignment1/words.txt")).split("\n");
        } catch (IOException e) {
            return dict;
        }

        int lines = Integer.parseInt(content[0].trim());
        for (int i = 1; i < lines + 1; i++) {
            dict.add(content[i].trim());
        }

        return dict;
    }
}
