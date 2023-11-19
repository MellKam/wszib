#include "../lib.h"
#include <stdlib.h>

void basic_test()
{
	LinkedList list = {.first = NULL, .last = NULL};

	LinkedListItem item = {.data = 5, .next = NULL, .prev = NULL};
	push(&list, &item);

	if (list.first == &item)
	{
		printf("Test passed!\n");
	}
	else
	{
		printf("List first item is not our target, got %p", &item);
		exit(1);
	}

	if (list.last == &item)
	{
		printf("Test passed!\n");
	}
	else
	{
		printf("List last item is not our target, got %p", &item);
		exit(1);
	}

	int length = get_list_length(&list);
	if (length == 1)
	{
		printf("Test passed!\n");
	}
	else
	{
		printf("List length not equal 1, got %d\n", length);
		exit(1);
	}
}

int main()
{
	basic_test();
}