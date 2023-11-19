Napisz klasę wzorcową CyclicBuffer reprezentującą bufor cykliczny o typie
elementu zadanym pierwszym parametrem wzorca i liczbie elementów zadanej drugim
parametrem. Proponowany schemat klasy:

```cpp
template<typename T, int CAPACITY = 8>
class CyclicBuffer {
private:
	T buffer[CAPACITY];
	int p_read; // index to read
	int p_write; // index to write
	int count; // number of elements
public:
	CyclicBuffer();
	bool empty(); // check if empty
	bool full();  // check if full
	T get();           // get element from buffer (if not empty)
	void put(T value); // put value to buffer (if not full)
	void print() const; // print buffer
};
```

Napisz funkcję `main()` pokazującą działanie klasy dla bufora liczb całkowitych
o defaultowej pojemności a następnie bufora zawierającego obiekty prostej klasy
napisanej przez siebie (o pojemności 16).
