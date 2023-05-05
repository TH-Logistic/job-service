package com.thlogistic.job.adapters.dtos;

import com.thlogistic.job.aop.custom_validator.NoEmptyFields;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {
    @Size(min = 1, message = "Product ID list cannot be empty")
    List<ProductCreateJobDto> productList;
    @NotEmpty(message = "Invalid route ID")
    String routeId;
    @NotEmpty(message = "Invalid delivery time")
    String deliveryTime;
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
