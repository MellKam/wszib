#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>

typedef struct Vector
{
	size_t length;
	size_t capacity;
	int* _vector;
} Vector;

Vector* new_vector(size_t capacity) {
	Vector* vector = (Vector*)malloc(sizeof(Vector));

	if (capacity != 0) {
		vector->_vector = (int*)malloc(sizeof(int) * capacity);
		vector->capacity = capacity;
	}

	vector->length = 0;
	return vector;
}

Vector* vector_from_array(int* array, size_t array_length) {
	Vector* vector = new_vector(array_length);
	memcpy(vector->_vector, array, sizeof(int) * array_length);
	vector->length = array_length;
	return vector;
}

void push_to_vector(Vector* vector, int items_length, ...) {
	if (items_length == 0) return;

	va_list args;
  va_start(args, items_length);

	if (vector->capacity == 0) {
		vector->_vector = (int*)malloc(sizeof(int) * items_length);

		for (int i = 0; i < items_length; i++) {
			vector->_vector[i] = va_arg(args, int);
		}

		vector->capacity = items_length;
		vector->length = items_length;
		return;
	}

	if (vector->length + items_length <= vector->capacity) {
		for (int i = 0; i < items_length; i++) {
			vector->_vector[vector->length + i] = va_arg(args, int);
		}

		vector->length = vector->length + items_length;
		return;
	}

	vector->capacity = vector->length + items_length;
	vector->_vector = (int*)realloc(vector->_vector, sizeof(int) * vector->capacity);

	for (int i = 0; i < items_length; i++) {
		vector->_vector[vector->length + i] = va_arg(args, int);
	}

	vector->length = vector->capacity;
}

void resize_vector(Vector* vector, size_t new_size) {
	if (vector->capacity == new_size) return;

	vector->_vector = (int*)realloc(vector->_vector, sizeof(int) * new_size);
	vector->capacity = new_size;

	if (vector->length > new_size) {
		vector->length = new_size;
	}
}

void free_vector(Vector* vector) {
	free(vector->_vector);
	free(vector);
}

void print_vector(Vector* vector) {
	printf("[ ");
	for (int i = 0; i < vector->length; i++) {
		printf("%d ", vector->_vector[i]);
	}
	printf("]\n");
}
