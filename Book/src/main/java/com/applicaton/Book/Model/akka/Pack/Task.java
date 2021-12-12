package com.applicaton.Book.Model.akka.Pack;

import akka.actor.typed.ActorRef;
import com.applicaton.Book.Model.akka.Model.Integral;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Task implements IMesssage {
    private int leftBoard;
    private int rightBoard;
    private double numb;
    private double h;
    private Integral integral; // использование ф-и
    //--------------------------
    private int workId;
    private ActorRef<IMesssage> replyTo;
}