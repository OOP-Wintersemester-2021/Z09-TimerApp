import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
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

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("TimerApp");
    }
}
