# Github API Integration Project

## Introduction
This project is a simple Spring Boot application (Java 21 + Spring Boot 3.5) that integrates with the GitHub REST API (v3).
Its purpose is to fetch all public repositories for a given GitHub user (excluding forks) and return, for each repository, its name, owner login and a list of branches with the last commit SHA.

## Technologies
- Java 21
- Spring Boot 3.5
- Spring Web (MVC)
- RestTemplate (HTTP client)
- JUnit 5 / Spring Boot Test (single integration test)

## Features
- Fetch public repositories of a GitHub user using `GET /users/{username}/repos` from GitHub API.
- Exclude forked repositories.
- For each repository, fetch branches using `GET /repos/{owner}/{repo}/branches`.
- Return JSON with repository name, `ownerLogin` and `branches` (branch name + last commit SHA).
- Single integration test covering the happy-path (real HTTP calls; minimal mocking).


## Running the project (local)
1. Clone repository:
```bash
git clone <your-repo-url>
cd githubapi
```
2. Build:
```bash
./mvnw clean install
# on Windows (PowerShell/CMD):
mvnw.cmd clean install
```
3. Run:
```bash
./mvnw spring-boot:run
# or
mvnw.cmd spring-boot:run
```
4. The API will be available at `http://localhost:8080` by default; if logs show a random port, use that port. Example endpoint:
```
http://localhost:8080/api/github/octocat/repos
```

## Tests
- There is **one integration test** (in `src/test/java/...`) which starts the Spring context and exercises the endpoint (happy path).
- Run tests:
```bash
./mvnw test
# or on Windows:
mvnw.cmd test
```
- Expect `BUILD SUCCESS` and `Tests run: 1, Failures: 0` for the provided single test.

## Limitations / Notes
- No pagination support (both when consuming GitHub API and in output).
- Only public repositories are supported (no authentication / OAuth token handling).
- No WebFlux; no DDD/Hexagonal structure — minimal implementation as requested.
- `octocat` used as an example username for testing.

## Sources
- GitHub REST API v3 documentation: https://developer.github.com/v3
- Spring Boot documentation: https://spring.io/projects/spring-boot


---
