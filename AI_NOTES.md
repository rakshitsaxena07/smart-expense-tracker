# AI Usage Notes

## AI Tool Used

- ChatGPT (GPT-5.5)
- Claude (Sonnet-5)

---

## How I Used AI

I used AI as a learning and review tool during development. It helped me understand Spring Boot best practices, review my code, and discuss different implementation approaches before I made the final decisions.

---

## AI-Assisted Areas

AI helped me with:

- Designing the REST API endpoints.
- Creating DTOs and keeping entities separate from API requests.
- Understanding why validation should be placed in request DTOs instead of the model.
- Choosing `BigDecimal` for handling monetary values.
- Understanding why constructor injection is preferred over field injection.
- Implementing exception handling using `ResourceNotFoundException` and `GlobalExceptionHandler`.
- Reviewing service and controller implementations.
- Writing clean Git commit messages following Conventional Commits.

---

## What I Implemented and Verified

I implemented the project myself and reviewed every AI suggestion before using it.

I verified each feature by:

- Compiling the project.
- Running unit tests.
- Testing the APIs using Postman and cURL.
- Modifying AI-generated suggestions whenever they did not match my project structure or coding style.
- Verified generated OpenAPI documentation using Swagger UI in the browser.

---

## AI Suggestions I Chose Not to Use

Some AI suggestions were intentionally not used:

- Maintaining a cached running total for expenses.
  I chose to calculate totals when requested because the project uses an in-memory `HashMap`, and this approach keeps the implementation simple.

- Combining all total calculation endpoints into a single controller method.
  I kept separate controller methods for consistency with the rest of my API design.

- AI also suggested that in a production application with a database, the total should be calculated using database queries such as `SUM(amount)` instead of loading all expenses into memory. Since this assignment uses an in-memory repository, I kept the calculation in the service layer.

---

## What I Learned

Using AI helped me understand the reasoning behind design decisions instead of only generating code. It improved my understanding of DTOs, validation, exception handling, dependency injection, and clean REST API design.