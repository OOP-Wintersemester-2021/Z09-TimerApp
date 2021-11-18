import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.graphics.Label;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

/**
 * In diesem Beispiel wird eine Eieruhr implementiert, die kontinuierlich den Ablauf des Zeitraums
 * von 60 Sekunden visualisiert. Dazu werden 60 kreisförmige Markierungen auf
 * einer um den Mittelpunkt der Zeichenfläche angeordneten Kreisbahn dargestellt. Im Sekundentakt
 * werden, beginnen beim obersten Kreis und entgegen dem Uhrzeigersinn, immer mehr Markierungen neu eingefärbt.
 * Die Farbe der Markierungen wechselt dabei von initial gelb zu rot. Zu Beginn und dann wieder nach den vollen
 * 60 Sekunden sind alle Markierungen in derselben Farbe eingefärbt. Die Animation beginnt dann erneut mit dem
 * sekündlichen Umschalten der Farben (jetzt von rot zu gelb) der Markierungen.
 */
public class TimerApp extends GraphicsApp {

    /*
     * Farben für verschiedene Teile der sichtbaren Elemente
     *
     * RED und YELLOW: Markierungen der "Uhr"
     * CREAM: Schriftfarbe für Label mit Titel der Anwendung
     * GREY: Hintergrundfarbe
     */
    private static final Color RED = new Color(234, 49, 63); // "Selbstgemischter" RGB-Farbe (rot)
    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color CREAM = new Color(241, 255, 250); // "Selbstgemischter" RGB-Farbe (creme)
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)
    // Gewünschte Breite des Anwendungsfensters
    private static final int WINDOW_WIDTH = 600;
    // Gewünschte Höhe des Anwendungsfensters
    private static final int WINDOW_HEIGHT = 600;
    // Hintergrundfarbe
    private static final Color BACKGROUND_COLOR = GREY;
    // Radius der Kreisbahn, auf der die Markierungen platziert werden
    private static final int CLOCK_RADIUS = 250;
    // Anzahl der Marker, hier: ein Marker pro Sekunde
    private static final int NUMBER_OF_MARKERS = 60;
    // Radius der Marker
    private static final int MARKER_RADIUS = 7;
    // Radius der Marker auf den Positionen 3, 6, 9 und 12
    private static final int QUARTER_MARKER_RADIUS = 10;
    // Farbe für die Marker
    private static final Color MARKER_COLOR = YELLOW;
    // Alternative Farbe für bereits passierte Marker
    private static final Color MARKER_PAST_COLOR = RED;
    // Text, der als Anwendungstitel im Fenster angezeigt werden soll
    private static final String APPLICATION_LABEL_TEXT = "60 Seconds Timer";
    // Schriftart für den Anwendungstitel (Achtung: Die Schriftart muss auf dem Rechner verfügbar sein)
    private static final String APPLICATION_LABEL_FONT = "Arial Rounded MT Bold";
    // Schriftgröße für den Anwendungstitel
    private static final int APPLICATION_LABEL_FONT_SIZE = 16;
    // Schriftfarbe für den Anwendungstitel
    private static final Color APPLICATION_LABEL_COLOR = CREAM;

    // Array mit allen Markierungen
    private Circle[] markers;
    // Label zur Darstellung des Anwendungstitels unten links auf der Zeichenfläche
    private Label applicationLabel;
    // Zeitpunkt (in Millisekunden sei 01/01/1970) an dem die Anwendung gestartet wurde
    private double startupTimeInMs;

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        markers = createClockMarkers();
        applicationLabel = createApplicationLabel();
        // Misst die aktuelle Zeit und speichert diese in einer Variable auf Klassenebene
        startupTimeInMs = System.currentTimeMillis();
    }

    /**
     * Die Methode erstellt ein Label mit dem Text aus der Konstanten "APPLICATION_LABEL_TEXT" und platziert
     * dieses unten links auf der Zeichenfläche. Schriftgröße und -farbe werden über weitere Konstanten bestimmt.
     * Mithilfe der wahrscheinlichen Höhe des Labels (basierend auf dem anzuzeigenden Text) wird das
     * Label im gleichen Abstand rechts von bzw. über der linken, unteren Ecke der Zeichenfläche positioniert.
     *
     * @return Das erstellte Label
     */
    private Label createApplicationLabel() {
        // Erstellt das Label (noch nicht an der finalen Position)
        Label  label = new Label(0, 0, APPLICATION_LABEL_TEXT, APPLICATION_LABEL_COLOR);
        // Setzt die Schriftart (Der übergebenen Schriftname muss zu einer verfügbaren Schriftart passen)
        label.setFont(APPLICATION_LABEL_FONT);
        // Setzt die Schriftgröße in Punkten
        label.setFontSize(APPLICATION_LABEL_FONT_SIZE);
        /*
         * Basierend auf dem aktuellen Text und der aktuellen Schriftgröße und -art kann eine wahrscheinliche
         * Höhe berechnet werden. Diese wird hier und in der nächsten Zeile genutzt, um das neue Label auf
         * der x- und y-Achse im identischen Abstand vom linken bzw. unteren Rand der Zeichenfläche zu positionieren.
         */
        label.setXPos(label.getHeightEstimate());
        label.setYPos(getHeight() - label.getHeightEstimate());
        return label;
    }

    /**
     * Erstellt ein Array mit 60 Markern zur Visualisierung der einzelnen Sekunden. Die Marker werden
     * als Kreise dargestellt, die auf einer gedachten Kreisbahn um den Mittelpunkt der Zeichenfläche
     * herum angeordnet werden. Der Abstand zwischen den Markierungen wird gleichmäßig verteilt.
     *
     * @return Das Array mit den erstellten Markierungen
     */
    private Circle[] createClockMarkers() {
        // Erstellen des (leeren) Arrays mit genügen Platz für alle Marker
        Circle[] clockMarkers = new Circle[NUMBER_OF_MARKERS];
        // Berechnung des Mittelpunkts der Zeichenfläche
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        /*
         * 0° auf der gedachten Kreisbahn entsprechen der 3-Uhr-Position. Wir beginnen genau 90° "früher",
         * um den ersten Kreis auf der 12-Uhr-Position zu platzieren.
         */
        int initialMarkerDegree = -90;
        // Wir iterieren mit der for-Schleife über alle möglichen Positionen des Arrays
        for (int i = 0; i < clockMarkers.length; i++) {
            // Wir bestimmen die Position des neuen Markers auf der Kreisbahn ...
            double markerX = centerX + (CLOCK_RADIUS * Math.cos(Math.toRadians(initialMarkerDegree)));
            double markerY = centerY + (CLOCK_RADIUS * Math.sin(Math.toRadians(initialMarkerDegree)));
            /*
             * ... und legen dessen Radius fest. Falls wir und auf der 3-, 6-, 9- oder 12-Uhr-Position
             * befinden, verwenden wir für den aktuellen Marker einen anderen, größeren Radius.
             */
            int markerRadius = MARKER_RADIUS;
            if (i % 15 == 0) {
                markerRadius = QUARTER_MARKER_RADIUS;
            }
            // Wir erstellen den neuen Marker und speichern ihn im Array
            clockMarkers[i] = new Circle((float) markerX, (float) markerY, markerRadius, MARKER_COLOR);
            /*
             * Vor der nächsten Iteration verschieben wir die Position des nächsten Markers um die richtige
             * Anzahl an Grad. Dazu berechnen wir den Abstand zwischen zwei Kreisen, in dem wir die vollen
             * Grad eines Kreises (360) durch die Anzahl der notwendigen Markierungen teilen.
             */
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
        // Wir berechnen die Anzahl der Sekunden, die seit dem Starten der Anwendung vergangen sind ...
        int secondsSinceStart = getSecondsSinceStart(startupTimeInMs);
        // ... und aktualisieren mithilfe dieser Information die notwendigen Markierungen der Uhr.
        updateMarkers(markers, secondsSinceStart);
        // Im Anschluss werden alle Marker gezeichnet.
        drawMarkers(markers);
    }

    /**
     * Die Methode gibt die zeitliche Differenz in Sekunden zwischen dem übergebenen Wert und dem
     * aktuellen Zeitpunkt zurück.
     *
     * @param startupTimeInMs Zeitangabe in Millisekunden seit 01/01/1970
     * @return Zeit in Sekunden, die seit dem im Parameter angegebenen Zeitpunkt verstrichen sind
     */
    private int getSecondsSinceStart(double startupTimeInMs) {
        double deltaTime = (System.currentTimeMillis() - startupTimeInMs) / 1000;
        return (int) deltaTime;
    }

    /**
     * Über diese Methode wird die Farbe von maximal einem der Kreise im übergebenen Array
     * geändert. Auf Basis der Sekundenangabe im zweiten Parameter wird, ausgehend vom letzten
     * Eintrag des Arrays, der korrespondierende Eintrag festgestellt. Dazu wird von der
     * Gesamtzahl der Sekunden seit Anwendungsstart die Sekunde in der aktuell laufenden Minute
     * bestimmt. Der ausgewählte Kreis repräsentiert diese Sekunde als die n-te in der laufenden
     * Minute verstrichene Sekunde. Aller Kreise an den davorliegenden Stellen des Arrays
     * repräsentieren noch nicht verstrichene Sekunden.
     *
     * Über die Anzahl der verstrichenen Minuten wird bestimmt mit welcher Farbe der ausgewählte Kreis
     * eingefärbt werden soll: Nach jeder Minute wird die Farbe gewechselt.
     *
     * @param markers Array mit allen Markierungen der Uhr
     * @param secondsSinceStartup Anzahl der Sekunden seit Anwendungsstart
     */
    private void updateMarkers(Circle[] markers, int secondsSinceStartup) {
        /*
         * Wenn seit Anwendungsstart noch keine volle Sekunde verstrichen ist, müssen keine
         * Markierungen geändert werden. Die Methode wird direkt abgebrochen (Vgl. Early Return)
         */
        if (secondsSinceStartup < 0) {
            return;
        }
        // Über verschiedene Formen der Integer-Division extrahieren wir Sekunden- und Minutenangaben
        int minutesSinceStartup = secondsSinceStartup / 60;
        int secondsInThisMinute = secondsSinceStartup % 60;
        // Auf Basis der in der aktuellen Minute verstrichenen Sekunden wir der Index der passenden Markierung ausgewählt
        int markerIndexToBeUpdated = markers.length - secondsInThisMinute;
        /*
         * Hier behandeln wir den besonderen Fall der Eintritt, wenn in der aktuellen Minute noch keine Sekunde
         * verstrichen ist. Der berechnete Index wäre in diesem Fall 60 (Länge des Arrays minus 0) und würde beim
         * Zugriff einen "IndexOutOfBounds"-Fehler verursachen. Wir wählen stattdessen das erste Element des Arrays aus.
         */
        if (markerIndexToBeUpdated == 60) {
            markerIndexToBeUpdated = 0;
        }
        /*
         * Wir wechseln mit jeder verstrichenen Minute die Farbe, die zum Markieren der Kreise verwendet wird und verwenden
         * dafür den Modulo-Operator. Jeder Kreis wird innerhalb von einer Minute damit abwechselnd erst Rot und dann
         * Gelb eingefärbt.
         */
        if (minutesSinceStartup % 2 == 0) {
            markers[markerIndexToBeUpdated].setColor(MARKER_PAST_COLOR);
        } else {
            markers[markerIndexToBeUpdated].setColor(MARKER_COLOR);
        }
    }

    private void drawMarkers(Circle[] markers) {
        /*
         * Hier müssen alle Markierungen gezeichnet werden. Wir verwenden die for-each-Schleife für den schnellen
         * Zugriff auf Element des Arrays.
         */
        for (Circle circle : markers) {
            circle.draw();
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("TimerApp");
    }
}
