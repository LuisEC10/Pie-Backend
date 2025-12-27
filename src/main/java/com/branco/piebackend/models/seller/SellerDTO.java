package com.branco.piebackend.models.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private Long id;
    private String brand;
    private Long ownerId;
    private List<Long> universities;
}
