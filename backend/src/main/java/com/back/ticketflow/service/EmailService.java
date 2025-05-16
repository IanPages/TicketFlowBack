package com.back.ticketflow.service;

import com.back.ticketflow.model.Booking;
import com.back.ticketflow.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingConfirmation(String to, Booking booking) {
        String subject = "Confirmación de Reserva - TicketFlow";
        StringBuilder body = new StringBuilder();
        body.append("Hola ").append(booking.getUser().getUsername()).append(",\n\n");
        body.append("Tu reserva ha sido confirmada. Aquí están los detalles:\n\n");
        body.append("Evento: ").append(booking.getEvent().getName()).append("\n");
        body.append("Fecha: ").append(booking.getEvent().getDate()).append("\n");
        body.append("Cantidad de entradas: ").append(booking.getQuantity()).append("\n");

        if (booking.getBookedSeats() != null && !booking.getBookedSeats().isEmpty()) {
            body.append("Asientos: ");
            for (Seat seat : booking.getBookedSeats()) {
                body.append("[").append("Fila: ").append(seat.getFila()).append("-").append(" Asiento: ").append(seat.getNumero()).append("] ");
            }
            body.append("\n");
        } else {
            body.append("Tipo de entrada: General\n");
        }

        body.append("\n¡Gracias por tu compra!");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body.toString());

        mailSender.send(message);
    }
}
