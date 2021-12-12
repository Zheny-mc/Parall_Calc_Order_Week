package com.applicaton.Book.Model.akka.Model;

import lombok.*;
import static java.lang.Math.pow;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Integral {
    private double numA;
    private double numB;
    private int countN;

    public static Integral create(double numA, double numB, int countN) {
        return new Integral(numA, numB, countN);
    }

    public double func(double x) {
        return 2*x - 3*pow(x, 2) + 4*pow(x, 3) + 7*pow(x, 5);
    }
}
