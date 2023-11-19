#ifndef MATRIX_H
#define MATRIX_H

#include <stdio.h>
#include <stdlib.h>

typedef struct Matrix
{
	int rows;
	int cols;
	float **matrix;
} Matrix;

Matrix *new_matrix(int rows, int cols);
Matrix *input_new_matrix();
void print_matrix(const Matrix *const matrix);
Matrix *multiply_matrices(const Matrix *const matrix1, const Matrix *const matrix2);
void free_matrix(Matrix *matrix);

#endif