package com.applicaton.Book.Model.akka.Pack;

import akka.actor.typed.ActorRef;
import com.applicaton.Book.Model.akka.Model.Integral;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class BigTask implements IMesssage {
    private Integral integral;
    private ActorRef<IMesssage> sendToF;
    private ActorRef<IMesssage> sendToS;
    private ActorRef<IMesssage> replyTo;
}
