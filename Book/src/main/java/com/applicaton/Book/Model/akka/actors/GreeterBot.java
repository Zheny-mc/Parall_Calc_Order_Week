package com.applicaton.Book.Model.akka.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.applicaton.Book.Model.akka.Model.Integral;
import com.applicaton.Book.Model.akka.Pack.IMesssage;
import com.applicaton.Book.Model.akka.Pack.PostStop;
import com.applicaton.Book.Model.akka.Pack.ResTask;
import com.applicaton.Book.Model.akka.Pack.Task;


public class GreeterBot extends AbstractBehavior<IMesssage> {

    public static Behavior<IMesssage> create() {
        return Behaviors.setup(context -> new GreeterBot(context));
    }

    private GreeterBot(ActorContext<IMesssage> context) {
        super(context);
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(Task.class, this::onTask)
                .onMessage(PostStop.class, this::onPostStop)
                .build();
    }

    private Behavior<IMesssage> onTask(Task message) {
        getContext().getLog().info("Выполнение этапа {}", message.getWorkId());

        int leftBoard = message.getLeftBoard();
        int rightBoard = message.getRightBoard();
        double a = message.getNumb();
        double h = message.getH();
        Integral integr = message.getIntegral();

        double partSum = 0.d;
        for (int i = leftBoard; i <= rightBoard; ++i) {
            partSum += integr.func(a + i*h);
        }

        message.getReplyTo().tell(new ResTask(message.getWorkId(), partSum));
        return this;
    }

    private Behavior<IMesssage> onPostStop(PostStop command) {
        getContext().getSystem().log().info("GreeterBot stopped");
        return Behaviors.stopped();
    }

}
