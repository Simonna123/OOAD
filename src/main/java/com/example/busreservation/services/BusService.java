package com.example.busreservation.services;

import com.example.busreservation.models.Bus;
import com.example.busreservation.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    // Add a new bus
    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }

    // Update an existing bus by ID
    @Transactional
    public Bus updateBus(Long id, Bus updatedBus) {
        // Find the existing bus or throw a custom exception
        Bus existingBus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + id));

        // Update the bus fields
        existingBus.setBusName(updatedBus.getBusName());
        existingBus.setSource(updatedBus.getSource());
        existingBus.setDestination(updatedBus.getDestination());
        existingBus.setDepartureTime(updatedBus.getDepartureTime());
        existingBus.setArrivalTime(updatedBus.getArrivalTime());
        existingBus.setSeatsAvailable(updatedBus.getSeatsAvailable());

        return busRepository.save(existingBus);
    }

    // Get all buses
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // Delete a bus by ID
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    // Get a bus by ID
    public Bus getBusById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + id));
    }
}
