#include "../libs/binary-tree/lib.h"

int main()
{
	BinaryTreeNode tree_root = {.data = 1,
															.left = NULL,
															.right = NULL};

	BinaryTreeNode tree_1 = {.data = 2,
													 .left = NULL,
													 .right = NULL};

	tree_root.right = &tree_1;

	BinaryTreeNode *tree_2 = (BinaryTreeNode*)malloc(sizeof(BinaryTreeNode));
	tree_2->data = 3;

	printf("%d\n", tree_2->data);

	tree_root.left = tree_2;

	print_tree(&tree_root);
}