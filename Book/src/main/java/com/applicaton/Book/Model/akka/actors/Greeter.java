package com.applicaton.Book.Model.akka.actors;


import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.applicaton.Book.Model.akka.Model.Integral;
import com.applicaton.Book.Model.akka.Pack.*;


// #greeter
public class Greeter extends AbstractBehavior<IMesssage> {
    private int countStep;
    private double sum;
    private double h;


    private ActorRef<IMesssage> replyTo;

    public static Behavior<IMesssage> create() {
        return Behaviors.setup(Greeter::new);
    }

    private Greeter(ActorContext<IMesssage> context) {
        super(context);
        sum = 0.d;
        countStep = 0;
        h = 0.;
    }

    private void sendSumIfEnd() {
        if (countStep == 2) {
            sum *= h;
            getContext().getLog().info("Отправка вычисленного интеграла!");
            replyTo.tell(new ResBigTask(sum));
        }
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(BigTask.class, this::onBigTask)
                .onMessage(ResTask.class, this::onResTask)
                .onMessage(PostStop.class, this::onPostStop)
                .build();
    }

    private Behavior<IMesssage> onBigTask(BigTask message) {
        getContext().getLog().info("Начало работы!");
        //Parsing Task
        ActorRef<IMesssage> workF = message.getSendToF();
        ActorRef<IMesssage> workS = message.getSendToF();
        replyTo = message.getReplyTo();

        Integral integral = message.getIntegral();
        double numA = integral.getNumA();
        double numB = integral.getNumB();
        int countN = integral.getCountN();

        sum += (integral.func(numA) + integral.func(numB)) / 2.d;
        //-----------------------------------------------
        final int numbActor = 2;

        h = (numB-numA) / (double)countN;
        final int step = countN / numbActor;

        Task task1 = new Task(1, step-1, numA, h, integral,
                1, getContext().getSelf());
        //#greeter-send-message
        message.getSendToF().tell(task1);

        Task task2 = new Task(step, countN-1, numA, h, integral,
                2, getContext().getSelf());
        //#greeter-send-message
        message.getSendToS().tell(task2);
        //-----------------------------------------------

        return this;
    }

    private Behavior<IMesssage> onResTask(ResTask mess) {
        sum += mess.getSum();

        countStep++;
        sendSumIfEnd();
        return this;
    }

    private Behavior<IMesssage> onPostStop(PostStop command) {
        getContext().getSystem().log().info("Greeter stopped");
        return Behaviors.stopped();
    }

}
// #greeter

