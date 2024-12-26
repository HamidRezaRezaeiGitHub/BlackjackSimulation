# Blackjack Simulation App

A Java-based application for simulating betting strategies in a blackjack-like game, with customizable
configurations and dynamic reporting.

## About

This application evaluates the effectiveness of different betting strategies by running repeated simulations of a
blackjack-like game. It uses a modular design to support custom game configurations, betting strategies, and reporting
mechanisms.

## Features

- **Customizable Simulations**: Configure the number of simulation rounds, repetitions, and betting strategies.
- **Betting Strategies**: Includes `Martingale`, `Flat` betting, and configurable strategies.
- **Dynamic Reporting**: Automatically generates reports in a structured directory.
- **Flexible Configuration**: Uses `application.yaml` for dynamic configuration.

## Technologies Used

- **Java 21**: Core programming language.
- **Spring Boot**: Application framework.
- **SLF4J & Logback**: Logging framework.
- **Maven**: Build and dependency management.

## Configuration

The application is configured via the `application.yaml` file. Below is a sample configuration:

```yaml
reports:
  enabled: true
  directory: reports

strategy:
  game:
    parallel: false
  bet:
    step: 5
    enabled: martingale
    flat:
      bet: 25
    martingale:
      cap: 1000
      initialBet: 25
  balance:
    initial: 100000

simulation:
  rounds: 10000
  repetitions: 10
```

### Key Configuration Parameters

- reports.enabled: Enables or disables report generation.
- reports.directory: Directory for storing reports.
- strategy.game.parallel: Enables simulation of 2 parallel Blackjack games in each round.
- strategy.bet.enabled: Specifies the betting strategy (flat or martingale).
- strategy.bet.martingale.cap: Maximum cap for the Martingale strategy. This is the maximum consecutive losses before
  resetting the bet.
- strategy.bet.martingale.initialBet: Initial bet for the Martingale strategy.
- strategy.balance.initial: Initial balance for each simulation.
- simulation.rounds: Number of rounds per simulation.
- simulation.repetitions: Number of times the simulation is repeated.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/HamidRezaRezaeiGitHub/blackjack-simulation.git
   cd blackjack-simulation
    ```

2. **Running the Application Locally**:

The project includes a Maven wrapper, so you don’t need to install Maven manually. To run the application:

- On Unix-like Systems (Linux, macOS):

    ```bash
    ./mvnw spring-boot:run
    ```
- On Windows:

    ```bash
    mvnw.cmd spring-boot:run
    ```

## Contact

- **Author**: Hamid R. Rezaei
- **Email**: hamidreza74hrr@yahoo.com
- **GitHub**: [https://github.com/HamidRezaRezaeiGitHub](https://github.com/HamidRezaRezaeiGitHub)