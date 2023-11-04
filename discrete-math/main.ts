import {
  assertEquals,
  assertThrows,
} from "https://deno.land/std@0.205.0/assert/mod.ts";

type Bit = 0 | 1;
type Expression = {
  type: "bit" | "var" | "func";
  evaluate(variables?: Record<string, Bit>): Bit;
  can_be(value: Bit, variables?: Record<string, Bit>[]): Record<string, Bit>[];
};

// Bit or boolean
export const bit = (value: Bit): Expression => ({
  type: "bit",
  can_be(v, variables = []) {
    if (value !== v) {
      throw new Error(`Can't be ${value}`);
    }
    return variables;
  },
  evaluate() {
    return value;
  },
});

Deno.test("Bit", () => {
  assertEquals(bit(1).can_be(1), []);
  assertThrows(() => bit(1).can_be(0));

  assertEquals(bit(0).can_be(0), []);
  assertThrows(() => bit(0).can_be(1));
});

// Variable
export const v = (name: string): Expression => ({
  type: "var",
  can_be(value, variables = []) {
    if (!variables.length) {
      return [{ [name]: value }];
    }
    const filtered = variables
      .filter((vars) => vars[name] === undefined || vars[name] === value)
      .map((v) => ({ ...v, [name]: value }));
    if (filtered.length === 0) {
      throw new Error(`Can't be ${value}`);
    }
    return filtered;
  },
  evaluate(variables = {}) {
    if (variables[name] === undefined) {
      throw new Error(`Variable ${name} not provided`);
    }
    return variables[name];
  },
});

Deno.test("Variable", () => {
  assertEquals(v("X").can_be(1), [{ X: 1 }]);
  assertEquals(v("X").can_be(0), [{ X: 0 }]);
});

const mergeVariants = (
  x: Record<string, Bit>[],
  y: Record<string, Bit>[],
): Record<string, Bit>[] => {
  if (x.length === 0) return y;
  if (y.length === 0) return x;
  const mergedVariants: Record<string, Bit>[] = [];

  // Helper function to merge two variant objects
  const mergeObjects = (
    obj1: Record<string, Bit>,
    obj2: Record<string, Bit>,
  ): Record<string, Bit> | null => {
    const mergedObj: Record<string, Bit> = { ...obj1 };

    for (const key in obj2) {
      if (mergedObj[key] !== undefined && mergedObj[key] !== obj2[key]) {
        // If a conflict is found in bit values, return null
        return null;
      }
      mergedObj[key] = obj2[key];
    }

    return mergedObj;
  };

  for (const variantX of x) {
    for (const variantY of y) {
      const merged = mergeObjects(variantX, variantY);

      // If the merge is successful (no conflicts), push the merged variant
      if (merged) {
        mergedVariants.push(merged);
      }
    }
  }

  if (mergedVariants.length === 0) {
    throw new Error("No variants");
  }

  return mergedVariants;
};

Deno.test("mergeVariants", () => {
  assertEquals(
    mergeVariants([{ X: 1 }], [{ X: 1 }]),
    [{ X: 1 }],
  );
  assertThrows(
    () => mergeVariants([{ X: 1 }], [{ X: 0 }]),
  );
  assertEquals(
    mergeVariants([{ Y: 0 }], [{ X: 1 }]),
    [{ Y: 0, X: 1 }],
  );
  assertEquals(
    mergeVariants(
      [{ X: 1 }, { Y: 0 }],
      [{ X: 0 }],
    ),
    [{ Y: 0, X: 0 }],
  );
  assertEquals(
    mergeVariants(
      [{ X: 1, Y: 0 }, { X: 0, Y: 0 }],
      [{ X: 0, Y: 1 }, { X: 0, Y: 0 }],
    ),
    [{ Y: 0, X: 0 }],
  );
  assertEquals(
    mergeVariants(
      [{ X: 1 }, { Y: 0 }],
      [{ Y: 0 }, { Z: 0 }],
    ),
    [{ X: 1, Y: 0 }, { X: 1, Z: 0 }, { Y: 0 }, { Y: 0, Z: 0 }],
  );
  assertEquals(
    mergeVariants([], [{ X: 1 }]),
    [{ X: 1 }],
  );
  assertThrows(
    () => mergeVariants([{ X: 0, Y: 0 }], [{ X: 1, Y: 0 }]),
  );
});

const getResult = <T>(fn: () => T) => {
  try {
    return fn();
  } catch (_) {
    return null;
  }
};

