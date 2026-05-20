# 2. TransactionInitiatedEvent is EXTERNAL-only

Date: 2026-05-20

## Status

Accepted

## Context

The ledger module needs to notify the integration module when a transfer requires central bank routing. Two transfer types exist: INTERNAL (same-bank, settles immediately) and EXTERNAL (requires RTGS/ELIXIR routing via NBP).

## Decision

TransactionInitiatedEvent is published exclusively for EXTERNAL transfers. INTERNAL transfers settle synchronously within TransferService and never enter the event flow. destinationIban is non-nullable — its presence is implicit proof the transfer is external.

## Consequences

- Integration module has no conditional branching on transfer type at the entry point.
- event_publication table will never contain stuck/unprocessed INTERNAL events
- TransferType is still needed on the Transaction entity and in the REST layer — just not on this event