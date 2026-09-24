import javax.swing.*;
import java.awt.*;

public class BuscaA extends JFrame {

    private final Grafo grafo = new Grafo();

    private No origem, destino;
    private Resultado resultado;

    private final JComboBox<Heuristica> comboHeuristica = new JComboBox<>(Heuristica.values());
    private final JTextArea saida = new JTextArea(6, 40);
    private final JLabel instrucao = new JLabel();
    private final PainelMapa painel = new PainelMapa(
            grafo, () -> origem, () -> destino, () -> resultado, this::aoClicar);

    public BuscaA() {
        super("Busca A* - Mapa da cidade virtual");

        JButton limpar = new JButton("Limpar seleção");
        limpar.addActionListener(e -> reiniciar());
        comboHeuristica.addActionListener(e -> executar());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Heurística:"));
        topo.add(comboHeuristica);
        topo.add(limpar);
        topo.add(instrucao);

        saida.setEditable(false);
        saida.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        add(topo, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);
        add(new JScrollPane(saida), BorderLayout.SOUTH);

        reiniciar();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void reiniciar() {
        origem = null;
        destino = null;
        resultado = null;
        saida.setText("");
        instrucao.setText("Clique no nó de ORIGEM.");
        painel.repaint();
    }

    private void executar() {
        if (origem == null || destino == null) return;
        Heuristica h = (Heuristica) comboHeuristica.getSelectedItem();
        resultado = BuscaAEstrela.buscar(grafo, origem, destino, h);

        StringBuilder sb = new StringBuilder();
        sb.append("Heurística: ").append(h).append("\n");
        if (resultado == null) {
            sb.append("Não há caminho de ").append(origem).append(" até ").append(destino).append(".");
        } else {
            for (int i = 0; i < resultado.caminho.size(); i++) {
                if (i == 0) sb.append(resultado.caminho.get(i));
                else sb.append(" -> ").append(resultado.caminho.get(i))
                       .append("(").append(formatar(resultado.acumulado.get(i))).append(")");
            }
            sb.append("\nCusto total: ").append(formatar(resultado.acumulado.get(resultado.acumulado.size() - 1)))
              .append(" U  |  Nós expandidos: ").append(resultado.expandidos);
        }
        saida.setText(sb.toString());
        painel.repaint();
    }

    private static String formatar(double v) {
        return v == Math.rint(v) ? String.valueOf((long) v) : String.format("%.2f", v);
    }

    private void aoClicar(No n) {
        if (n == null) return;
        if (origem == null) {
            origem = n;
            instrucao.setText("Agora clique no nó de DESTINO.");
        } else if (destino == null) {
            if (n == origem) return;
            destino = n;
            instrucao.setText("Clique em 'Limpar seleção' para nova busca.");
            executar();
        }
        painel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BuscaA().setVisible(true));
    }
}
