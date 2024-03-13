;Artem Melnyk, 1k212, środa
DATA1 segment
  number_start db "Hello world!", "$"
DATA1 ends

CODE1 segment
  ASSUME cs:CODE1, ds:DATA1, ss:STOS1

START:
  mov ax, seg STOS1
  mov ss, ax
  mov sp, offset top

  mov ax, seg number_start
  mov ds, ax
  mov dx, offset number_start

  mov ah, 9
  int 21h
  mov ah, 4ch
  int 21h
CODE1 ends
STOS1 segment STACK
  dw 256 dup(?)
  top dw ?
STOS1 ends

END START