const NAND_TRUTH_TABLE: Bit[][] = [
  [1, 1],
  [1, 0],
];
export const nand = (x: Expression, y: Expression): Expression => {
  return {
    type: "func",
    can_be(value, variables = []) {
      if (
        x.type === "bit" && y.type === "bit" &&
        NAND_TRUTH_TABLE[x.evaluate()][y.evaluate()] === value
      ) {
        return [];
      }
      if (value === 0) {
        return mergeVariants(x.can_be(1, variables), y.can_be(1, variables));
      }

      const x_zero_variants = getResult(() => x.can_be(0, variables));
      const y_zero_variants = getResult(() => y.can_be(0, variables));

      if (!x_zero_variants && !y_zero_variants) {
        throw new Error("Can't be one");
      }
      if (x_zero_variants?.length === 0 && y_zero_variants?.length === 0) {
        return [];
      }

      let result: Record<string, Bit>[] = [];

      const x_one_variants = getResult(() => x.can_be(1, variables));
      const y_one_variants = getResult(() => y.can_be(1, variables));

      if (x_zero_variants && y_zero_variants) {
        const merged = getResult(() =>
          mergeVariants(
            x_zero_variants,
            y_zero_variants,
          )
        );
        if (merged) {
          result = [...merged];
        }
      }

      if (y_zero_variants && x_one_variants) {
        const merged = getResult(() =>
          mergeVariants(y_zero_variants, x_one_variants)
        );
        if (merged) {
          result = [...result, ...merged];
        }
      }

      if (x_zero_variants && y_one_variants) {
        const merged = getResult(() =>
          mergeVariants(x_zero_variants, y_one_variants)
        );
        if (merged) {
          result = [...result, ...merged];
        }
      }

      if (result.length === 0) {
        throw new Error("Can't be one");
      }

      return result;
    },
    evaluate(variables) {
      return NAND_TRUTH_TABLE[x.evaluate(variables)][y.evaluate(variables)];
    },
  };
};

Deno.test("Nand", () => {
  assertEquals(nand(bit(0), bit(0)).can_be(1), []);
  assertEquals(nand(bit(0), bit(1)).can_be(1), []);
  assertEquals(nand(bit(1), bit(0)).can_be(1), []);
  assertEquals(nand(bit(1), bit(1)).can_be(0), []);

  assertEquals(
    nand(bit(0), v("X")).can_be(1),
    [{ X: 0 }, { X: 1 }],
  );
  assertEquals(
    nand(bit(1), v("X")).can_be(0),
    [{ X: 1 }],
  );
  assertEquals(
    nand(v("X"), v("Y")).can_be(1),
    [{ X: 0, Y: 0 }, { X: 1, Y: 0 }, { X: 0, Y: 1 }],
  );

  assertEquals(
    nand(v("X"), v("X")).can_be(1),
    [{ X: 0 }],
  );
});

export const not = (x: Expression): Expression => ({
  type: "func",
  can_be(value, variables) {
    return nand(x, x).can_be(value, variables);
  },
  evaluate(variables) {
    return nand(x, x).evaluate(variables);
  },
});

Deno.test("Not", () => {
  assertThrows(() => not(bit(0)).can_be(0));
  assertEquals(
    not(v("X")).can_be(1),
    [{ X: 0 }],
  );
});

export const and = (x: Expression, y: Expression): Expression => ({
  type: "func",
  can_be: (value, variables) => {
    return not(nand(x, y)).can_be(value, variables);
  },
  evaluate(variables) {
    return not(nand(x, y)).evaluate(variables);
  },
});

Deno.test("And", () => {
  assertEquals(
    and(bit(1), bit(1)).can_be(1),
    [],
  );
  assertEquals(
    and(v("X"), v("X")).can_be(1),
    [{ X: 1 }],
  );
  assertEquals(
    and(v("X"), v("Y")).can_be(1),
    [{ X: 1, Y: 1 }],
  );
  assertThrows(
    () => and(v("X"), not(v("X"))).can_be(1),
  );
});

export const or = (x: Expression, y: Expression): Expression => ({
  type: "func",
  can_be(value, variables) {
    return nand(not(x), not(y)).can_be(value, variables);
  },
  evaluate(variables) {
    return nand(not(x), not(y)).evaluate(variables);
  },
});

Deno.test("Or", () => {
  assertEquals(
    or(v("X"), v("Y")).can_be(1),
    [{ X: 1, Y: 1 }, { X: 0, Y: 1 }, { X: 1, Y: 0 }],
  );
});

export const nor = (x: Expression, y: Expression): Expression => ({
  type: "func",
  can_be(value, variables) {
    return not(or(x, y)).can_be(value, variables);
  },
  evaluate(variables) {
    return not(or(x, y)).evaluate(variables);
  },
});

export const xor = (x: Expression, y: Expression): Expression => ({
  type: "func",
  can_be(value, variables) {
    const z = nand(x, y);
    return nand(nand(x, z), nand(z, y)).can_be(value, variables);
  },
  evaluate(variables) {
    const z = nand(x, y);
    return nand(nand(x, z), nand(z, y)).evaluate(variables);
  },
});

export const imp = (x: Expression, y: Expression): Expression => ({
  type: "func",
  can_be(value, variables) {
    return nand(x, not(y)).can_be(value, variables);
  },
  evaluate(variables) {
    return nand(x, not(y)).evaluate(variables);
  },
});
