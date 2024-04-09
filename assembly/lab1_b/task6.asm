;Artem Melnyk, 1k212, czwartek 9:45
DATA1 segment
  x DW 0
  y DW 0
  color DB 0
DATA1 ends

CODE1 segment
  ASSUME cs:CODE1, ds:DATA1, ss:STACK1

set_pixel_320:
  mov ax, 0A000h
  mov es, ax
  mov ax, y
  mov dx, 320
  mul dx
  mov dx, x
  add ax, dx
  mov di, ax
  mov al, color
  mov es:[di], al
  ret

START:
  mov ax, seg STACK1
  mov ss, ax
  mov sp, offset top

  mov ah, 00h
  mov al, 13h
  int 10h

  mov ax, 0
  int 16h

  mov x, 0
  mov color, 0
  mov cx, 256

draw_lines:
  push cx
  mov cx, 100

  mov y, 0

draw_line:
  push cx
  call set_pixel_320
  pop cx
  inc y
  loop draw_line

  pop cx
  inc x
  inc color
  loop draw_lines

  mov ax, 0
  int 16h

  mov ah, 00h
  mov al, 03h
  int 10h

  mov ah, 4ch
  int 21h
CODE1 ends

STACK1 segment STACK
  dw 256 dup(?)
  top dw ?
STACK1 ends

END START