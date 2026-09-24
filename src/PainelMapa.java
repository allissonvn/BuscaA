import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.function.Supplier;


public class PainelMapa extends JPanel {

    public interface NoClickListener {
        void aoClicar(No n);
    }

    public static final int ESCALA = 80, MARGEM = 50, RAIO = 13;
    private static final Color AZUL = new Color(0, 90, 220);
    private static final Color AMARELO = new Color(245, 190, 0);
    private static final Color VERMELHO_VIA = new Color(210, 40, 40);

    private final Grafo grafo;
    private final Supplier<No> origemSupplier;
    private final Supplier<No> destinoSupplier;
    private final Supplier<Resultado> resultadoSupplier;

    public PainelMapa(Grafo grafo,
                       Supplier<No> origemSupplier,
                       Supplier<No> destinoSupplier,
                       Supplier<Resultado> resultadoSupplier,
                       NoClickListener listener) {
        this.grafo = grafo;
        this.origemSupplier = origemSupplier;
        this.destinoSupplier = destinoSupplier;
        this.resultadoSupplier = resultadoSupplier;

        setPreferredSize(new Dimension(7 * ESCALA + 2 * MARGEM, 5 * ESCALA + 2 * MARGEM));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (No n : grafo.getNos()) {
                    if (Point2D.distance(px(n), py(n), e.getX(), e.getY()) <= RAIO + 4) {
                        listener.aoClicar(n);
                        return;
                    }
                }
            }
        });
    }

    int px(No n) { return MARGEM + n.x * ESCALA; }
    int py(No n) { return MARGEM + n.y * ESCALA; }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        No origem = origemSupplier.get();
        No destino = destinoSupplier.get();
        Resultado resultado = resultadoSupplier.get();


        g.setColor(new Color(220, 220, 220));
        g.setStroke(new BasicStroke(1f));
        for (int i = 0; i <= 7; i++) g.drawLine(MARGEM + i * ESCALA, MARGEM, MARGEM + i * ESCALA, MARGEM + 5 * ESCALA);
        for (int j = 0; j <= 5; j++) g.drawLine(MARGEM, MARGEM + j * ESCALA, MARGEM + 7 * ESCALA, MARGEM + j * ESCALA);


        g.setStroke(new BasicStroke(2f));
        for (Aresta a : grafo.getArestas()) {
            g.setColor(VERMELHO_VIA);
            g.drawLine(px(a.de), py(a.de), px(a.para), py(a.para));
            if (!existeVolta(a)) seta(g, a, Color.DARK_GRAY, 0.5);
        }


        if (resultado != null) {
            g.setStroke(new BasicStroke(4f));
            g.setColor(AMARELO);
            for (Aresta a : resultado.testadas) {
                if (!resultado.selecionadas.contains(a))
                    g.drawLine(px(a.de), py(a.de), px(a.para), py(a.para));
            }

            g.setColor(AZUL);
            g.setStroke(new BasicStroke(5f));
            for (Aresta a : resultado.selecionadas)
                g.drawLine(px(a.de), py(a.de), px(a.para), py(a.para));
        }


        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        for (No n : grafo.getNos()) {
            Color fundo = Color.WHITE;
            if (n == origem) fundo = new Color(120, 220, 120);
            else if (n == destino) fundo = new Color(240, 130, 130);
            g.setColor(fundo);
            g.fillOval(px(n) - RAIO, py(n) - RAIO, 2 * RAIO, 2 * RAIO);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1.5f));
            g.drawOval(px(n) - RAIO, py(n) - RAIO, 2 * RAIO, 2 * RAIO);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(n.nome, px(n) - fm.stringWidth(n.nome) / 2, py(n) + fm.getAscent() / 2 - 1);
        }
    }

    private boolean existeVolta(Aresta a) {
        for (Aresta b : grafo.getArestas()) if (b.de == a.para && b.para == a.de) return true;
        return false;
    }

    private void seta(Graphics2D g, Aresta a, Color cor, double t) {
        double x1 = px(a.de), y1 = py(a.de), x2 = px(a.para), y2 = py(a.para);
        double mx = x1 + (x2 - x1) * t, my = y1 + (y2 - y1) * t;
        double ang = Math.atan2(y2 - y1, x2 - x1);
        int tam = 9;
        Path2D p = new Path2D.Double();
        p.moveTo(mx + tam * Math.cos(ang), my + tam * Math.sin(ang));
        p.lineTo(mx + tam * Math.cos(ang + 2.5), my + tam * Math.sin(ang + 2.5));
        p.lineTo(mx + tam * Math.cos(ang - 2.5), my + tam * Math.sin(ang - 2.5));
        p.closePath();
        g.setColor(cor);
        g.fill(p);
    }
}
