package org.emp.gl.core.launcher;

import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.clients.Horloge;

//  New import for countdown
import org.emp.gl.clients.CompteARebours;
import java.util.Random;

public class App {

    public static void main(String[] args) {
        TimerService timer = new DummyTimeServiceImpl();
        new CompteARebours("fr", 2, timer);
        new CompteARebours("fr", 17, timer);
        new CompteARebours("fr", 14, timer);
        new CompteARebours("fr", 11, timer);
        new CompteARebours("fr", 19, timer);
        new CompteARebours("fr", 14, timer);
        new CompteARebours("fr", 16, timer);
        new CompteARebours("fr", 12, timer);
        new CompteARebours("fr", 13, timer);
        new CompteARebours("fr", 18, timer);
        new CompteARebours("fr", 16, timer);
        new CompteARebours("fr", 10, timer);
        new CompteARebours("fr", 13, timer);
    }

    private static void testDuTimeService() {
        // 1. Créer le TimerService
        TimerService service = new DummyTimeServiceImpl();

        
    }




    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
