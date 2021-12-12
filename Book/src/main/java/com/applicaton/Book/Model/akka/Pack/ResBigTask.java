package com.applicaton.Book.Model.akka.Pack;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ResBigTask implements IMesssage {
    private double sum;
}
