# Sequence Generator

A lightweight Spring Boot service that generates unique, sequential numbers for different entity types across your application.

---

## What This Project Does

This service provides a simple REST API to generate unique numbers for named sequences like `ORDER`, `USER`, or `INVOICE`. Any part of your application can call it and get back the next number ? guaranteed to be unique even when multiple services or threads are calling at the same time.

It stores each sequence in a PostgreSQL table and uses a database-level lock on every increment, so no two callers ever receive the same value.

---

## Goal

The goal is to have a single, reliable place in your system responsible for generating unique IDs ? instead of every service managing its own counters and risking collisions. You register a sequence by name, and from that point any service in your application can ask for the next value and trust that no other service will ever get the same number back.
