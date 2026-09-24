import java.util.*;

public class BuscaAEstrela {

    private static class Entrada {
        final No no;
        final double f;
        Entrada(No no, double f) { this.no = no; this.f = f; }
    }

    public static Resultado buscar(Grafo grafo, No origem, No destino, Heuristica h) {
        Map<No, Double> g = new HashMap<>();
        Map<No, Aresta> veioDe = new HashMap<>();
        Set<No> fechada = new HashSet<>();
        PriorityQueue<Entrada> aberta = new PriorityQueue<>(Comparator.comparingDouble(e -> e.f));
        Resultado r = new Resultado();

        g.put(origem, 0.0);
        aberta.add(new Entrada(origem, h.calcular(origem, destino)));

        while (!aberta.isEmpty()) {
            No atual = aberta.poll().no;
            if (fechada.contains(atual)) continue;
            fechada.add(atual);
            r.expandidos++;

            if (atual == destino) {
                LinkedList<No> caminho = new LinkedList<>();
                for (No n = destino; n != null; ) {
                    caminho.addFirst(n);
                    Aresta a = veioDe.get(n);
                    if (a != null) r.selecionadas.add(a);
                    n = (a == null) ? null : a.de;
                }
                r.caminho = caminho;
                for (No n : caminho) r.acumulado.add(g.get(n));
                return r;
            }

            for (Aresta a : grafo.getArestas()) {
                if (a.de != atual || fechada.contains(a.para)) continue;
                r.testadas.add(a);
                double novoG = g.get(atual) + a.custo;
                if (novoG < g.getOrDefault(a.para, Double.MAX_VALUE)) {
                    g.put(a.para, novoG);
                    veioDe.put(a.para, a);
                    aberta.add(new Entrada(a.para, novoG + h.calcular(a.para, destino)));
                }
            }
        }
        return null;
    }
}
