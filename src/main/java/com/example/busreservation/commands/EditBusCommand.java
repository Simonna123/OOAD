package com.example.busreservation.commands;

import com.example.busreservation.models.Bus;
import com.example.busreservation.services.BusService;

public class EditBusCommand implements BusCommand {
    private final BusService busService;
    private final Long id;
    private final Bus updatedBus;

    public EditBusCommand(BusService busService, Long id, Bus updatedBus) {
        this.busService = busService;
        this.id = id;
        this.updatedBus = updatedBus;
    }

    @Override
    public void execute() {
        busService.updateBus(id, updatedBus);
    }
}
