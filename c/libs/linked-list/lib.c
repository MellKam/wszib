#include <stdio.h>
#include <stdbool.h>

typedef struct LinkedListItem
{
	int data;
	struct LinkedListItem *next;
	struct LinkedListItem *prev;
} LinkedListItem;

typedef struct LinkedList
{
	LinkedListItem *first;
	LinkedListItem *last;
} LinkedList;

bool push(LinkedList *list, LinkedListItem *item)
{
	if (list == NULL || item == NULL)
	{
		return false;
	}
	if (list->first == NULL)
	{
		list->first = item;
		list->last = item;
		return true;
	}

	list->last->next = item;
	item->prev = list->last;
	list->last = item;
	return true;
}

void print_list_item(LinkedListItem *item)
{
	if (item == NULL)
	{
		printf("item[] NULL\n");
		return;
	}

	printf("item[%p] { data: %d, prev: %p, next: %p }\n", item, item->data, item->prev, item->next);
}

void print_list(LinkedList *list)
{
	if (list == NULL)
	{
		printf("Cannot print NULL as list");
		return;
	}
	LinkedListItem *item = list->first;

	if (item == NULL)
	{
		printf("List is empty");
		return;
	}

	while (true)
	{
		print_list_item(item);

		item = item->next;
		if (item == NULL)
		{
			return;
		}
	}
}

int get_list_length(LinkedList *list)
{
	if (list == NULL)
	{
		return -1;
	}

	LinkedListItem *item = list->first;

	if (item == NULL)
	{
		return 0;
	}

	int length = 0;

	while (true)
	{
		length++;
		item = item->next;
		if (item == NULL)
		{
			return length;
		}
	}
}

LinkedListItem *pop(LinkedList *list)
{
	if (list == NULL)
	{
		return NULL;
	}

	LinkedListItem *last = list->last;

	if (last == NULL)
	{
		return NULL;
	}

	list->last->prev->next = NULL;
	list->last = list->last->prev;

	last->prev = NULL;
	return last;
}

LinkedListItem *get_item_by_index(LinkedList *list, int index)
{
	int length = get_list_length(list);

	if (index > length)
	{
		return NULL;
	}

	LinkedListItem *item = list->first;

	for (int i = 0; i < index; i++)
	{
		if (item->next == NULL)
		{
			return NULL;
		}

		item = item->next;
	}

	return item;
}

/*
	Issue #1
	What if you pass `list_item` that not included in list?
	It can be fixed by storing the `list` pointer in every
	LinkedListItem, but we will need more memory. Or we can
	iterate through list to find this item and check if it exists.

	Issue #2
	What if `list_item` has next in it and we push it to the end? After insert list will have unconsistend data in it.
*/
bool insert_next_to(LinkedList *list, LinkedListItem *list_item, LinkedListItem *insert_item)
{
	if (list == NULL || list_item == NULL || insert_item == NULL)
	{
		return false;
	}

	LinkedListItem *list_item_next = list_item->next;
	if (list_item_next == NULL)
	{
		// next item not exits, so we can just push it
		return push(list, insert_item);
	}

	// next item exists
	list_item->next = insert_item;

	insert_item->prev = list_item;
	insert_item->next = list_item_next;

	list_item_next->prev = insert_item;
	return true;
}

bool insert_by_index(LinkedList *list, int index, LinkedListItem *item)
{
	if (list == NULL || item == NULL)
	{
		return false;
	}

	LinkedListItem *target = get_item_by_index(list, index);
	if (target == NULL)
	{
		return false;
	}

	return insert_next_to(list, target, item);
}