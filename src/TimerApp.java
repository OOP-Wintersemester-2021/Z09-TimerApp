import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.graphics.Label;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

public class TimerApp extends GraphicsApp {

    private static final Color RED = new Color(234, 49, 63); // "Selbstgemischter" RGB-Farbe (rot)
    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color CREAM = new Color(241, 255, 250); // "Selbstgemischter" RGB-Farbe (creme)
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;
    private static final Color BACKGROUND_COLOR = GREY;
    private static final int CLOCK_RADIUS = 250;
    private static final int NUMBER_OF_MARKERS = 60;
    private static final int MARKER_RADIUS = 7;
    private static final int QUARTER_MARKER_RADIUS = 10;
    private static final Color MARKER_COLOR = YELLOW;
    private static final Color MARKER_PAST_COLOR = RED;
    private static final String APPLICATION_LABEL_TEXT = "60 Seconds Timer";
    private static final String APPLICATION_LABEL_FONT = "Arial Rounded MT Bold";
    private static final int APPLICATION_LABEL_FONT_SIZE = 16;
    private static final Color APPLICATION_LABEL_COLOR = CREAM;

    private Circle[] markers;
    private Label applicationLabel;
    private double startupTimeInMs;

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        markers = createClockMarkers();
        applicationLabel = createApplicationLabel();
        startupTimeInMs = System.currentTimeMillis();
    }

    private Label createApplicationLabel() {
        Label  label = new Label(0, 0, APPLICATION_LABEL_TEXT, APPLICATION_LABEL_COLOR);
        label.setFont(APPLICATION_LABEL_FONT);
        label.setFontSize(APPLICATION_LABEL_FONT_SIZE);
        label.setXPos(label.getHeightEstimate());
        label.setYPos(getWidth() - label.getHeightEstimate());
        return label;
    }

    private Circle[] createClockMarkers() {
        Circle[] clockMarkers = new Circle[NUMBER_OF_MARKERS];
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int initialMarkerDegree = -90;
        for (int i = 0; i < clockMarkers.length; i++) {
            double markerX = centerX + (CLOCK_RADIUS * Math.cos(Math.toRadians(initialMarkerDegree)));
            double markerY = centerY + (CLOCK_RADIUS * Math.sin(Math.toRadians(initialMarkerDegree)));
            int markerRadius = MARKER_RADIUS;
            if (i % 15 == 0) {
                markerRadius = QUARTER_MARKER_RADIUS;
            }
            clockMarkers[i] = new Circle((float) markerX, (float) markerY, markerRadius, MARKER_COLOR);
            initialMarkerDegree += 360 / NUMBER_OF_MARKERS;
        }
        return clockMarkers;
    }

    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
        updateAndDrawMarkers();
        applicationLabel.draw();
    }

    private void updateAndDrawMarkers() {
        int secondsSinceStart = getSecondsSinceStart(startupTimeInMs);
        updateMarkers(markers, secondsSinceStart);
        drawMarkers(markers);
    }

    private int getSecondsSinceStart(double startupTimeInMs) {
        double deltaTime = (System.currentTimeMillis() - startupTimeInMs) / 1000;
        return (int) deltaTime;
    }

    private void updateMarkers(Circle[] markers, int secondsSinceStartup) {
        if (secondsSinceStartup < 0) {
            return;
        }
        int minutesSinceStartup = secondsSinceStartup / 60;
        int secondsInThisMinute = secondsSinceStartup % 60;
        int markerIndexToBeUpdated = markers.length - secondsInThisMinute;
        if (markerIndexToBeUpdated >= 60) {
            markerIndexToBeUpdated = 0;
        }
        if (minutesSinceStartup % 2 == 0) {
            markers[markerIndexToBeUpdated].setColor(MARKER_PAST_COLOR);
        } else {
            markers[markerIndexToBeUpdated].setColor(MARKER_COLOR);
        }
    }

    private void drawMarkers(Circle[] markers) {
        for (Circle circle : markers) {
            circle.draw();
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("TimerApp");
    }
}
