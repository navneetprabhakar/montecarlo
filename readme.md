# Monte Carlo Simulation Service

A Spring Boot service that demonstrates Monte Carlo simulation through a casino betting scenario.

The service models a sequence of independent bets to analyze likely outcomes under randomness — the final balance and whether (and when) the player goes bankrupt. It is a compact, self-contained example of simulation-based risk analysis.

## Features

- REST API to run a casino-betting Monte Carlo simulation
- Configurable starting balance, stake per round, win probability, and number of rounds
- Reports the final balance and the round at which the player went bankrupt (if any)
- Input validation with descriptive failure messages
- Generic `ServiceResponse<T>` wrapper for consistent success/failure payloads

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.2** (`spring-boot-starter-webmvc`)
- **Lombok**
- **Maven** (wrapper included)

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+ (or use the bundled `./mvnw` wrapper)

### Build

```bash
./mvnw clean package
```

### Run

```bash
./mvnw spring-boot:run
```

Or run the packaged JAR:

```bash
java -jar target/montecarlo-0.0.1-SNAPSHOT.jar
```

The service starts on port **8080** with a context path of `/simulation`.

## Configuration

Settings live in `src/main/resources/application.properties`:

| Key | Description | Value |
|-----|-------------|-------|
| `server.port` | HTTP port | `8080` |
| `server.servlet.context-path` | Base path for all endpoints | `/simulation` |

## API

### Simulate a casino game

`POST /simulation/casino-game`

**Request body (`CasinoRequest`)**

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `amount` | Double | `> 0` | Starting balance |
| `betAmount` | Double | `> 0` and `<= amount` | Stake placed each round |
| `winRatio` | Double | `0.0`–`1.0` | Probability of winning a single bet |
| `gameSize` | Integer | `>= 0` | Number of rounds to simulate |

**Response (`ServiceResponse<CasinoResponse>`)**

| Field | Type | Description |
|-------|------|-------------|
| `status` | String | `"success"` or `"failure"` |
| `message` | String | Human-readable status or error message |
| `data.endAmount` | Double | Final balance after all rounds |
| `data.bankruptRound` | Integer \| null | 0-based round index where the balance hit zero; `null` if never bankrupt |

**Example**

```bash
curl -X POST http://localhost:8080/simulation/casino-game \
  -H "Content-Type: application/json" \
  -d '{"amount":1000.0,"betAmount":10.0,"winRatio":0.5,"gameSize":10000}'
```

```json
{
  "status": "success",
  "message": "Simulation Complete",
  "data": { "endAmount": 842.3, "bankruptRound": null }
}
```

## Project Structure

```
src/main/java/com/navneet/
├── MonteCarloApplication.java                  # Spring Boot entry point
├── controller/SimulationController.java        # POST /casino-game
├── models/                                     # Request/response DTOs
└── service/
    ├── SimulationService.java                  # Service interface
    ├── impl/SimulationServiceImpl.java         # Simulation loop
    └── helper/SimulationServiceHelper.java     # Validation + per-round bet logic
```

## License

Licensed under the GNU General Public License v3.0 — see [LICENSE](LICENSE).
