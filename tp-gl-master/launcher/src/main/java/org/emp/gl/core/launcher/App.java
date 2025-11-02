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
        new CompteARebours("fr", 10, timer);
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
