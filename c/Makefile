BUILD_DIR = build
CC = gcc

build_dir:
	if [ ! -d $(BUILD_DIR) ]; then mkdir $(BUILD_DIR); fi

matrix.o: ./libs/matrix/lib.c ./libs/matrix/lib.h build_dir
	$(CC) -c ./libs/matrix/lib.c -o ./$(BUILD_DIR)/matrix.o

linked-list.o: ./libs/linked-list/lib.c ./libs/linked-list/lib.h build_dir
	$(CC) -c ./libs/linked-list/lib.c -o ./$(BUILD_DIR)/linked-list.o

vector.o: ./libs/vector/lib.c ./libs/vector/lib.h build_dir
	$(CC) -c ./libs/vector/lib.c -o ./$(BUILD_DIR)/vector.o

binary-tree.o: ./libs/binary-tree/lib.c ./libs/binary-tree/lib.h build_dir
	$(CC) -c ./libs/binary-tree/lib.c -o ./$(BUILD_DIR)/binary-tree.o

example-matrix: matrix.o build_dir
	$(CC) -o ./$(BUILD_DIR)/example-matrix ./examples/matrix.c ./$(BUILD_DIR)/matrix.o && ./$(BUILD_DIR)/example-matrix

example-linked-list: linked-list.o build_dir
	$(CC) -o ./$(BUILD_DIR)/example-linked-list ./examples/linked-list.c ./$(BUILD_DIR)/linked-list.o && ./$(BUILD_DIR)/example-linked-list

example-binary-tree: binary-tree.o build_dir
	$(CC) -o ./$(BUILD_DIR)/example-binary-tree ./examples/binary-tree.c ./$(BUILD_DIR)/binary-tree.o && ./$(BUILD_DIR)/example-binary-tree

example-vector: vector.o build_dir
	$(CC) -o ./$(BUILD_DIR)/example-vector ./examples/vector.c ./$(BUILD_DIR)/vector.o && ./$(BUILD_DIR)/example-vector

test-linked-list: linked-list.o build_dir
	$(CC) -o ./$(BUILD_DIR)/test-linked-list ./libs/linked-list/__tests__/unit-tests.c ./$(BUILD_DIR)/linked-list.o && ./$(BUILD_DIR)/test-linked-list

clean:
	rm -r ./$(BUILD_DIR)/*