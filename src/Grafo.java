import java.util.*;

public class Grafo {
    private final Map<String, No> nos = new LinkedHashMap<>();
    private final List<Aresta> arestas = new ArrayList<>();

    public Grafo() {
        montarMapa();
    }

    public Collection<No> getNos() {
        return nos.values();
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public No getNo(String nome) {
        return nos.get(nome);
    }

    private void no(String nome, int x, int y) {
        nos.put(nome, new No(nome, x, y));
    }


    private void via(String a, String b, boolean maoDupla) {
        arestas.add(new Aresta(nos.get(a), nos.get(b)));
        if (maoDupla) arestas.add(new Aresta(nos.get(b), nos.get(a)));
    }

    private void montarMapa() {
        // Coordenadas (coluna, linha) na grade de 7 x 5 unidades
        no("A", 0, 0); no("B", 1, 0); no("C", 4, 0); no("D", 5, 0); no("E", 7, 0);
        no("F", 0, 2); no("G", 1, 2); no("H", 4, 2); no("I", 7, 2);
        no("J", 1, 3); no("K", 3, 3); no("L", 4, 3); no("M", 5, 3); no("N", 7, 3);
        no("O", 4, 4); no("P", 7, 4);
        no("Q", 0, 5); no("R", 1, 5); no("S", 3, 5); no("T", 4, 5); no("U", 7, 5);

        // Linha superior (mão dupla)
        via("A", "B", true); via("B", "C", true); via("C", "D", true); via("D", "E", true);
        // Borda esquerda (mão dupla)
        via("A", "F", true); via("F", "Q", true);
        // Linha de F a H (mão dupla)
        via("F", "G", true); via("G", "H", true);
        // Coluna de B a R (mão única, para baixo)
        via("B", "G", false); via("G", "J", false); via("J", "R", false);
        // Coluna de C a T (mão única, para cima)
        via("H", "C", false); via("L", "H", false); via("O", "L", false); via("T", "O", false);
        // Colunas de mão dupla
        via("K", "S", true);
        via("D", "M", true);
        // Coluna da direita (mão única, para baixo)
        via("E", "I", false); via("I", "N", false); via("N", "P", false); via("P", "U", false);
        // Linha J-N (mão única, para a esquerda)
        via("N", "M", false); via("M", "L", false); via("L", "K", false); via("K", "J", false);
        // Linha O-P (mão única, para a esquerda)
        via("P", "O", false);
        // Linha inferior
        via("Q", "R", false); via("R", "S", false); via("S", "T", false); via("T", "U", true);
    }
}
