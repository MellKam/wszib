;Artem Melnyk, 1k212, środa
DATA1 segment
  number_start db "    0000", 10, 13, "$"
  txt2 db "1111", 10, 13, "$"
DATA1 ends

CODE1 segment
  ASSUME cs:CODE1, ds:DATA1, ss:STOS1

START:
  mov ax, seg STOS1
  mov ss, ax
  mov sp, offset top

  mov cx, 3

outer_loop:
  push cx
  mov cx, 3

inner_loop:
  push cx
  call print_number
  pop cx
  loop inner_loop

  call print_ones
  pop cx
  loop outer_loop

  mov ah, 4ch
  int 21h

print_number: 
  mov ax, seg number_start
  mov ds, ax
  mov dx, offset number_start

  mov ah, 9
  int 21h
  ret

print_ones:
  mov ax, seg txt2
  mov ds, ax
  mov dx, offset txt2

  mov ah, 9
  int 21h
  ret

CODE1 ends
STOS1 segment STACK
  dw 256 dup(?)
  top dw ?
STOS1 ends

END START
