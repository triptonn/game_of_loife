package data;

public class Vec {
    private final int n;
    public double[] data;

    public Vec() {
        this.n = 2;
        this.data = new double[n];
    };

    public Vec(int n) {
        this.n = n;
        this.data = new double[n];
    }

    public Vec(double x, double y) {
        this.n = 2;
        this.data = new double[n];
        this.data[0] = x;
        this.data[1] = y;
    }

    public Vec(double x, double y, double z) {
        this.n = 3;
        this.data = new double[n];
        this.data[0] = x;
        this.data[1] = y;
        this.data[2] = z;
    }

    public Vec(Vec original) {
        this.n = original.length();

        this.data = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            this.data[i] = original.data[i];
        }
    }

    public Vec(double[] data) {
        n = data.length;

        this.data = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            this.data[i] = data[i];
        }
    }

    public double x() {
        return this.data[0];
    }

    public double y() {
        return this.data[1];
    }

    public int length() {
        return n;
    }

    public double dot(Vec that) {
        if (this.length() != that.length()) {
            throw new IllegalArgumentException("dimensions disagree");
        }
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum = sum + (this.data[i] * that.data[i]);
        }
        return sum;
    }

    public Vec cross3(Vec a, Vec b) {
        if (a.length() == 3 && b.length() == 3) {
            Vec axb = new Vec(3);
            axb.data[0] = a.data[1] * b.data[2] - a.data[2] * b.data[1];
            axb.data[1] = a.data[2] * b.data[0] - a.data[0] * b.data[2];
            axb.data[2] = a.data[0] * b.data[1] - a.data[1] * b.data[0];

            return axb;
        } else {
            throw new IllegalArgumentException("Vec must be Vec3");
        }
    }

    public double mag() {
        return Math.sqrt(this.dot(this));
    }

    public double distanceTo(Vec that) {
        if (this.length() != that.length()) {
            throw new IllegalArgumentException("dimensions disagree");
        }
        return this.minus(that).mag();
    }

    public Vec plus(Vec that) {
        if (this.length() != that.length()) {
            throw new IllegalArgumentException("dimensions disagree");
        }
        Vec c = new Vec(this.length());
        for (int i = 0; i < n; i++) {
            c.data[i] = this.data[i] + that.data[i];
        }
        return c;
    }

    public Vec minus(Vec that) {
        if (this.length() != that.length()) {
            throw new IllegalArgumentException("dimensions disaggree");
        }
        Vec c = new Vec(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = this.data[i] - that.data[i];
        }
        return c;
    }

    public double cart(int i) {
        return data[i];
    }

    @Deprecated
    public Vec times(double factor) {
        Vec c = new Vec(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = factor * data[i];
        }
        return c;
    }

    public Vec scale(double factor) {
        Vec c = new Vec(n);
        for (int i = 0; i < n; i++) {
            c.data[i] = factor * data[i];
        }
        return c;
    }

    public Vec norm() {
        if (this.mag() == 0.0) {
            throw new ArithmeticException("zero-vector has no direction");
        }
        return this.scale(1.0 / this.mag());
    }

    public Vec negate() {
        Vec negated = new Vec(n);
        for (int i = 0; i < n; i++) {
            negated.data[i] = -1 * this.data[i];
            ;
        }
        return negated;
    }

    public Vec reflect2D(int axis) {
        if (this.n != 2) {
            return this;
        }
        Vec reflected = new Vec(n);
        if (axis == 0) {
            reflected.data[0] = this.data[0];
            reflected.data[1] = -this.data[1];
        } else {
            reflected.data[0] = -this.data[0];
            reflected.data[1] = this.data[1];
        }
        return reflected;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('(');
        for (int i = 0; i < n; i++) {
            s.append(data[i]);
            if (i < n - 1) {
                s.append(", ");
            }
        }
        s.append(')');
        return s.toString();
    }
}
