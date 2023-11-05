import { and, imp, not, or, v } from "./main.ts";

// ((p→q)∧(r→q))→((¬q∨¬q)→(¬p∨¬r))
const Q = imp(
  and(imp(v("p"), v("q")), imp(v("r"), v("q"))),
  imp(or(not(v("q")), not(v("q"))), or(not(v("p")), not(v("r")))),
);

try {
  console.log(Q.can_be(1));
  console.log("Q is tautology");
} catch (_) {
  console.log("Q is not tautology");
}

// Equivalence ↔
// Negation ¬
// Alternative ∨
// Conjuction ∧
// Implication →
// XOR ⊕

try {
  console.time("C");
  console.log(
    imp(
      or(and(v("p"), v("q")), and(v("r"), v("s"))),
      and(or(not(v("p")), not(v("q"))), or(not(v("r")), not(v("s")))),
    ).can_be(1),
  );
} catch (error) {
  console.error(error);
} finally {
  console.timeEnd("C");
}

try {
  console.time("X");
  // (p∧q∧r∧s∧t)→(¬u∨¬v∨¬w∨¬x∨¬y∨¬z)
  and(
    imp(v("p"), v("p")),
    and(
      imp(v("q"), v("q")),
      and(
        imp(v("r"), v("r")),
        and(
          imp(v("s"), v("s")),
          and(
            imp(v("t"), v("t")),
            and(
              imp(v("u"), v("u")),
              and(
                imp(v("v"), v("v")),
                and(
                  imp(v("w"), v("w")),
                  and(
                    imp(v("x"), v("x")),
                    imp(v("y"), v("y")),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    ),
  ).can_be(1);
} catch (error) {
  console.error(error);
} finally {
  console.timeEnd("X");
}
