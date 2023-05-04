package engine.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Intersection getOtherIntersection(Intersection intersection) {
        if (intersection == sourceIntersection) return destinationIntersection;
        if (intersection == destinationIntersection) return sourceIntersection;
        return null;
    }
}
