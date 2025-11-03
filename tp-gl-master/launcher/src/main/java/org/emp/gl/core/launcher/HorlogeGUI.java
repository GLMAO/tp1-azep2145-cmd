package org.emp.gl.core.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * Modern real-time clock GUI with contemporary design.
 * Features gradient backgrounds, smooth animations, and modern typography.
 * 
 * @author Generated
 * @version 3.0
 */
public class HorlogeGUI extends JFrame implements TimerChangeListener {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(HorlogeGUI.class.getName());
    
    // Modern UI Configuration
    private static final String WINDOW_TITLE = "Digital Clock";
    private static final int WINDOW_WIDTH = 450;
    private static final int WINDOW_HEIGHT = 200;
    private static final String DIGITAL_FONT = "SF Pro Display";
    private static final String FALLBACK_FONT = "Segoe UI";
    private static final int TIME_FONT_SIZE = 52;
    private static final int DATE_FONT_SIZE = 16;
    
    // Modern Color Palette
    private static final Color GRADIENT_START = new Color(20, 30, 48);
    private static final Color GRADIENT_END = new Color(36, 59, 85);
    private static final Color TIME_COLOR = new Color(255, 255, 255);
    private static final Color DATE_COLOR = new Color(200, 210, 220);
    private static final Color ACCENT_COLOR = new Color(100, 200, 255);
    
    private final TimerService timerService;
    private final JLabel timeLabel;
    private final JLabel dateLabel;
    private final JPanel contentPanel;
    
    /**
     * Constructs the modern clock GUI.
     * 
     * @param timerService the timer service to observe
     */
    public HorlogeGUI(TimerService timerService) {
        super(WINDOW_TITLE);
        
        if (timerService == null) {
            throw new IllegalArgumentException("TimerService cannot be null");
        }
        
        this.timerService = timerService;
        this.timeLabel = createTimeLabel();
        this.dateLabel = createDateLabel();
        this.contentPanel = createContentPanel();
        
        initializeModernFrame();
        registerAsObserver();
        updateDisplay();
        
        LOGGER.info("Modern Clock GUI initialized successfully");
    }
    
    /**
     * Initializes the frame with modern design.
     */
    private void initializeModernFrame() {
        setUndecorated(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Rounded corners
        setShape(new RoundRectangle2D.Double(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 20, 20));
        
        setupModernLayout();
        addWindowControls();
        
        setVisible(true);
    }
    
    /**
     * Creates the gradient content panel.
     */
    private JPanel createContentPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, GRADIENT_START,
                    0, getHeight(), GRADIENT_END
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }
    
    /**
     * Sets up the modern layout with all components.
     */
    private void setupModernLayout() {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 20, 10, 20);
        
        // Time display
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(timeLabel, gbc);
        
        // Date display
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 20);
        contentPanel.add(dateLabel, gbc);
        
        // Close button
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 20, 10, 20);
        contentPanel.add(createCloseButton(), gbc);
        
        setContentPane(contentPanel);
    }
    
    /**
     * Creates the time display label with modern styling.
     */
    private JLabel createTimeLabel() {
        JLabel label = new JLabel("00:00:00", SwingConstants.CENTER);
        
        // Try modern font, fallback to system font
        Font font = tryLoadFont(DIGITAL_FONT, FALLBACK_FONT, Font.BOLD, TIME_FONT_SIZE);
        label.setFont(font);
        label.setForeground(TIME_COLOR);
        
        return label;
    }
    
    /**
     * Creates the date display label.
     */
    private JLabel createDateLabel() {
        JLabel label = new JLabel("Loading...", SwingConstants.CENTER);
        
        Font font = tryLoadFont(DIGITAL_FONT, FALLBACK_FONT, Font.PLAIN, DATE_FONT_SIZE);
        label.setFont(font);
        label.setForeground(DATE_COLOR);
        
        return label;
    }
    
    /**
     * Creates a modern close button.
     */
    private JButton createCloseButton() {
        JButton closeBtn = new JButton("×");
        closeBtn.setFont(new Font(FALLBACK_FONT, Font.PLAIN, 24));
        closeBtn.setForeground(DATE_COLOR);
        closeBtn.setBackground(new Color(255, 255, 255, 20));
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setPreferredSize(new Dimension(40, 40));
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeBtn.setBackground(new Color(255, 100, 100, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeBtn.setBackground(new Color(255, 255, 255, 20));
            }
        });
        
        closeBtn.addActionListener(e -> dispose());
        
        return closeBtn;
    }
    
    /**
     * Tries to load a font with fallback support.
     */
    private Font tryLoadFont(String preferredFont, String fallbackFont, int style, int size) {
        Font font = new Font(preferredFont, style, size);
        
        // Check if font exists, otherwise use fallback
        if (!font.getFamily().equals(preferredFont)) {
            font = new Font(fallbackFont, style, size);
        }
        
        return font;
    }
    
    /**
     * Adds drag capability to move the window.
     */
    private void addWindowControls() {
        final Point[] mouseDownCompCoords = {null};
        
        contentPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
        });
        
        contentPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(
                    currCoords.x - mouseDownCompCoords[0].x,
                    currCoords.y - mouseDownCompCoords[0].y
                );
            }
        });
    }
    
    /**
     * Registers this GUI as an observer of the timer service.
     */
    private void registerAsObserver() {
        timerService.addTimeChangeListener(this);
        LOGGER.fine("Registered as timer change listener");
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (isTimeChangeEvent(evt.getPropertyName())) {
            scheduleDisplayUpdate();
        }
    }
    
    @Override
    public void propertyChange(String propertyName, Object oldValue, Object newValue) {
        if (isTimeChangeEvent(propertyName)) {
            scheduleDisplayUpdate();
        }
    }
    
    /**
     * Checks if the event is a time change event.
     */
    private boolean isTimeChangeEvent(String propertyName) {
        return TimerChangeListener.SECONDE_PROP.equals(propertyName);
    }
    
    /**
     * Schedules a display update on the EDT.
     */
    private void scheduleDisplayUpdate() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::updateDisplay);
        } else {
            updateDisplay();
        }
    }
    
    /**
     * Updates the time and date display with modern formatting.
     */
    private void updateDisplay() {
        int hours = timerService.getHeures();
        int minutes = timerService.getMinutes();
        int seconds = timerService.getSecondes();
        
        // Format time with modern separator
        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(formattedTime);
        
        // Update date (simulated - you might want to use actual date)
        String period = hours >= 12 ? "PM" : "AM";
        int displayHours = hours % 12;
        if (displayHours == 0) displayHours = 12;
        
        String dateInfo = String.format("%d:%02d %s • Today", displayHours, minutes, period);
        dateLabel.setText(dateInfo);
    }
    
    /**
     * Formats time components into a display string.
     */
    private String formatTime(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    @Override
    public void dispose() {
        timerService.removeTimeChangeListener(this);
        LOGGER.info("Modern Clock GUI disposed");
        super.dispose();
    }
    
    /**
     * Gets the current timer service instance.
     */
    public TimerService getTimerService() {
        return timerService;
    }
}