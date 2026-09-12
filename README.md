# Lab - Statuses of Your Product in Java 21

## Product

We build a **payment processing service**. Customers pay through
different banks, and the company earns money from a commission on
every transaction.

## Core item

The tracked item is an `Order`: one id, one status. It starts as a
draft, becomes paid once the bank confirms the payment (and our
commission is deducted), and is shipped once the money is forwarded to
the recipient.

## Status table

`OrderStatus` has three values: `DRAFT`, `PAID`, `SHIPPED`.

| From  | To      | Change      | Business reason (if forbidden)                                                                 |
|-------|---------|-------------|--------------------------------------------------------------------------------------------------|
| DRAFT | PAID    | Allowed  | The bank confirmed the payment — normal next step.                                              |
| PAID  | SHIPPED | Allowed  | The money was forwarded to the recipient — normal next step.                                    |
| DRAFT | SHIPPED | Forbidden | **Skipped payment.** We can't forward money to the recipient before the bank confirms it was paid — that would mean paying out with no funds and no commission collected. |
| PAID  | DRAFT   | Forbidden | **Reopen after pay.** Once a payment is confirmed and the commission is deducted, reverting it back to draft would break the financial records and accounting. |

## Forbidden - why

Both forbidden rows protect the same rule: **an order can only move
forward once the real-world financial commitment behind it (bank
confirmation, money transfer) has actually happened.**
`OrderPolicy.move(from, to)` throws `IllegalStateException` for
forbidden changes instead of returning a boolean, so the caller can't
silently ignore a rejected transition.

## Running the tests

```
mvn -B test
```
