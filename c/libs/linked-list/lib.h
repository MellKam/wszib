#ifndef LINKED_LIST_H
#define LINKED_LIST_H

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

bool push(LinkedList *list, LinkedListItem *item);
void print_list_item(LinkedListItem *item);
void print_list(LinkedList *list);
int get_list_length(LinkedList *list);
LinkedListItem *pop(LinkedList *list);
LinkedListItem *get_item_by_index(LinkedList *list, int index);
bool insert_next_to(LinkedList *list, LinkedListItem *list_item, LinkedListItem *insert_item);
bool insert_by_index(LinkedList *list, int index, LinkedListItem *item);

#endif