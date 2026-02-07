# Monte Carlo Simulation Service (Java / Spring Boot)

A small Spring Boot service that demonstrates Monte Carlo simulation techniques using a casino betting scenario as an example. The project models repeated bets to analyze likely outcomes, risk of ruin, and expected final balance under randomness.

## Overview

Monte Carlo simulations model the probability of different outcomes in processes affected by random variables. This service provides a reusable simulation for simple betting games and is intended as a learning/demo project for simulation-based risk analysis.

Technologies
- Java
- Spring Boot
- Maven
- Lombok

Business context
- Use Monte Carlo simulations to estimate distributions of outcomes when analytical solutions are hard to obtain.
- Example business uses: financial risk analysis, gambling/game-theory experiments, A/B testing with uncertainty, and decision support where randomness plays a strong role.

## Project layout (important files)
- `src/main/java/com/navneet/controller/SimulationController.java` - REST controller exposing the simulation endpoint.
- `src/main/java/com/navneet/service/impl/SimulationServiceImpl.java` - Service implementation that runs the simulation loop.
- `src/main/java/com/navneet/service/helper/SimulationServiceHelper.java` - Helper with validation and per-round game logic.
- `src/main/java/com/navneet/models/` - POJOs describing request & response shapes (`CasinoRequest`, `CasinoResponse`, `ServiceResponse`).

## Use case: Casino Game (example)

Purpose: Run a sequence of independent bets and observe the player's end balance and whether/when they go bankrupt.

- Link (conceptual): https://towardsdatascience.com/the-house-always-wins-monte-carlo-simulation-eb82787da2a3

API endpoint (from the code)
- Method: POST
- Path: /casino-game
- Description: Runs a Monte Carlo simulation for the casino betting scenario.

Request JSON (CasinoRequest)
- amount (Double) — starting balance (must be > 0)
- betAmount (Double) — stake per round (must be > 0 and <= amount)
- winRatio (Double) — probability of winning a single bet (0.0 - 1.0)
- gameSize (Integer) — number of rounds to simulate (must be >= 0)

Example request

```json
{
  "amount": 1000.0,
  "betAmount": 10.0,
  "winRatio": 0.50,
  "gameSize": 10000
}
```

Response JSON (ServiceResponse<CasinoResponse>)
- status (String) — "success" or "failure"
- message (String) — human-readable status/message
- data (object|null) — on success, contains the CasinoResponse:
  - endAmount (Double) — final amount after simulation (can be >, =, or < starting amount)
  - bankruptRound (Integer|null) — the 0-based round index when the player first hit or fell below zero; null if never bankrupt

Example success response

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

Example failure response (validation error)

```json
{
  "status": "failure",
  "message": "Amount must be greater than Bet Amount",
  "data": null
}
```

Validation rules (from `SimulationServiceHelper`)
- `amount` must be provided and > 0
- `betAmount` must be provided and > 0
- `amount` must be >= `betAmount`
- `winRatio` must be provided and between 0.0 and 1.0 (inclusive)
- `gameSize` must be provided and >= 0

If validation fails, the endpoint returns a `ServiceResponse` with `status: "failure"` and a helpful message.

## Build & run

Prerequisites
- JDK 17+ (or the JDK configured for this project)
- Maven (or use the provided `mvnw` wrapper)

Build

```bash
./mvnw clean package
# or
mvn clean package
```

Run

```bash
# run the Spring Boot jar produced in target/
java -jar target/*.jar
```

Or run the `com.navneet.MonteCarloApplication` from your IDE.

Quick curl example

```bash
curl -s -X POST http://localhost:8080/casino-game \
  -H "Content-Type: application/json" \
  -d '{"amount":1000.0,"betAmount":10.0,"winRatio":0.5,"gameSize":10000}'
```

## Tests

Run unit tests with

```bash
./mvnw test
# or
mvn test
```

## Contributing

- Open an issue for bugs or feature requests.
- Fork the repository, create a feature branch, add tests, and submit a pull request.

## License

This project is licensed under the GNU General Public License v3.0. See `LICENSE` for the full text.


---

If you'd like, I can also add a short sample integration test that calls the controller with a valid `CasinoRequest` and asserts a `success` response — would you like that?
