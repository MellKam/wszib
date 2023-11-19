#include <stdio.h>
#include <stdlib.h>

typedef struct Matrix
{
	int rows;
	int cols;
	float **_matrix;
} Matrix;

void print_matrix(const Matrix *const matrix)
{
	for (int r = 0; r < matrix->rows; r++)
	{
		printf("[ ");
		for (int c = 0; c < matrix->cols; c++)
		{
			printf("%.2f ", matrix->_matrix[r][c]);
		}
		printf("]\n");
	}
}

void free_matrix(Matrix *matrix)
{
	for (int r = 0; r < matrix->rows; r++)
	{
		free(matrix->_matrix[r]);
	}
	free(matrix->_matrix);
	free(matrix);
}

Matrix *new_matrix(int rows, int cols)
{
	Matrix *matrix = (Matrix*)malloc(sizeof(Matrix));
	if (matrix == NULL)
	{
		return NULL;
	}

	float **arr = (float**)malloc(sizeof(float *) * rows);

	matrix->cols = cols;
	matrix->rows = rows;
	matrix->_matrix = arr;

	for (int r = 0; r < rows; r++)
	{
		arr[r] = (float*)malloc(sizeof(float) * cols);

		if (arr[r] == NULL)
		{
			free_matrix(matrix);
			return NULL;
		}
	}

	return matrix;
}

Matrix *input_new_matrix()
{
	int rows, cols;

	printf("Input matrix rows count: ");
	scanf("%d", &rows);

	printf("Input matrix cols count: ");
	scanf("%d", &cols);

	Matrix *matrix = new_matrix(rows, cols);
	if (matrix == NULL)
	{
		return NULL;
	}

	for (int r = 0; r < rows; r++)
	{
		for (int c = 0; c < cols; c++)
		{
			printf("Enter matrix[%d][%d]: ", r, c);
			scanf("%f", &(matrix->_matrix[r][c]));
		}
	}

	print_matrix(matrix);

	return matrix;
}

Matrix *multiply_matrices(const Matrix *const matrix1, const Matrix *const matrix2)
{
	if (matrix1->cols != matrix2->rows)
	{
		printf("You cannot multiply this matricies\n");
		return 0;
	}

	Matrix *result = new_matrix(matrix1->rows, matrix2->cols);

	for (int r1 = 0; r1 < matrix1->rows; r1++)
	{
		for (int c2 = 0; c2 < matrix2->cols; c2++)
		{
			float sum = 0;

			for (int i = 0; i < matrix1->cols; i++)
			{
				sum += matrix1->_matrix[r1][i] * matrix2->_matrix[i][c2];
			}

			result->_matrix[r1][c2] = sum;
		}
	}

	return result;
}