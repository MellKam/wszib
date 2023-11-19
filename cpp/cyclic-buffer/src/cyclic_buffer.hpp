#pragma once

#include <iostream>
#include <stdexcept>
#include <initializer_list>
#include <array>

template <typename T, size_t CAPACITY = 8>
class CyclicBuffer
{
private:
  std::array<T, CAPACITY> _buffer; // I used std::array because it's safer to use
  size_t _read_idx;                // p_read => _read_idx
  size_t _write_idx;               // p_write => _write_idx
  size_t _size;                    // count => _size

  const T &get_at(size_t index) const
  {
    if (index >= _size)
    {
      throw std::out_of_range("Index out of bound");
    }

    return _buffer[(_read_idx + index) % CAPACITY];
  };

public:
  /**
   * @brief Default constructor. Initializes an empty cyclic buffer.
   */
  CyclicBuffer() noexcept : _read_idx(0), _write_idx(0), _size(0){};

  /**
   * @brief Constructor that initializes the cyclic buffer with the elements from an initializer list.
   *
   * @param init_list The initializer list of elements.
   * @throws std::out_of_range If the size of the initializer list exceeds the buffer capacity.
   */
  CyclicBuffer(std::initializer_list<T> init_list) : CyclicBuffer()
  {
    if (init_list.size() == 0)
    {
      return;
    }
    if (init_list.size() > CAPACITY)
    {
      throw std::out_of_range("The size of the initializer is larger than the buffer capacity");
    }

    for (T item : init_list)
    {
      put(item);
    }
  };

  /**
   * @brief Returns the current size of the buffer.
   *
   * @return size_t The size of the buffer.
   */
  size_t size() const
  {
    return _size;
  };

  /**
   * @brief Checks if the buffer is empty.
   *
   * @return bool True if the buffer is empty, false otherwise.
   */
  bool empty() const
  {
    return _size == 0;
  };

  /**
   * @brief Checks if the buffer is full.
   *
   * @return bool True if the buffer is full, false otherwise.
   */
  bool full() const
  {
    return _size == CAPACITY;
  };

  /**
   * @brief Retrieves and removes the first element from the buffer.
   *
   * @return T The first element in the buffer.
   * @throws std::out_of_range If the buffer is empty.
   */
  T get()
  {
    if (empty())
    {
      throw std::out_of_range("Cannot read from empty buffer");
    }

    T value = _buffer[_read_idx];
    _read_idx = (_read_idx + 1) % CAPACITY;
    _size--;
    return value;
  };

  /**
   * @brief Adds an element to the end of the buffer.
   *
   * @param value The value to be added to the buffer.
   * @throws std::out_of_range If the buffer is full.
   */
  void put(T value)
  {
    if (full())
    {
      throw std::out_of_range("Cannot write to full buffer");
    }

    _buffer[_write_idx] = value;
    _write_idx = (_write_idx + 1) % CAPACITY;
    _size++;
  };

  /**
   * @brief Prints the elements in the buffer.
   *        The elements are printed within square brackets.
   */
  void print() const
  {
    if (empty())
    {
      std::cout << "[ ]" << std::endl;
      return;
    }

    std::cout << "[ ";
    for (size_t i = 0; i < _size; ++i)
    {
      std::cout << get_at(i);
      if (i != _size - 1)
      {
        std::cout << ", ";
      }
    }
    std::cout << " ]" << std::endl;
  };

  class ReadonlyIterator;

  /**
   * @brief Returns an iterator pointing to the beginning of the buffer.
   *
   * @return ReadonlyIterator An iterator pointing to the beginning of the buffer.
   */
  ReadonlyIterator begin() const
  {
    return ReadonlyIterator(*this, 0);
  };

  /**
   * @brief Returns an iterator pointing to the end of the buffer.
   *
   * @return ReadonlyIterator An iterator pointing to the end of the buffer.
   */
  ReadonlyIterator end() const
  {
    return ReadonlyIterator(*this, _size);
  };

  /**
   * @brief Checks if the buffer contains a specific value.
   *
   * @param value The value to search for.
   * @return bool True if the value is found in the buffer, false otherwise.
   */
  bool contains(const T &value) const
  {
    for (const T &item : *this)
    {
      if (item == value)
        return true;
    }
    return false;
  };
};

/**
 * @brief An iterator class for iterating over the elements in the buffer in a read-only manner.
 */
template <typename T, size_t CAPACITY>
class CyclicBuffer<T, CAPACITY>::ReadonlyIterator
{
private:
  const CyclicBuffer &_buffer;
  size_t _index;

public:
  ReadonlyIterator(const CyclicBuffer &buffer, size_t index)
      : _buffer(buffer), _index(index){};

  const T &operator*() const
  {
    return _buffer.get_at(_index);
  };

  ReadonlyIterator &operator++()
  {
    _index++;
    return *this;
  };

  bool operator==(const ReadonlyIterator &other) const
  {
    return _index == other._index;
  };

  bool operator!=(const ReadonlyIterator &other) const
  {
    return !(*this == other);
  };
};
