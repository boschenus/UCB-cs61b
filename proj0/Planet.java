public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static final double g = 6.67e-11;
    public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
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

    public double calcDistance(Planet p) {
        double dis = Math.sqrt(Math.pow(xxPos - p.xxPos, 2) + Math.pow(yyPos - p.yyPos, 2));
        return dis;
    }
    
    public double calcForceExertedBy(Planet p) {
        double forceByp = Planet.g * mass * p.mass / Math.pow(this.calcDistance(p), 2);
        return forceByp;
    }

    public double calcForceExertedByX(Planet p) {
        double forceByp_x = this.calcForceExertedBy(p)* (p.xxPos - xxPos) / this.calcDistance(p);
        return forceByp_x;
    }

    public double calcForceExertedByY(Planet p) {
        double forceByp_y = this.calcForceExertedBy(p)* (p.yyPos - yyPos) / this.calcDistance(p);
        return forceByp_y;
    }

    public double calcNetForceExertedByX(Planet[] all) {
        if (all.length == 0) {
            return 0;
        }

        double netF_X = 0;
        for (Planet p: all) {
            if (this.equals(p)) {
                continue;
            }
            netF_X = netF_X + this.calcForceExertedByX(p);
        }
        return netF_X;
    }
    

    public double calcNetForceExertedByY(Planet[] all) {
        if (all.length == 0) {
            return 0;
        }
        double netF_Y = 0;
        for (Planet p: all) {
            if (this.equals(p)) {
                continue;
            }
            netF_Y = netF_Y + this.calcForceExertedByY(p);
        }
        return netF_Y;
    }
    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;

        xxVel = xxVel + aX * dt;
        yyVel = yyVel + aY * dt;

        xxPos = xxPos + xxVel * dt;
        yyPos = yyPos + yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}


