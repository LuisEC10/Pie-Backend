package com.branco.piebackend.models.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRegisterDTO {
    @NotNull(message = "Latitude is required")
    private Double shipLatitude;

    @NotNull(message = "Longitude is required")
    private Double shipLongitude;
}
