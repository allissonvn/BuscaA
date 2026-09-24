public enum Heuristica {
    MANHATTAN("Distância de Manhattan") {
        public double calcular(No a, No b) {
            return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        }
    },
    EUCLIDIANA("Distância Euclidiana") {
        public double calcular(No a, No b) {
            return Math.hypot(a.x - b.x, a.y - b.y);
        }
    },
    CHEBYSHEV("Distância de Chebyshev") {
        public double calcular(No a, No b) {
            return Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
        }
    };

    private final String rotulo;

    Heuristica(String rotulo) {
        this.rotulo = rotulo;
    }

    public abstract double calcular(No a, No b);

    @Override
    public String toString() {
        return rotulo;
    }
}
