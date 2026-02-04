package com.tradeflow.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        String id,
        @NotNull(message = "Firstname is required")
        @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters")
        String firstname,

        @NotNull(message = "Lastname is required")
        @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
        String lastname,

        @NotNull(message = "Email is required")
        @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
        @Email(message = "Invalid email address")
        String email,

        @Valid
        Address address
) {
}
