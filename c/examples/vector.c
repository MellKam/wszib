#include "../libs/vector/lib.h"

void print_vector_info(Vector* vector) {
	printf("Length: %ld\n", vector->length);
	printf("Capacity: %ld\n", vector->capacity);
	print_vector(vector);
}

void basic() {
	Vector* vector = new_vector(3);
	push_to_vector(vector, 3, 1, 2, 3);

	print_vector_info(vector);
	/*
		Length: 3
		Capacity: 3
		[ 1 2 3 ]
	*/

	free_vector(vector);
}

void from_array() {
int array[] = {1, 2, 3, 4, 5};
	Vector* vector = vector_from_array(
		array, 
		sizeof(array) / sizeof(int)
	);

	print_vector_info(vector);
	/*
		Length: 5
		Capacity: 5
		[ 1 2 3 4 5 ]
	*/

	push_to_vector(vector, 4, 6, 7, 8, 9);

	print_vector_info(vector);
	/*
		Length: 9
		Capacity: 9
		[ 1 2 3 4 5 6 7 8 9 ]
	*/

	free_vector(vector);
}

void resize() {
	Vector* vector = new_vector(0);
	push_to_vector(vector, 3, 1, 2, 3);
	resize_vector(vector, 100);

	print_vector_info(vector);
	/*
		Length: 3
		Capacity: 100
		[ 1 2 3 ]
	*/

	push_to_vector(vector, 1, 4);

	print_vector_info(vector);
	/*
		Length: 4
		Capacity: 100
		[ 1 2 3 4 ]
	*/

	resize_vector(vector, 1);

	print_vector_info(vector);
	/*
		Length: 1
		Capacity: 1
		[ 1 ]
	*/

	free_vector(vector);
}

int main() {
	basic();
	printf("\n");
	from_array();
	printf("\n");
	resize();
}