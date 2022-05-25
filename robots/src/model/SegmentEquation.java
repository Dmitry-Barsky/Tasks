package model;


import java.util.Objects;

// Ax + By = C
class SegmentEquation {

    private final int A;
    private final int B;
    private final int C;

    private final Point S;
    private final Point F;

    public SegmentEquation(Point f, Point s)
    {
        this.A = s.y - f.y;
        this.B = f.x - s.x;
        this.C = -s.x * f.y + s.y * f.x;

        S = new Point(Math.min(f.x, s.x), Math.min(f.y, s.y));
        F = new Point(Math.max(f.x, s.x), Math.max(f.y, s.y));
    }

    /*
    A1x + B1y = C1
    A2x + B2y = C2

    A2x + B2 * (C1 - A1x)/B1 = C2

    x(A2 - B2 * A1 /B1) = C2 - B2/B1 * C1

    x = (B1 * C2 - B2 * C1) /
        (B1 * A2 - B2 * A1)

    A2 * (C1 - B1y)/A1 + B2y = C2

    y (B2 - A2/A1 * B1) = C2 - A2/A1 * C1

    y = (C2 * A1 - C1 * A2) /
        (B2 * A1 - A2 * B1)
     */

    public  boolean isParallel(SegmentEquation other) {
        return this.B * other.A - this.A - other.B == 0;
    }



    public boolean hasIntersection(SegmentEquation other) {
        if (isParallel(other))
            return false;
        var denum =  this.B * other.A - this.A - other.B;
        var intersection =  new Point(
                (this.B * other.C - other.B * this.C),
                (this.C * other.A - other.C * this.A)
        );

        if (denum > 0) {
            return ((denum * S.x <= intersection.x && intersection.x <= denum * F.x) &&
                    (denum * S.y <= intersection.y && intersection.y <= denum * F.y));
        }
        else
        {
            return ((denum * F.x <= intersection.x && intersection.x <= denum * S.x) &&
                    (denum * F.y <= intersection.y && intersection.y <= denum * S.y));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentEquation that = (SegmentEquation) o;
        return that.A == A && that.B == B && that.C == C;
    }

    @Override
    public int hashCode() {
        return Objects.hash(A, B, C);
    }
}
