package com.thlogistic.job.adapters.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {
    @Size(min = 1, message = "Product ID list cannot be empty")
    List<ProductCreateJobRequest> productList;
    @NotEmpty(message = "Invalid route ID")
    String routeId;
    @NotNull(message = "Invalid delivery time")
    @Min(value = 0, message = "Invalid delivery time")
    Long deliveryTime;
    @NotEmpty(message = "Invalid pick up contact name")
    String pickUpContactName;
    @NotEmpty(message = "Invalid pick up contact no")
    String pickUpContactNo;
    @NotEmpty(message = "Invalid unload contact name")
    String unloadContactName;
    @NotEmpty(message = "Invalid unload contact no")
    String unloadContactNo;
    String notesToDriver;
}
