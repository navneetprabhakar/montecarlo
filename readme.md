# Monte Carlo Simulation using JAVA
This repository is implementation of Monte Carlo Simulations on various scenarios / use cases.

# Introduction:
Monte Carlo simulations are used to model the probability of different outcomes in a process that cannot easily be predicted due to the intervention of random variables. It is a technique used to understand the impact of risk and uncertainty in prediction and forecasting models.

Monte Carlo simulation can be used to tackle a range of problems in virtually every field such as finance, engineering, supply chain, and science.

Monte Carlo simulation is also referred to as multiple probability simulation.

# Use cases
1. Casino Game: The house always wins. 
- Link: https://towardsdatascience.com/the-house-always-wins-monte-carlo-simulation-eb82787da2a3
- METHOD: POST
- URL: http://localhost:8080/Simulation/casinoGame

```json
{
  "amount": 1000,
  "betAmount": 10,
  "gameSize": 10000,
  "winRatio": 0.50
}
```
