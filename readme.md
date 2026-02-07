# Monte Carlo Simulation Service (Java / Spring Boot)

A small Spring Boot service that demonstrates Monte Carlo simulation techniques using a casino betting scenario as an example. The project models repeated bets to analyze likely outcomes, risk of ruin, and expected final balance under randomness.

## Table of Contents

- [Overview](#overview)
- [Business Context](#business-context)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Use Case: Casino Game](#use-case-casino-game)
- [API Reference](#api-reference)
- [Getting Started](#getting-started)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Overview

Monte Carlo simulations model the probability of different outcomes in processes affected by random variables. This service provides a reusable simulation for simple betting games and is intended as a learning/demo project for simulation-based risk analysis.

## Business Context

Monte Carlo simulations are used to:
- **Estimate distributions of outcomes** when analytical solutions are hard to obtain
- **Analyze financial risk** through repeated sampling and randomness modeling
- **Support decision-making** where uncertainty plays a strong role

Example use cases:
- Financial risk analysis and portfolio simulation
- Gambling and game-theory experiments
- A/B testing with uncertainty quantification
- Decision support systems under randomness
- Risk of ruin analysis for betting and investment strategies

## Technologies

- **Java** 17+
- **Spring Boot** - Web framework
- **Maven** - Build tool
- **Lombok** - Reduce boilerplate code

## Project Structure

```
montecarlo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/navneet/
│   │   │       ├── MonteCarloApplication.java         # Spring Boot entry point
│   │   │       ├── controller/
│   │   │       │   └── SimulationController.java       # REST controller exposing /casino-game endpoint
│   │   │       ├── models/
│   │   │       │   ├── CasinoRequest.java              # Request DTO for casino simulation
│   │   │       │   ├── CasinoResponse.java             # Response DTO with simulation results
│   │   │       │   ├── ServiceRequest.java             # Base service request interface
│   │   │       │   └── ServiceResponse.java            # Generic service response wrapper
│   │   │       └── service/
│   │   │           ├── SimulationService.java          # Service interface
│   │   │           ├── impl/
│   │   │           │   └── SimulationServiceImpl.java   # Core simulation loop implementation
│   │   │           └── helper/
│   │   │               └── SimulationServiceHelper.java # Validation & per-round game logic
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/                                 # Static assets
│   │       └── templates/                              # Thymeleaf templates (if any)
│   └── test/
│       └── java/
│           └── com/navneet/
│               └── MonteCarloApplicationTests.java     # Unit tests
├── target/                                             # Maven build output (compiled classes)
├── pom.xml                                             # Maven project configuration
├── mvnw & mvnw.cmd                                     # Maven wrapper scripts
├── README.md                                           # This file
├── LICENSE                                             # GNU General Public License v3.0
└── HELP.md                                             # Additional help documentation
```

### Key Components

| Component | Location | Purpose |
|-----------|----------|---------|
| **REST Controller** | `controller/SimulationController.java` | Exposes POST `/casino-game` endpoint |
| **Service Implementation** | `service/impl/SimulationServiceImpl.java` | Runs the core simulation loop for N rounds |
| **Service Helper** | `service/helper/SimulationServiceHelper.java` | Input validation and per-round bet logic |
| **Request/Response Models** | `models/` | Type-safe DTOs for API communication |

## Use Case: Casino Game

**Purpose:** Run a sequence of independent bets and observe the player's end balance and whether/when they go bankrupt.

**Conceptual Link:** [The House Always Wins: Monte Carlo Simulation](https://towardsdatascience.com/the-house-always-wins-monte-carlo-simulation-eb82787da2a3)

## API Reference

### Endpoint: `/casino-game`

| Attribute | Value |
|-----------|-------|
| **HTTP Method** | POST |
| **Path** | `/casino-game` |
| **Content-Type** | `application/json` |
| **Description** | Runs a Monte Carlo simulation for the casino betting scenario |

### Request Payload (CasinoRequest)

| Field | Type | Constraints | Description |
|-------|------|-----------|-------------|
| `amount` | Double | > 0 | Starting balance for the simulation |
| `betAmount` | Double | > 0 and <= `amount` | Stake placed per round |
| `winRatio` | Double | 0.0 - 1.0 (inclusive) | Probability of winning a single bet |
| `gameSize` | Integer | >= 0 | Number of rounds to simulate |

**Example Request:**

```json
{
  "amount": 1000.0,
  "betAmount": 10.0,
  "winRatio": 0.50,
  "gameSize": 10000
}
```

### Response Payload (ServiceResponse<CasinoResponse>)

| Field | Type | Description |
|-------|------|-------------|
| `status` | String | `"success"` or `"failure"` |
| `message` | String | Human-readable status or error message |
| `data` | CasinoResponse \| null | On success, contains simulation results; on failure, null |

**CasinoResponse fields:**

| Field | Type | Description |
|-------|------|-------------|
| `endAmount` | Double | Final balance after all rounds (can be greater, equal, or less than starting amount) |
| `bankruptRound` | Integer \| null | 0-based round index when bankrupt; `null` if never bankrupt |

**Example Success Response:**

```json
{
  "status": "success",
  "message": "Simulation Complete",
  "data": {
    "endAmount": 842.3,
    "bankruptRound": null
  }
}
```

**Example Failure Response (Validation Error):**

```json
{
  "status": "failure",
  "message": "Amount must be greater than Bet Amount",
  "data": null
}
```

### Validation Rules

The following validation rules are enforced by `SimulationServiceHelper`:

- `amount` must be provided and > 0
- `betAmount` must be provided and > 0
- `amount` must be >= `betAmount`
- `winRatio` must be provided and between 0.0 and 1.0 (inclusive)
- `gameSize` must be provided and >= 0

If validation fails, the endpoint returns a `ServiceResponse` with `status: "failure"` and a descriptive error message.

## Getting Started

### Prerequisites

- **JDK 17** or higher
- **Maven** 3.6+ (or use the provided `mvnw` wrapper)

### Build

Clone the repository and build the project:

```bash
./mvnw clean package
```

Or with Maven directly:

```bash
mvn clean package
```

### Run

Execute the compiled Spring Boot application:

```bash
java -jar target/*.jar
```

Or run directly from your IDE:
- Execute the `com.navneet.MonteCarloApplication` class

The service will start on `http://localhost:8080` by default.

### Quick Test with curl

```bash
curl -s -X POST http://localhost:8080/casino-game \
  -H "Content-Type: application/json" \
  -d '{"amount":1000.0,"betAmount":10.0,"winRatio":0.5,"gameSize":10000}'
```

## Testing

Run the unit test suite:

```bash
./mvnw test
```

Or with Maven directly:

```bash
mvn test
```

## Contributing

- **Report Bugs**: Open an issue describing the bug with reproduction steps
- **Feature Requests**: Open an issue describing the desired feature
- **Pull Requests**: 
  - Fork the repository
  - Create a feature branch (`git checkout -b feature/your-feature`)
  - Add tests for new functionality
  - Commit your changes (`git commit -am 'Add new feature'`)
  - Push to the branch (`git push origin feature/your-feature`)
  - Open a pull request for review

## License

This project is licensed under the **GNU General Public License v3.0**. See the [`LICENSE`](./LICENSE) file for the full text.
