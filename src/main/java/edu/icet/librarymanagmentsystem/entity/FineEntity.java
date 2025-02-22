package edu.icet.librarymanagmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString


public class FineEntity {

    private int fineId;
    private String borrowId;
    private BigDecimal fineAmount;
    private LocalDate paymentDate;

}
