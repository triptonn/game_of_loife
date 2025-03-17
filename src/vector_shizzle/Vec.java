package vector_shizzle;

public class Vec {
    private final int n;
    public double[] data;

    public Vec() {
        n = 2;
        data = new double[n];
    };

    public Vec(int n) {
        this.n = n;
        this.data = new double[n];
    }

    public Vec(double x, double y) {
        n = 2;
        this.data = new double[n];
        this.data[0] = x;
        this.data[1] = y;
    }

    public Vec(double[] data) {
        n = data.length;

        this.data = new double[n];
        for (int i = 0; i < n; i++) {
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
