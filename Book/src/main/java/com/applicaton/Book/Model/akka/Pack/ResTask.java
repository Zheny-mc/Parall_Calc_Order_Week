package com.applicaton.Book.Model.akka.Pack;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public final class ResTask implements IMesssage {
    private int workId;
    private double sum;
}