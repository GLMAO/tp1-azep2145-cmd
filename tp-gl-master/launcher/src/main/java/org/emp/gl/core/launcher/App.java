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
        new CompteARebours("fr1", 2, timer);
        new CompteARebours("fr2", 17, timer);
        new CompteARebours("fr3", 14, timer);
        new CompteARebours("fr4", 11, timer);
        new CompteARebours("fr5", 19, timer);
        new CompteARebours("fr6", 14, timer);
        new CompteARebours("fr7", 16, timer);
        new CompteARebours("fr8", 12, timer);
        new CompteARebours("fr9", 13, timer);
        new CompteARebours("fr10", 18, timer);
        new CompteARebours("fr11", 16, timer);
        new CompteARebours("fr12", 10, timer);
        new CompteARebours("fr13", 13, timer);
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
