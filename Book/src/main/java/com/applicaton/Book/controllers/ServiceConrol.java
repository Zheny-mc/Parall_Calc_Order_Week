package com.applicaton.Book.controllers;

import akka.actor.typed.ActorSystem;
import com.applicaton.Book.Model.Engine.Pair;
import com.applicaton.Book.Model.akka.Pack.IMesssage;
import com.applicaton.Book.Model.akka.Pack.Start;
import com.applicaton.Book.Model.akka.actors.GreeterMain;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServiceConrol {
    private ActorSystem<IMesssage> greeterMain;

    @GetMapping
    public String getResult() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        return GreeterMain.readResult();
    }

    @PostMapping
    public void sendData(@RequestBody Pair pair) {
        greeterMain = ActorSystem.create(GreeterMain.create(), "Integral");
        greeterMain.tell(new Start(
                Double.parseDouble(pair.getNumberF()),
                Double.parseDouble(pair.getNumberS()),
                10));

        System.out.println("send Data = " + Double.parseDouble(pair.getNumberF()) + ": " +
                Double.parseDouble(pair.getNumberS()));
    }

}
