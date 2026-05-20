# 3. ISO 20022-Aligned Transfer Request Model: IBAN-First Account Identity

Date: 2026-05-20

## Status

Accepted

## Context

The initial TransferRequest DTO identified accounts by internal UUIDs (debtorAccountId, creditorAccountId). As the integration module needs to route external transfers through NBP infrastructure (SORBNET2/ELIXIR), which is ISO 20022 native, a decision was needed on how to model transfer requests and account identity.

A polymorphic DTO approach (single DTO with conditional fields based on transferType) and a split DTO approach (separate InternalTransferRequest / ExternalTransferRequest) were considered. Both were rejected in favour of aligning with industry standard.

## Decision

TransferRequest identifies both parties by IBAN (debtorIban, creditorIban). Internal account IDs are never exposed at the API boundary.

The service layer determines transfer type by resolving the creditor IBAN against the ledger — if it resolves to an internal account the transfer is INTERNAL; if not, it is EXTERNAL. The client does not declare transfer type.

Account entity stores an IBAN. AccountRepository exposes a findByIban query.

The ledger module owns IBAN generation at account creation time using IBAN4J (org.iban4j:iban4j). A fixed fictional Polish bank code is used to produce structurally valid PL IBANs that pass checksum validation but do not correspond to a real institution.

TransactionInitiatedEvent carries creditorIban as a non-nullable field. The event is only ever published for EXTERNAL transfers.

## Consequences

- TransferRequest DTO must be updated: creditorAccountId and debtorAccountId removed, debtorIban and creditorIban added, both non-nullable with IBAN format validation.
- Account entity (#9) must include a non-nullable, unique iban field.
- Account creation (#30) must invoke IBAN4J to generate and persist an IBAN at creation time.
- AccountRepository needs findByIban(String iban).
- Transfer routing logic in TransferService (#10) resolves creditor IBAN to determine INTERNAL vs EXTERNAL — no client-supplied discriminator.
- IBAN4J must be added to build.gradle.kts.
- All existing stub controller code referencing account IDs in transfer context must be updated.
