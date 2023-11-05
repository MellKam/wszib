import { and, imp, not, or, v } from "./main.ts";

// ((p → q) ∧ (r → q)) → ((¬q ∨ ¬q) → (¬p ∨ ¬r))
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
