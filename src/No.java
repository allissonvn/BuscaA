public class No {
    public final String nome;
    public final int x, y; // coordenadas em unidades (U) da grade

    public No(String nome, int x, int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return nome;
    }
}
