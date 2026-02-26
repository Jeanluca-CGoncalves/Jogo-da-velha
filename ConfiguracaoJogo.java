import javax.swing.*;
import java.awt.*;

public class ConfiguracaoJogo {
    private JogoDaVelhaGUI jogo;
    private JPanel menuPanel;
    private JComboBox<String> inicioCombo;
    private JSpinner dificuldadeSpinner;
    private JComboBox<String> estrategiaCombo;

    private final Color COR_FUNDO = new Color(34, 34, 34);
    private final Color COR_TEXTO = Color.WHITE;
    private final Color COR_AZUL = new Color(54, 162, 235);
    private final Color COR_VERDE = new Color(99, 255, 132);
    private final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 28);
    private final Font FONTE_TEXTO = new Font("SansSerif", Font.PLAIN, 16);

    public ConfiguracaoJogo(JogoDaVelhaGUI jogo) {
        this.jogo = jogo;
    }

    public void mostrarMenuInicial() {
        jogo.getContentPane().removeAll();

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        menuPanel.setBackground(COR_FUNDO);

        JLabel tituloLabel = new JLabel("🎮 Jogo da Velha", SwingConstants.CENTER);
        tituloLabel.setFont(FONTE_TITULO);
        tituloLabel.setForeground(COR_VERDE);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inicioPanel = criarPainelOpcoes("👾 Quem começa:", new String[]{"❌ Jogador (X)", "🤖 IA (O)"});
        inicioCombo = (JComboBox<String>) inicioPanel.getComponent(1);

        JPanel dificuldadePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        dificuldadePanel.setBackground(COR_FUNDO);
        JLabel dificuldadeLabel = new JLabel("⚙️ Dificuldade (1-9):");
        dificuldadeLabel.setFont(FONTE_TEXTO);
        dificuldadeLabel.setForeground(COR_TEXTO);
        SpinnerModel model = new SpinnerNumberModel(1, 1, 9, 1);
        dificuldadeSpinner = new JSpinner(model);
        JFormattedTextField spinnerText = ((JSpinner.DefaultEditor) dificuldadeSpinner.getEditor()).getTextField();
        spinnerText.setBackground(Color.BLACK);
        spinnerText.setForeground(COR_AZUL);
        spinnerText.setFont(FONTE_TEXTO);
        spinnerText.setBorder(BorderFactory.createLineBorder(COR_AZUL));
        dificuldadePanel.add(dificuldadeLabel);
        dificuldadePanel.add(dificuldadeSpinner);

        JPanel estrategiaPanel = criarPainelOpcoes("🧠 Estratégia da IA:", new String[]{"Minimax", "Heurística"});
        estrategiaCombo = (JComboBox<String>) estrategiaPanel.getComponent(1);

        JButton iniciarButton = new JButton("🚀 Iniciar Jogo");
        iniciarButton.setFont(FONTE_TEXTO);
        iniciarButton.setBackground(COR_VERDE);
        iniciarButton.setForeground(Color.BLACK);
        iniciarButton.setFocusPainted(false);
        iniciarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iniciarButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        iniciarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        iniciarButton.addActionListener(e -> iniciarJogo());

        // Montagem do menu
        menuPanel.add(tituloLabel);
        menuPanel.add(Box.createVerticalStrut(30));
        menuPanel.add(inicioPanel);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(dificuldadePanel);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(estrategiaPanel);
        menuPanel.add(Box.createVerticalStrut(30));
        menuPanel.add(iniciarButton);

        jogo.add(menuPanel);
        jogo.revalidate();
        jogo.repaint();
    }

    private JPanel criarPainelOpcoes(String texto, String[] opcoes) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painel.setBackground(COR_FUNDO);
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_TEXTO);
        label.setForeground(COR_TEXTO);
        JComboBox<String> combo = new JComboBox<>(opcoes);
        combo.setBackground(Color.BLACK);
        combo.setForeground(COR_VERDE);
        combo.setFont(FONTE_TEXTO);
        combo.setFocusable(false);
        combo.setBorder(BorderFactory.createLineBorder(COR_VERDE));
        painel.add(label);
        painel.add(combo);
        return painel;
    }

    private void iniciarJogo() {
        boolean humanoComeca = inicioCombo.getSelectedIndex() == 0;
        int dificuldade = (int) dificuldadeSpinner.getValue();
        String estrategia = (String) estrategiaCombo.getSelectedItem(); // "Minimax" ou "Heurística"
        jogo.iniciarJogo(humanoComeca, dificuldade, estrategia);
    }
}
