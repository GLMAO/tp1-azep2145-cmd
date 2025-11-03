package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;
import java.beans.PropertyChangeEvent;

/**
 * La classe Horloge agit comme un "Observer".
 * Elle est notifiée à chaque changement de temps par le TimerService.
 */
public class Horloge implements TimerChangeListener {

    private final String name;
    private final TimerService timerService;

    public Horloge(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;

        // S'enregistrer comme observateur
        timerService.addTimeChangeListener(this);
        System.out.println("Horloge " + name + " initialisée et abonnée !");
    }

    // Méthode de TimerChangeListener
    @Override
    public void propertyChange(String propertyName, Object oldValue, Object newValue) {
        if (TimerChangeListener.SECONDE_PROP.equals(propertyName)) {
            afficherHeure();
        }
    }

    // Méthode imposée par PropertyChangeListener
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        propertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }

    private void afficherHeure() {
        System.out.printf(" Horloge %s : %02d:%02d:%02d%n",
                name,
                timerService.getHeures(),
                timerService.getMinutes(),
                timerService.getSecondes());
    }
}
