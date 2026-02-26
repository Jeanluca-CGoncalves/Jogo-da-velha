import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JogoDaVelhaGUI jogo = new JogoDaVelhaGUI();
            jogo.setVisible(true);
        });
    }
}