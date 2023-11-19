Gra w życie:

Gra toczy się na planszy o rozmiarze nXn podzielonej na nXn kwadratowych komórek. Każda komórka ma ośmiu "sąsiadów", czyli komórki przylegające do niej bokami i rogami. Każda komórka może znajdować się w jednym z dwóch stanów: może być albo "żywa" (włączona), albo "martwa" (wyłączona). Stany komórek zmieniają się w pewnych jednostkach czasu. Stan wszystkich komórek w pewnej jednostce czasu jest używany do obliczenia stanu wszystkich komórek w następnej jednostce. Po obliczeniu wszystkie komórki zmieniają swój stan dokładnie w tym samym momencie. Stan komórki zależy tylko od liczby jej żywych sąsiadów.

Reguły gry według Conwaya:

1. Martwa komórka, która ma dokładnie 3 żywych sąsiadów, staje się żywa w następnej jednostce czasu (rodzi się)
2. Żywa komórka z 2 albo 3 żywymi sąsiadami pozostaje nadal żywa; przy innej liczbie sąsiadów umiera (z "samotności" albo "zatłoczenia").
   
Napisz program symulujący stan gry w kolejnych chwilach czasu. Rozmiar planszy n jest podany parametrem programu. Stan początkowy generowany jest losowo z udziałem "żywych" komórek 30%. Kolejne stany wyświetlają się po naciśnięciu Enter.