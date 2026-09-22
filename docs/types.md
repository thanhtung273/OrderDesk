\# Type Selections for LineTotals



\- \*\*sku (`String`)\*\*: Chosen `String` because SKUs are alphanumeric identifiers (e.g., `KB-01`) without arithmetic operations. Rejected numeric types (`int`, `long`) because SKUs contain hyphens and characters.

\- \*\*quantity (`long`)\*\*: Chosen `long` because quantities are discrete, positive whole counts of items. Rejected `double` and `float` because item counts cannot be fractional.

\- \*\*unit\_price (`BigDecimal`)\*\*: Chosen `BigDecimal` constructed from `String` to ensure exact decimal precision for financial amounts. Rejected `double` and `float` due to IEEE 754 floating-point rounding errors (e.g., `0.1 + 0.2 != 0.3`).

\- \*\*lines (`long`)\*\*: Chosen `long` to represent non-negative count of processed rows. Rejected `int` to avoid overflow on large files.

\- \*\*units (`long`)\*\*: Chosen `long` to represent the accumulated sum of item quantities. Rejected floating-point types to maintain discrete counts.

\- \*\*total (`BigDecimal`)\*\*: Chosen `BigDecimal` to ensure the grand monetary sum remains exact without rounding drift.

