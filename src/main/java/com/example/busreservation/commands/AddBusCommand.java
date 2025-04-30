package com.example.busreservation.commands;

import com.example.busreservation.models.Bus;
import com.example.busreservation.services.BusService;

public class AddBusCommand implements BusCommand {
    private final BusService busService;
    private final Bus bus;

    public AddBusCommand(BusService busService, Bus bus) {
        this.busService = busService;
        this.bus = bus;
    }

    @Override
    public void execute() {
        busService.addBus(bus);
    }
}
