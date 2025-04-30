package com.example.busreservation.commands;

import org.springframework.stereotype.Component;

@Component
public class BusCommandExecutor {
    public void executeCommand(BusCommand command) {
        command.execute();
    }
}
