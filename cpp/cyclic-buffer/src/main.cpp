#include <iostream>
#include "cyclic_buffer.hpp"
#include <cassert>

void testIntBuffer()
{
  // Test with integer buffer
  CyclicBuffer<int, 8> buffer;
  assert(buffer.empty());
  assert(!buffer.full());
  assert(buffer.size() == 0);

  buffer.put(1);
  buffer.put(2);
  buffer.put(3);
  assert(!buffer.empty());
  assert(!buffer.full());
  assert(buffer.size() == 3);

  assert(buffer.contains(2));
  assert(!buffer.contains(4));

  int value = buffer.get();
  assert(value == 1);
  assert(buffer.size() == 2);

  buffer.put(4);
  buffer.put(5);
  buffer.put(6);
  buffer.put(7);
  buffer.put(8);
  buffer.put(9);
  assert(buffer.full());
  assert(buffer.size() == 8);

  try
  {
    buffer.put(10); // This should throw an exception
    assert(false);  // This line should not be reached
  }
  catch (const std::out_of_range &e)
  {
    // Exception thrown as expected
  }

  buffer.get();   // Removing one element to make room for the next put
  buffer.put(10); // This should not throw an exception
}

void testInitializer()
{
  CyclicBuffer<int, 8> buffer = {1, 2, 3, 4, 5, 6, 7, 8};
  assert(buffer.size() == 8);
  assert(buffer.full());
  buffer.print();

  try
  {
    // Test invalid initializer with more size more than capacity
    CyclicBuffer<int, 2>({1, 2, 3});
    assert(false);
  }
  catch (const std::out_of_range &e)
  {
    // Exception thrown as expected
  }
}

void testStringBuffer()
{
  CyclicBuffer<std::string, 5> buffer = {"Hello", "World"};
  assert(buffer.size() == 2);
  assert(buffer.contains("Hello"));
  assert(!buffer.contains("Test"));

  std::string str = buffer.get();
  assert(str == "Hello");
  assert(buffer.size() == 1);

  // Iterate (readonly) over the buffer using range-based for loop
  for (const std::string &item : buffer)
  {
    std::cout << item << std::endl;
  }
}

class Item
{
public:
  int value;

  Item() {}
  Item(int value) : value(value) {}
};

void testCustomItemBuffer()
{
  CyclicBuffer<Item, 8> buffer;

  // Put items into the buffer
  buffer.put(Item(1));
  buffer.put(Item(2));
  buffer.put(Item(3));

  // Get items from the buffer and print their values
  while (!buffer.empty())
  {
    Item item = buffer.get();
    std::cout << item.value << std::endl;
  }
}

int main()
{
  try
  {
    testIntBuffer();
    testStringBuffer();
    testInitializer();
    testCustomItemBuffer();

    std::cout << "All tests successfully passed!" << std::endl;
  }
  catch (const std::out_of_range &e)
  {
    std::cout << "Error: " << e.what() << std::endl;
  }

  return 0;
}