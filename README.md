<div align="center">

# 📒 KorusLedger

**Sistema de finanzas personales orientado a eventos**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.3-6DB33F?style=flat&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=flat&logo=postgresql&logoColor=white)](https://postgresql.org)
[![Docker](https://img.shields.io/badge/Docker-ready-2496ED?style=flat&logo=docker&logoColor=white)](https://docker.com)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red?style=flat)](LICENSE)

</div>

---

## ¿Qué es KorusLedger?

KorusLedger es un sistema de gestión financiera personal construido con arquitectura limpia y orientado a eventos. Permite registrar usuarios, billeteras y transacciones financieras, calculando balances en tiempo real. El sistema está diseñado como base de un ecosistema modular que incorporará OCR de tickets, mensajería asíncrona y análisis inteligente de gastos.

---

## Arquitectura

El proyecto sigue **Clean Architecture** con separación estricta de capas. Las dependencias siempre apuntan hacia adentro — el dominio no conoce nada de la infraestructura.

```
api  →  application  →  domain
              ↑
       infrastructure
```

| Capa | Paquete | Responsabilidad |
|---|---|---|
| **API** | `com.korus.core.api` | Controllers REST, DTOs, manejo de excepciones |
| **Application** | `com.korus.core.application` | Casos de uso, interfaces de repositorios (puertos) |
| **Domain** | `com.korus.core.domain` | Entidades puras, enums, reglas de negocio |
| **Infrastructure** | `com.korus.core.infrastructure` | Implementaciones Postgres con Exposed (adaptadores) |

---

## Stack tecnológico

| Componente | Tecnología |
|---|---|
| Lenguaje | Kotlin 2.2 + JVM 21 |
| Framework | Spring Boot 3.4.3 |
| ORM | Jetbrains Exposed 0.45 |
| Base de datos | PostgreSQL 16 |
| Seguridad | Spring Security + BCrypt |
| Validación | Spring Validation (Bean Validation) |
| Build | Gradle 9.4 (Kotlin DSL) |
| Contenedores | Docker + Docker Compose |
| Tests | JUnit 5 + repositorios fake in-memory |

---

## Estructura del proyecto

```
korus-ledger/
├── core-orchestrator/
│   ├── src/
│   │   ├── main/kotlin/com/korus/core/
│   │   │   ├── api/                    # Controllers, DTOs, SecurityConfig
│   │   │   │   ├── TransactionController.kt
│   │   │   │   ├── UserController.kt
│   │   │   │   ├── WalletController.kt
│   │   │   │   ├── GlobalExceptionHandler.kt
│   │   │   │   └── SecurityConfig.kt
│   │   │   ├── application/            # Casos de uso e interfaces
│   │   │   │   ├── CreateUserUseCase.kt
│   │   │   │   ├── RecordTransactionUseCase.kt
│   │   │   │   ├── RecordWalletUseCase.kt
│   │   │   │   ├── GetTransactionsUseCase.kt
│   │   │   │   ├── GetBalanceUseCase.kt
│   │   │   │   ├── TransactionRepository.kt
│   │   │   │   ├── UserRepository.kt
│   │   │   │   └── WalletRepository.kt
│   │   │   ├── domain/                 # Entidades y enums
│   │   │   │   ├── models.kt           # Transaction, Wallet, User
│   │   │   │   ├── TransactionType.kt  # INCOME, EXPENSE
│   │   │   │   ├── TransactionCategory.kt
│   │   │   │   └── WalletType.kt       # BANK, CASH, DIGITAL, CRYPTO
│   │   │   └── infrastructure/         # Repos Postgres + tablas Exposed
│   │   │       ├── PostgresTransactionRepository.kt
│   │   │       ├── PostgresUserRepository.kt
│   │   │       ├── PostgresWalletRepository.kt
│   │   │       ├── TransactionTable.kt
│   │   │       ├── UserTable.kt
│   │   │       └── WalletTable.kt
│   │   └── test/
│   │       └── application/
│   │           └── RecordTransactionUseCaseTest.kt
│   ├── build.gradle.kts
│   └── src/main/resources/application.yml
├── docs/
│   ├── er-diagram.mermaid
│   ├── architecture-diagram.mermaid
│   ├── class-diagram.mermaid
│   ├── sequence-diagram.mermaid
│   └── package-diagram.puml
├── docker-compose.yml
└── .gitignore
```

---

## Primeros pasos

### Prerrequisitos

- [JDK 21+](https://openjdk.org)
- [Docker](https://docs.docker.com/get-docker/) y Docker Compose

### Instalación

```bash
# 1. Clonar el repositorio
git clone https://github.com/tu-usuario/korus-ledger.git
cd korus-ledger

# 2. Levantar la base de datos
docker compose up -d

# 3. Correr el servidor
cd core-orchestrator
./gradlew bootRun
```

El servidor queda disponible en `http://localhost:8080`.

> La base de datos se crea automáticamente al iniciar gracias a `exposed.generate-ddl: true`.

---

## Configuración

La configuración se encuentra en `core-orchestrator/src/main/resources/application.yml`:

| Propiedad | Valor por defecto | Descripción |
|---|---|---|
| `server.port` | `8080` | Puerto del servidor HTTP |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/korus_db` | Conexión a PostgreSQL |
| `spring.datasource.username` | `postgres` | Usuario de la base de datos |
| `spring.datasource.password` | `admin` | Contraseña de la base de datos |
| `spring.exposed.generate-ddl` | `true` | Crea las tablas automáticamente al iniciar |

---

## API — Endpoints

Todos los endpoints autenticados requieren el header `X-User-Id: <uuid>`.

### Usuarios

```
POST   /api/users/register     Registrar nuevo usuario
```

```json
// Body
{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phoneNumber": "+5491112345678",
  "password": "minimo8chars"
}
```

### Billeteras

```
POST   /api/wallets             Crear billetera
```

```json
// Body
{
  "name": "Cuenta Bancaria",
  "balance": 50000.00,
  "type": "BANK"
}
// Tipos disponibles: BANK | CASH | DIGITAL | CRYPTO
```

### Transacciones

```
POST   /api/transactions        Registrar transacción
GET    /api/transactions        Listar transacciones (con filtros opcionales)
GET    /api/transactions/balance  Obtener balance total
```

```json
// Body POST
{
  "title": "Supermercado",
  "amount": 12500.00,
  "type": "EXPENSE",
  "category": "FOOD",
  "walletId": "uuid-de-la-billetera"
}
// Tipos: INCOME | EXPENSE
// Categorías: FOOD | TRANSPORT | HOUSING | ENTERTAINMENT | SALARY | OTHERS
```

**Filtros disponibles en GET /transactions:**

| Parámetro | Tipo | Descripción |
|---|---|---|
| `type` | `INCOME` / `EXPENSE` | Filtrar por tipo |
| `category` | enum | Filtrar por categoría |
| `startDate` | ISO 8601 | Fecha de inicio |
| `endDate` | ISO 8601 | Fecha de fin |

---

## Modelo de datos

```
USERS ──< WALLETS ──< TRANSACTIONS
  └──────────────────────────────<┘
```

El diagrama ER completo está en `docs/er-diagram.mermaid`.

---

## Tests

Los tests de casos de uso usan repositorios fake en memoria, sin necesidad de base de datos real:

```bash
cd core-orchestrator
./gradlew test
```

La estrategia de testing sigue el patrón de **puertos y adaptadores**: los casos de uso se testean con implementaciones `Fake` de las interfaces de repositorio, sin dependencias externas.

---

## Roadmap

| Estado | Feature |
|---|---|
| ✅ | CRUD de usuarios, billeteras y transacciones |
| ✅ | Balance calculado en tiempo real |
| ✅ | Filtros por tipo, categoría y fecha |
| ✅ | Encriptación de contraseñas con BCrypt |
| 🔜 | Autenticación JWT |
| 🔜 | API Gateway |
| 🔜 | OCR Worker en Rust |
| 🔜 | Mensajería con RabbitMQ |
| 🔜 | App mobile en React Native |
| 🔜 | Frontend web en Next.js |

La arquitectura del sistema completo (microservicios, OCR, mensajería) está documentada en `docs/architecture-diagram.mermaid`.

---

## Documentación técnica

| Diagrama | Archivo |
|---|---|
| Arquitectura general | `docs/architecture-diagram.mermaid` |
| Modelo de datos (ER) | `docs/er-diagram.mermaid` |
| Diagrama de clases | `docs/class-diagram.mermaid` |
| Diagrama de secuencia | `docs/sequence-diagram.mermaid` |
| Diagrama de paquetes | `docs/package-diagram.puml` |

---

## Licencia

© 2026 KorusLedger. Todos los derechos reservados.

Este software es propietario. Queda estrictamente prohibida su copia, modificación, distribución o uso, total o parcial, sin autorización escrita de los autores.

<div align="center">

© 2026 KorusLedger — Todos los derechos reservados

</div>