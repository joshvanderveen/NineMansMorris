package engine.board;

public class Path {
    Intersection sourceIntersection;
    Intersection destinationIntersection;

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
