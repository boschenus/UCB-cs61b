public class NBody {
    public static double readRadius(String s) {
        In in = new In(s);
        
        int n = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String s) {
        In in = new In(s);
        int n = in.readInt();
        Planet[] planet = new Planet[n];
        double radius = in.readDouble();
        for (int i=0; i<n; i+=1) {
            double pX = in.readDouble();
            double pY = in.readDouble();
            double vX = in.readDouble();
            double vY = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planet[i] = new Planet(pX, pY, vX, vY, m, img);
        }

        return planet;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        StdDraw.setScale(-radius, radius);
            StdDraw.clear();

            StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
            for (int i = 0; i<planets.length;i++) {
            planets[i].draw();
            }
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        double time = 0;
        while (time < T) {

            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i=0; i<planets.length;i++) {
                // Planet[] others = new Planet[planets.length-1];
                // for (int j=0, k =0; j<planets.length;j++) {
                //     if (i != j) {
                //         others[k] = planets[i];
                //         k+=1;
                //     }
                // } 
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            
            for (int i=0; i<planets.length;i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
            
            for (int i = 0; i<planets.length;i++) {
                planets[i].draw();
            }
            
            StdDraw.show();
            StdDraw.pause(10);
            time = time + dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }

}