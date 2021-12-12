package com.applicaton.Book.Model.akka.actors;


import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.applicaton.Book.Model.akka.Model.Integral;
import com.applicaton.Book.Model.akka.Pack.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GreeterMain extends AbstractBehavior<IMesssage> {
    private final ActorRef<IMesssage> greeter;
    private List<ActorRef<IMesssage>> actors;

    public static Behavior<IMesssage> create() {
        return Behaviors.setup(GreeterMain::new);
    }

    private GreeterMain(ActorContext<IMesssage> context) {
        super(context);
        //#create-actors
        actors = new ArrayList<>();
        greeter = context.spawn(Greeter.create(), "greeter");
        //#create-actors
    }

    @Override
    public Receive<IMesssage> createReceive() {
        return newReceiveBuilder()
                .onMessage(Start.class, this::onStart)
                .onMessage(ResBigTask.class, this::onResBigTask)
                .build();
    }

    private Behavior<IMesssage> onStart(Start mess) {
        getContext().getLog().info("Создание задачи!");
        actors.add(greeter);
        //#create-actors
        ActorRef<IMesssage> replyTofirst =
                getContext().spawn(GreeterBot.create(), "worker1");
        actors.add(replyTofirst);

        ActorRef<IMesssage> replyToSecond =
                getContext().spawn(GreeterBot.create(), "worker2");
        actors.add(replyToSecond);
        //#create-actors
        //----------------Task---------------
        Integral integral = Integral.create(mess.getNumA(), mess.getNumB(), mess.getCountN());
        //------------------------------------
        //send message
        getContext().getLog().info(integral.toString());
        greeter.tell(new BigTask(integral,
                replyTofirst, replyToSecond, getContext().getSelf()));
        //send message
        return this;
    }

    private Behavior<IMesssage> onResBigTask(ResBigTask message) {
        String answer = String.format(
                "Задача готова! Значение интеграла: %.3f",
                message.getSum());
        getContext().getLog().info(answer);

        //запись в файл
        writeResInFile(answer);

        for (ActorRef<IMesssage> it: actors)
            it.tell(new PostStop());
        return Behaviors.stopped();
    }

    private void writeResInFile(String str) {
        Path path = Path.of("result.txt");
        List<String> list = null;
        try {
            Files.writeString(path, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readResult() {
        Path path = Path.of("result.txt");
        List<String> list = null;
        try {
            list = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list.size() > 0)
            return list.get(0);

        return "Error";
    }
}
