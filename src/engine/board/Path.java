package engine.board;

public class Path {
    private final Intersection sourceIntersection;
    private final Intersection destinationIntersection;

    public Path(Intersection sourceIntersection, Intersection destinationIntersection) {
        this.sourceIntersection = sourceIntersection;
        this.destinationIntersection = destinationIntersection;
    }
    public Intersection getSourceIntersection() {
        return sourceIntersection;
    }

    public Intersection getDestinationIntersection() {
        return destinationIntersection;
    }
}
