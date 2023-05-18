package model.board;

public class Path {
    private final Intersection sourceIntersection;
    private final Intersection destinationIntersection;

    public Path(Intersection sourceIntersection, Intersection destinationIntersection) {
        this.sourceIntersection = sourceIntersection;
        this.destinationIntersection = destinationIntersection;
    }

    /**
     * Returns the source intersection of the path
     * @return the source intersection
     */
    public Intersection getSourceIntersection() {
        return sourceIntersection;
    }

    /**
     * Returns the destination intersection of the path
     * @return the destination intersection
     */
    public Intersection getDestinationIntersection() {
        return destinationIntersection;
    }

    /**
     * Returns the other intersection of the path given one of the intersections
     * @param intersection the intersection to get the other intersection of
     * @return the other intersection
     */
    public Intersection getOtherIntersection(Intersection intersection) {
        if (intersection == sourceIntersection) return destinationIntersection;
        if (intersection == destinationIntersection) return sourceIntersection;
        return null;
    }
}
