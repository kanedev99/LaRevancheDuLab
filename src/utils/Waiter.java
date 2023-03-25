package utils;

/**
 * Class Waiter, appel une methode void() après un certain temps sans mettre
 * l'application en pause
 * 
 * @author Brian Normant
 **/
public class Waiter implements Runnable {
    /** Le temps à attendre avant de mettre lancer le callback */
    private int time; // The time to wait
    /** La méthode à appeler */
    private Callback callback; // The method to call

    /**
     * Constructeur de waiteur
     * 
     * @param time     le temps
     * @param callback la méthode
     */
    public Waiter(int time, Callback callback) {
        this.time = time;
        this.callback = callback;
    }

    /**
     * démarre le chronomêtre
     */
    public void fire() {
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * méthode du thread pour attendre de façon asyncrone
     */
    public void run() {
        try {
            Thread.sleep(time);
        } catch (Exception ignored) {
        } finally {
            callback.method();
        }
    }
}
