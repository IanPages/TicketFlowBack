package com.back.ticketflow.config;

import com.back.ticketflow.dto.UserDTO;
import com.back.ticketflow.model.*;
import com.back.ticketflow.repository.*;
import com.back.ticketflow.service.SeatService;
import com.back.ticketflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private SalaRepository salaRepository;
    private GenreEventRepository genreEventRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private EventRepository eventRepository;
    private SeatService seatService;

    @Autowired
    public DataSeeder( RoleRepository roleRepository,UserRepository userRepository, SalaRepository salaRepository, GenreEventRepository genreEventRepository, PasswordEncoder passwordEncoder, UserService userService, EventRepository eventRepository , SeatService seatService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.salaRepository = salaRepository;
        this.genreEventRepository = genreEventRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.eventRepository = eventRepository;
        this.seatService = seatService;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("Admin")
                .orElseGet(() -> roleRepository.save(new Role("Admin")));

        Role basicRole = roleRepository.findByName("Basic")
                .orElseGet(() -> roleRepository.save(new Role("Basic")));

        if (userRepository.count() == 0) {
            userService.saveAdminUser(new UserDTO("admin1@gmail.com", "admin123"));
            userService.saveAdminUser(new UserDTO("admin2@gmail.com", "admin123"));
            userService.saveUser(new UserDTO("basic1@gmail.com","basic123"));
            userService.saveUser(new UserDTO("basic2@gmail.com", "basic123"));
        }

        if (salaRepository.count() == 0) {
            salaRepository.saveAll(List.of(
                    new Sala("Sala Central", "Centro", 100, true, 10, 10),
                    new Sala("Sala Norte", "Barrio Norte", 200, true, 10, 20),
                    new Sala("Sala Sur", "Barrio Sur", 150, true , 10 , 15)
            ));
        }

        if (genreEventRepository.count() == 0) {
            genreEventRepository.saveAll(List.of(
                    new GenreEvent("Danza"),
                    new GenreEvent("Festival"),
                    new GenreEvent("Musical"),
                    new GenreEvent("Teatro"),
                    new GenreEvent("Comedia"),
                    new GenreEvent("Documental"),
                    new GenreEvent("Orquesta")
            ));
        }

        if (eventRepository.count() == 0) {
            GenreEvent teatro = genreEventRepository.findByName("Teatro").orElse(null);
            GenreEvent musical = genreEventRepository.findByName("Musical").orElse(null);
            GenreEvent festival = genreEventRepository.findByName("Festival").orElse(null);
            Sala salaCentral = salaRepository.findByName("Sala Central").orElse(null);
            Sala salaSur = salaRepository.findByName("Sala Sur").orElse(null);

            Event indoorEvent = new Event();
            indoorEvent.setName("Obra de Teatro Clásica");
            indoorEvent.setDescription("Una emocionante obra de teatro en el corazón de la ciudad.");
            indoorEvent.setImage1("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326588/teatroClasico_sfrgkz.jpg");
            indoorEvent.setImage2("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326587/teatroMerida_gilmrz.jpg");
            indoorEvent.setDate(Date.valueOf("2025-06-02"));
            indoorEvent.setLocation("Teatro leal, Santa Cruz 38001");
            indoorEvent.setCapacity(100);
            indoorEvent.setNormalPrice(20.0);
            indoorEvent.setVipPrice(40.0);
            indoorEvent.setNormalCapacity(80);
            indoorEvent.setVipCapacity(20);
            indoorEvent.setGenre(teatro);
            indoorEvent.setSala(salaCentral);
            List<Seat> seats1 = seatService.generateSeats(indoorEvent, salaCentral, 80, 20);
            indoorEvent.setSeats(seats1);

            Event indoorEvent2 = new Event();
            indoorEvent2.setName("Obra de Teatro Clásica 2.0");
            indoorEvent2.setDescription("Una emocionante obra de teatro en el corazón de la ciudad 2.0.");
            indoorEvent2.setImage1("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326588/teatroClasico_sfrgkz.jpg");
            indoorEvent2.setImage2("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326587/teatroMerida_gilmrz.jpg");
            indoorEvent2.setDate(Date.valueOf("2025-06-08"));
            indoorEvent2.setLocation("Teatro leal, Santa Cruz 38001");
            indoorEvent2.setCapacity(150);
            indoorEvent2.setNormalPrice(20.0);
            indoorEvent2.setVipPrice(40.0);
            indoorEvent2.setNormalCapacity(120);
            indoorEvent2.setVipCapacity(30);
            indoorEvent2.setGenre(teatro);
            indoorEvent2.setSala(salaSur);
            List<Seat> seats2 = seatService.generateSeats(indoorEvent2, salaSur, 120, 30);
            indoorEvent2.setSeats(seats2);

            Event outdoorEvent = new Event();
            outdoorEvent.setName("Festival de Música al Aire Libre");
            outdoorEvent.setDescription("Disfruta de una tarde de música en el parque.");
            outdoorEvent.setImage1("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326587/port2_bqnd9e.jpg");
            outdoorEvent.setImage2("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326588/festi_huahbb.jpg");
            outdoorEvent.setDate(Date.valueOf("2025-06-03"));
            outdoorEvent.setLocation("Parque Central, Santa Cruz 38001");
            outdoorEvent.setCapacity(500);
            outdoorEvent.setNormalPrice(15.0);
            outdoorEvent.setVipPrice(30.0);
            outdoorEvent.setNormalCapacity(500);
            outdoorEvent.setVipCapacity(100);
            outdoorEvent.setGenre(musical);
            outdoorEvent.setSala(null);

            Event outdoorEvent2 = new Event();
            outdoorEvent2.setName("Concierto de Jazz al Atardecer");
            outdoorEvent2.setDescription("Disfruta de un relajante concierto de jazz al aire libre.");
            outdoorEvent2.setImage1("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326739/jazzport_tqzs6i.jpg");
            outdoorEvent2.setImage2("https://res.cloudinary.com/dtnprgkqq/image/upload/v1747326739/jazzport2_nmtzwp.webp");
            outdoorEvent2.setDate(Date.valueOf("2025-06-01"));
            outdoorEvent2.setLocation("Jardín Botánico, Santa Cruz 38004");
            outdoorEvent2.setCapacity(300);
            outdoorEvent2.setNormalPrice(15.0);
            outdoorEvent2.setVipPrice(30.0);
            outdoorEvent2.setNormalCapacity(300);
            outdoorEvent2.setVipCapacity(50);
            outdoorEvent2.setGenre(festival);
            outdoorEvent2.setSala(null);

            eventRepository.saveAll(List.of(indoorEvent, outdoorEvent, indoorEvent2, outdoorEvent2));
        }
    }

}
