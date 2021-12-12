package com.applicaton.Book.Model.akka.Pack;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Start implements IMesssage{
    private double numA;
    private double numB;
    private int countN;
}
