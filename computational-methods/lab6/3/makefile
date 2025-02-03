# Definicja zmiennych
CC = gcc              # Kompilator C
CFLAGS = -Wall -Wextra -std=c99 
TARGET = root_finder   # Nazwa pliku wykonywalnego
SRC = main.c           # Plik źródłowy
OBJ = main.o           # Pliki obiektowe

# Reguła domyślna: kompilowanie programu
all: $(TARGET)

# Tworzenie pliku wykonywalnego
$(TARGET): $(OBJ)
	$(CC) $(OBJ) -o $(TARGET)

# Kompilowanie pliku źródłowego na plik obiektowy
main.o: main.c
	$(CC) $(CFLAGS) -c main.c

# Czyszczenie plików obiektowych i pliku wykonywalnego
clean:
	rm -f $(OBJ) $(TARGET)

# Reguła do uruchomienia programu
run: $(TARGET)
	./$(TARGET)