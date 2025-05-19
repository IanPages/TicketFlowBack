package com.back.ticketflow.service;

import com.back.ticketflow.model.Event;
import com.back.ticketflow.model.Sala;
import com.back.ticketflow.model.Seat;
import com.back.ticketflow.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public void deleteSeatsByEventId(Integer eventId) {
        seatRepository.deleteByEventId(eventId);
    }

    public List<Seat> generateSeats(Event event, Sala sala, int normal, int vip){
        List<Seat> seats = new ArrayList<>();

        int total = normal + vip;
        int filas = sala.getFilas();
        int columnas = sala.getColumnas();

        int seatCreado = 0;
        int vipRestantes = vip;

        for (int fila = 1; fila <= filas; fila++) {
            for (int columna = 1; columna <= columnas; columna++){
                if (seatCreado >= total){
                    break;
                }
                Seat seat = new Seat();
                seat.setFila(fila);
                seat.setNumero(columna);
                seat.setEvent(event);
                seat.setTipo(vipRestantes > 0 ? "VIP" : "NORMAL");
                seat.setOcupado(false);

                seats.add(seat);
                seatCreado++;
                if (vipRestantes > 0){
                    vipRestantes--;
                }
            }
        }
        return seats;
    }
}
