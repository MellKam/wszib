#include <stdio.h>
#include <stdlib.h>

typedef struct BinaryTreeNode
{
	int data;
	struct BinaryTreeNode *left;
	struct BinaryTreeNode *right;
} BinaryTreeNode;

void print_tree(BinaryTreeNode *node)
{
	if (node == NULL)
		return;

	printf("%d\n", node->data);
	print_tree(node->left);
	print_tree(node->right);
}
