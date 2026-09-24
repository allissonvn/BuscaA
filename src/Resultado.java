import java.util.*;

public class Resultado {
    public List<No> caminho = new ArrayList<>();
    public List<Double> acumulado = new ArrayList<>(); // g de cada nó do caminho
    public Set<Aresta> selecionadas = new HashSet<>();
    public List<Aresta> testadas = new ArrayList<>();
    public int expandidos;
}
