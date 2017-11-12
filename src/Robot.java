import jbotsim.Topology;
import jbotsim.ui.JViewer;
import jbotsim.Node;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;


public class Robot extends Node{

    ArrayList<Point2D> locations;
    Point2D target;

    static double EPS = 0.000001;

    @Override
    public void onPreClock() {
        locations = new ArrayList<Point2D>();
        for (Node node : getTopology().getNodes() )
        {
            locations.add(node.getLocation());
        }
    }

    @Override
    public void onClock(){
        target = locations.get(3);
        for(Point2D r : locations)
        {
            if(r.getX() > target.getX() || (r.getX() == target.getX() && r.getY() > target.getY()))
            {
                target = r;
            }
        }
    }

    public void onPostClock(){
        setDirection(target);
        move(Math.min(10, distance(target)));
    }


    // Start the simulation
    public static void main(String[] args){

        // Create the Topology (a plane of size 800x400)
        Topology tp = new Topology(1000, 400);
        // Create the simulation window
        new JViewer(tp);

        // set the default node to be our Robot class
        // (When the user click in the simulation window,
        //  a default node is automatically added to the topology)
        tp.setDefaultNodeModel(Robot.class);

        // Robots cannot communicate
        tp.disableWireless();

        // Here we remove the sensing range since the robots have unlimited visibility
        tp.setSensingRange(0);

        // Add 20 Robots to the topology (with random positions)
        for (int i = 0; i < 20; i++)
            tp.addNode(-1,-1);

        //The clock click every 0.5 sec (so that you can see the evolution slowly)
        tp.setClockSpeed(500);

        // We pause the simulation
        // (to start it, you'll have to right click on the window and resume it)
        tp.pause();
    }
}