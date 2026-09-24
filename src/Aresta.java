public class Aresta {
    public final No de, para;
    public final int custo;

    public Aresta(No de, No para) {
        this.de = de;
        this.para = para;
        this.custo = Math.abs(de.x - para.x) + Math.abs(de.y - para.y);
    }
}
