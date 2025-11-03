package org.emp.gl.core.launcher;

import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.clients.CompteARebours;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

/**
 * Main application launcher for the timer system.
 * Initializes the timer service, GUI, and countdown instances.
 * 
 * @author Generated
 * @version 2.0
 */
public class App {
    
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    
    // Configuration constants
    private static final int COUNTDOWN_COUNT = 10;
    private static final int MIN_COUNTDOWN_VALUE = 10;
    private static final int MAX_COUNTDOWN_VALUE = 20;
    private static final String COUNTDOWN_PREFIX = "compte";
    
    private final TimerService timerService;
    private final List<CompteARebours> countdowns;
    
    /**
     * Private constructor to encapsulate application logic.
     */
    private App() {
        this.timerService = initializeTimerService();
        this.countdowns = new ArrayList<>();
    }
    
    /**
     * Application entry point.
     * 
     * @param args command line arguments (currently unused)
     */
    public static void main(String[] args) {
        App application = new App();
        application.launch();
    }
    
    /**
     * Launches the application components.
     */
    private void launch() {
        LOGGER.info("Starting application...");
        
        launchGUI();
        createCountdowns();
        
        LOGGER.info("Application started successfully");
    }
    
    /**
     * Initializes and returns the timer service.
     * 
     * @return configured TimerService instance
     */
    private TimerService initializeTimerService() {
        LOGGER.info("Initializing timer service");
        return new DummyTimeServiceImpl();
    }
    
    /**
     * Launches the graphical user interface on the Event Dispatch Thread.
     */
    private void launchGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                new HorlogeGUI(timerService);
                LOGGER.info("GUI launched successfully");
            } catch (Exception e) {
                LOGGER.severe("Failed to launch GUI: " + e.getMessage());
            }
        });
    }
    
    /**
     * Creates multiple countdown instances with random initial values.
     */
    private void createCountdowns() {
        LOGGER.info(String.format("Creating %d countdowns", COUNTDOWN_COUNT));
        
        for (int i = 1; i <= COUNTDOWN_COUNT; i++) {
            int initialValue = generateRandomCountdownValue();
            String countdownName = COUNTDOWN_PREFIX + i;
            
            CompteARebours countdown = new CompteARebours(
                countdownName, 
                initialValue, 
                timerService
            );
            
            countdowns.add(countdown);
            LOGGER.fine(String.format("Created countdown '%s' with initial value %d", 
                countdownName, initialValue));
        }
    }
    
    /**
     * Generates a random countdown value within the configured range.
     * Uses ThreadLocalRandom for better performance in concurrent contexts.
     * 
     * @return random value between MIN_COUNTDOWN_VALUE and MAX_COUNTDOWN_VALUE (inclusive)
     */
    private int generateRandomCountdownValue() {
        return ThreadLocalRandom.current()
            .nextInt(MIN_COUNTDOWN_VALUE, MAX_COUNTDOWN_VALUE + 1);
    }
    
    /**
     * Creates a countdown with a specific initial value.
     * Useful for testing or special cases.
     * 
     * @param name countdown identifier
     * @param initialValue starting countdown value
     * @return created CompteARebours instance
     */
    public CompteARebours createCustomCountdown(String name, int initialValue) {
        CompteARebours countdown = new CompteARebours(name, initialValue, timerService);
        countdowns.add(countdown);
        LOGGER.info(String.format("Created custom countdown '%s' with value %d", 
            name, initialValue));
        return countdown;
    }
    
    /**
     * Returns all active countdowns.
     * 
     * @return unmodifiable list of countdowns
     */
    public List<CompteARebours> getCountdowns() {
        return List.copyOf(countdowns);
    }
    
    /**
     * Utility method for testing the timer service.
     * Can be called independently for validation purposes.
     */
    public static void testTimerService() {
        LOGGER.info("Running timer service tests");
        TimerService service = new DummyTimeServiceImpl();
        
        // Add manual test cases here as needed
        LOGGER.info(String.format("Current time: %02d:%02d:%02d",
            service.getHeures(),
            service.getMinutes(),
            service.getSecondes()));
    }
    
    /**
     * Clears the console screen using ANSI escape codes.
     * Works on Unix-like systems and Windows 10+ with ANSI support.
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}