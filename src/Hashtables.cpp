// Made with a lot of love by Bruno 🧡

#include <bits/stdc++.h>

using namespace std;

namespace HashTable
{
    struct Hashtable
    {
    protected:
        vector<string> *arr;
        int current;
        int max;
        float load_factor;

        int HashFunction(string _value)
        {
            int total = 0;
            for (size_t i = 0; i < _value.length(); i++)
            {
                total += _value.at(i) * (i + 1);
            }
            return total % this->max;
        }

    public:
        Hashtable(int _max, float _load_factor)
        {
            this->arr = new vector<string>(_max);
            this->current = 0;
            this->max = _max;
            this->load_factor = _load_factor;
        }

        virtual bool Add(string _value) = 0;
        virtual int Retrieve(string _value) = 0;
        virtual bool Update(string _old, string _new)
        {
            if (this->Remove(_old))
                return this->Add(_new);
            return false;
        }
        virtual bool Remove(string _value)
        {
            int index = this->Retrieve(_value);

            if (index == -1)
                return false;

            this->arr->at(index) = "🔴";
            this->current--;
            return true;
        }

        virtual bool operator[](string _value)
        {
            return this->Retrieve(_value) != -1;
        }

        string ToString()
        {
            string f;
            for (size_t i = 0; i < this->max; i++)
            {
                f += "i: " + to_string(i) + " | " + this->arr->at(i) + "\n";
            }
            return f;
        }

        ~Hashtable()
        {
            delete arr;
        }
    };

    struct Hashtable_linear : public Hashtable
    {
    public:
        using Hashtable::Hashtable;

        bool Add(string _value) override
        {
            if ((this->current * 1.0f) / this->max >= load_factor)
                return false;

            int hash = this->HashFunction(_value);
            cout << _value << " hashed to " << hash << endl;

            while (!this->arr->at(hash).empty() && this->arr->at(hash) != "🔴")
                hash = (hash + 1) % this->max;

            this->arr->at(hash) = _value;
            current++;
            return true;
        }

        int Retrieve(string _value) override
        {
            int hash = this->HashFunction(_value);

            while (!this->arr->at(hash).empty() && this->arr->at(hash) != "🔴")
            {
                if (this->arr->at(hash) != _value)
                    return hash;
                hash = (hash + 1) % this->max;
            }

            return this->arr->at(hash).empty() || this->arr->at(hash) == "🔴" ? -1 : hash;
        }
    };

    struct Hashtable_quadratic : public Hashtable
    {
    public:
        using Hashtable::Hashtable;

        bool Add(string _value) override
        {
            if ((this->current * 1.0f) / this->max >= load_factor)
                return false;

            int hash = this->HashFunction(_value);
            int starthash = hash;
            int increment = 0;
            cout << _value << " hashed to " << hash << endl;

            while (!this->arr->at(hash).empty() && this->arr->at(hash) != "🔴")
            {
                increment++;
                hash = (starthash + increment * increment) % this->max;
            }

            this->arr->at(hash) = _value;
            current++;
            return true;
        }

        int Retrieve(string _value) override
        {
            int hash = this->HashFunction(_value);
            int starthash = hash;
            int increment = 0;

            while (!this->arr->at(hash).empty() && this->arr->at(hash) != "🔴")
            {
                if (this->arr->at(hash) == _value)
                    return hash;
                increment++;
                hash = (starthash + increment * increment) % this->max;
            }

            return -1;
        }
    };

    struct Hashtable_doublehash : public Hashtable
    {
    private:
        int HashFunction2(string _value)
        {
            int total = 0;
            for (size_t i = 0; i < _value.length(); i++)
                total += _value.at(i) * (i + 1);
            return M_PI - fmod(total, M_PI); // M_PI - fmod(total, M_PI); // this->max / 2; // (total * ()) * M_PI; // fmod(, this->max * 1.0) + 1;
        }

    public:
        using Hashtable::Hashtable;

        bool Add(string _value) override
        {
            if ((this->current * 1.0f) / this->max >= load_factor)
                return false;

            int hash = this->HashFunction(_value);
            int increment = this->HashFunction2(_value);
            cout << _value << " hashed to " << hash << endl;

            while (!this->arr->at(hash).empty() && this->arr->at(hash) != "🔴")
                hash = (hash + increment) % this->max;

            this->arr->at(hash) = _value;
            current++;
            return true;
        }

        int Retrieve(string _value) override
        {
            int hash = this->HashFunction(_value);
            int increment = this->HashFunction2(_value);

            while (!this->arr->at(hash).empty())
            {
                if (this->arr->at(hash) == _value)
                    return hash;
                hash = (hash + increment) % this->max;
            }

            return -1;
        }
    };

    void UnitTest(Hashtable *table)
    {
        table->Add("Peach");
        table->Add("Orange");
        table->Add("Apple");
        table->Add("Avocado");
        table->Add("Banana");
        table->Add("Watermellon");
        table->Add("Strawberry");

        cout << table->ToString() << endl;

        cout << std::boolalpha << table->operator[]("info") << endl;
        cout << std::boolalpha << table->operator[]("Apple") << endl;
        cout << std::boolalpha << table->operator[](" ") << endl;
        cout << std::boolalpha << table->operator[]("Banana") << endl;

        cout << "Watermellon: " << std::boolalpha << table->operator[]("Watermellon") << endl;
        table->Remove("Apple");
        table->Remove("Orange");
        cout << table->ToString() << endl;
        cout << "Apple: " << std::boolalpha << table->operator[]("Apple") << endl;
        cout << "Watermellon: " << std::boolalpha << table->operator[]("Watermellon") << endl;

        table->Add("Orange");
        cout << table->ToString() << endl;
    }

    int Main()
    {
        int max = 10;
        float lf = .7;
        Hashtable_linear linear(max, lf);
        Hashtable_quadratic quadratic(max, lf);
        Hashtable_doublehash doublehash(max, lf);

        cout << "\n****************************** Linear Hashing ******************************\n"
             << endl;
        UnitTest(&linear);
        cout << "\n****************************** Quadratic Hashing ******************************\n"
             << endl;
        UnitTest(&quadratic);
        cout << "\n****************************** Double Hashing ******************************\n"
             << endl;
        UnitTest(&doublehash);

        return 0;
    }
}