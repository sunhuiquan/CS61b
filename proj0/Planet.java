public class Planet {
    // the position of x and y
    public double xxPos;
    public double yyPos;
    // the velocity of x and y
    public double xxVel;
    public double yyVel;
    // the mass of planet
    public double mass;
    // the img File Name
    public String imgFileName;

    private static double numberG = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet otherPlanet) {
        double dx = xxPos - otherPlanet.xxPos;
        double dy = yyPos - otherPlanet.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet other) {
        // use 'e' to simplify and speed up our program
        double distance = this.calcDistance(other);
        return numberG * mass * other.mass / (distance * distance);
    }

    public double calcForceExertedByX(Planet other) {
        double distance = this.calcDistance(other);
        double dx = other.xxPos - xxPos;
        return this.calcForceExertedBy(other) * dx / distance;
    }

    public double calcForceExertedByY(Planet other) {
        double distance = this.calcDistance(other);
        double dy = other.yyPos - yyPos;
        return this.calcForceExertedBy(other) * dy / distance;
    }

    public double calcNetForceExertedByX(Planet[] all) {
        double totalForceX = 0;
        for (Planet p : all)
            if (p.equals(this) == false)
                totalForceX += this.calcForceExertedByX(p);
        return totalForceX;
    }

    public double calcNetForceExertedByY(Planet[] all) {
        double totalForceY = 0;
        for (Planet p : all)
            if (p.equals(this) == false)
                totalForceY += this.calcForceExertedByY(p);
        return totalForceY;
    }

    public void update(double dt, double fx, double fy) {
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
