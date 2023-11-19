#include "../libs/linked-list/lib.h"

int main()
{
	LinkedList list = {.first = NULL, .last = NULL};

	LinkedListItem item = {.data = 5, .next = NULL, .prev = NULL};
	push(&list, &item);

	LinkedListItem item2 = {.data = 8, .next = NULL, .prev = NULL};
	push(&list, &item2);

	LinkedListItem item3 = {.data = 2, .next = NULL, .prev = NULL};

	insert_by_index(&list, 0, &item3);

	print_list(&list);
}