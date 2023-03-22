// Made with a lot of love by Bruno 🧡

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

    }
}

abstract class Hashtable {
    protected String[] arr;
    protected int current;
    protected int max;
    protected float load_factor;

    public Hashtable(int _max, float _load_factor) {
        this.arr = new String[_max];
        this.current = 0;
        this.max = _max;
        this.load_factor = _load_factor;
    }

    protected int HashFunction(String _value) {
        int total = 0;
        for (int i = 0; i < _value.length(); i++) {
            total += _value.charAt(i) * (i + 1);
        }
        return total % this.max;
    }

    abstract boolean Add(String _value);

    abstract int Retrieve(String _value);

    public boolean Update(String _old, String _new) {
        if (this.Remove(_old))
            return this.Add(_new);
        return false;
    }

    public boolean Remove(String _value) {
        int index = this.Retrieve(_value);

        if (index == -1)
            return false;

        this.arr[index] = "-deleted-";
        this.current--;
        return true;
    }

    public String toString() {
        StringBuilder f = new StringBuilder();
        for (int i = 0; i < this.max; i++) {
            f.append("i: ").append(i).append(" | ").append(this.arr[i]).append("\n");
        }
        return f.toString();
    }
}

class Hashtable_linear extends Hashtable {
    public Hashtable_linear(int _max, float _load_factor) {
        super(_max, _load_factor);
    }

    boolean Add(String _value) {
        if ((this.current * 1.0) / this.max >= this.load_factor)
            return false;

        int hash = this.HashFunction(_value);
        while (this.arr[hash] != null && !this.arr[hash].equals("")) {
            hash = (hash + 1) % this.max;
        }

        this.arr[hash] = _value;
        this.current++;
        return true;
    }

    int Retrieve(String _value) {
        int hash = this.HashFunction(_value);
        while (this.arr[hash] != null && !this.arr[hash].equals(_value))
            hash = (hash + 1) % this.max;
        return hash;
    }
}

class Hashtable_quadratic extends Hashtable {
    public Hashtable_quadratic(int _max, float _load_factor) {
        super(_max, _load_factor);
    }

    boolean Add(String _value) {
        if ((this.current * 1.0) / this.max >= this.load_factor)
            return false;

        int hash = this.HashFunction(_value);
        int starthash = hash;
        int increment = 0;
        while (this.arr[hash] != null && !this.arr[hash].equals("")) {
            increment++;
            hash = (starthash + increment * increment) % this.max;
        }

        this.arr[hash] = _value;
        this.current++;
        return true;
    }

    int Retrieve(String _value) {
        int hash = this.HashFunction(_value);
        int starthash = hash;
        int increment = 0;
        while (this.arr[hash] != null && !this.arr[hash].equals("")) {
            increment++;
            if (this.arr[hash].equals(_value)) return hash;
            hash = (starthash + increment * increment) % this.max;
        }
        return -1;
    }
}

class Hashtable_doublehash extends Hashtable {
    public Hashtable_doublehash(int _max, float _load_factor) {
        super(_max, _load_factor);
    }

    public int HashFunction2(String _value) {
        int total = 0;
        for (int i = 0; i < _value.length(); i++)
            total += _value.charAt(i) * (i + 1);
        return (int) (Math.PI - (total % Math.PI));
    }

    boolean Add(String _value) {
        if ((this.current * 1.0) / this.max >= this.load_factor)
            return false;

        int hash = this.HashFunction(_value);
        int increment = this.HashFunction2(_value);
        while (this.arr[hash] != null && !this.arr[hash].equals("")) {
            hash = (hash + increment) % this.max;
        }

        this.arr[hash] = _value;
        this.current++;
        return true;
    }

    int Retrieve(String _value) {
        int hash = this.HashFunction(_value);
        int increment = this.HashFunction2(_value);
        while (this.arr[hash] != null && !this.arr[hash].equals("")) {
            if (this.arr[hash].equals(_value)) return hash;
            hash = (hash + increment) % this.max;
        }
        return -1;
    }
}