package org.emp.gl.timer.service.impl;


import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * Implémentation concrète du service de temps (TimerService)
 * utilisant le Design Pattern Observer.
 * 
 * Ce service agit comme le "Subject" : il notifie les observateurs (listeners)
 * à chaque changement de dixième, seconde, minute ou heure.
 */
public class DummyTimeServiceImpl implements TimerService {

    private int dixiemeDeSeconde;
    private int minutes;
    private int secondes;
    private int heures;

    // Liste des observateurs (observers)
    private final List<TimerChangeListener> listeners = new LinkedList<>();

    public DummyTimeServiceImpl() {
        setTimeValues();

        // Timer Java pour générer un "tick" toutes les 100 millisecondes
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    // Met à jour les valeurs du temps
    private void setTimeValues() {
        LocalTime localTime = LocalTime.now();

        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
        setDixiemeDeSeconde(localTime.getNano() / 100_000_000);
    }

    // Méthodes du pattern Observer : gestion des abonnés
    @Override
    public void addTimeChangeListener(TimerChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener listener) {
        listeners.remove(listener);
    }

    // Méthode appelée à chaque "tick" du Timer
    private void timeChanged() {
        setTimeValues();
    }

    // === Gestion des changements et notifications ===

    public void setDixiemeDeSeconde(int newValue) {
        if (dixiemeDeSeconde == newValue) return;
        int oldValue = dixiemeDeSeconde;
        dixiemeDeSeconde = newValue;
        notifyListeners(TimerChangeListener.DIXEME_DE_SECONDE_PROP, oldValue, newValue);
    }

    public void setSecondes(int newValue) {
        if (secondes == newValue) return;
        int oldValue = secondes;
        secondes = newValue;
        notifyListeners(TimerChangeListener.SECONDE_PROP, oldValue, newValue);
    }

    public void setMinutes(int newValue) {
        if (minutes == newValue) return;
        int oldValue = minutes;
        minutes = newValue;
        notifyListeners(TimerChangeListener.MINUTE_PROP, oldValue, newValue); // ✅ correction ici
    }

    public void setHeures(int newValue) {
        if (heures == newValue) return;
        int oldValue = heures;
        heures = newValue;
        notifyListeners(TimerChangeListener.HEURE_PROP, oldValue, newValue); // ✅ correction ici
    }

    // Méthode utilitaire : notifie tous les observateurs
    private void notifyListeners(String property, Object oldValue, Object newValue) {
        for (TimerChangeListener listener : listeners) {
            listener.propertyChange(property, oldValue, newValue);
        }
    }

    // === Getters ===
    @Override
    public int getDixiemeDeSeconde() {
        return dixiemeDeSeconde;
    }

    @Override
    public int getSecondes() {
        return secondes;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getHeures() {
        return heures;
    }
}
