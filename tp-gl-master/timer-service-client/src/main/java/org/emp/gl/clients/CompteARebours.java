package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class CompteARebours implements TimerChangeListener {

    private int counter;
    private final TimerService service;
    private final String name;

    public CompteARebours(String name, int initialValue, TimerService service) {
        this.name = name;
        this.counter = initialValue;
        this.service = service;
        service.addTimeChangeListener(this);
    }

    @Override
    public void propertyChange(String property, Object oldValue, Object newValue) {
        // We decrease counter every second
        if (property.equals(TimerChangeListener.SECONDE_PROP)) {
            counter--;
            System.out.println(name + " countdown: " + counter);
            
            if (counter <= 0) {
                System.out.println(name + " finished!");
                service.removeTimeChangeListener(this); // ✅ Unsubscribe at 0
            }
        }
    }
}
