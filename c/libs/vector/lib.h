#ifndef VECTOR_H
#define VECTOR_H

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

Vector* new_vector(size_t capacity);
Vector* vector_from_array(int* array, size_t array_length);
void free_vector(Vector* vector);

void print_vector(Vector* vector);

void push_to_vector(Vector* vector, int items_length, ...);
void resize_vector(Vector* vector, size_t new_size);

#endif