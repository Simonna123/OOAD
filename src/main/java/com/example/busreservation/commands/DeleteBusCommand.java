package com.example.busreservation.commands;

import com.example.busreservation.services.BusService;

public class DeleteBusCommand implements BusCommand {
    private final BusService busService;
    private final Long busId;

    public DeleteBusCommand(BusService busService, Long busId) {
        this.busService = busService;
        this.busId = busId;
    }

    @Override
    public void execute() {
        busService.deleteBus(busId);
    }
}
