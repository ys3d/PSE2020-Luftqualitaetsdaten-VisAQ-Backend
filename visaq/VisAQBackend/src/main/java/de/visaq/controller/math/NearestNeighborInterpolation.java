package de.visaq.controller.math;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

/**
 * The class applies nearest neighbor interpolation to a discrete grid.
 *
 */
public class NearestNeighborInterpolation {
    
    /*
     * The default value if there if the coordinates are empty 
     * or the minimal distance of the grid point to a sensor is bigger than maxDistance.
     */
    public final double defaultValue = -999;
    
    public final Coordinate[] coordinates;
    
    public final double maxDistance;
    
    /**
     * Sole constructor of the class.
     * 
     * @param coordinates   contains geographic coordinates and measured values
     * @param maxDistance   The maximum distance
     */
    public NearestNeighborInterpolation(Coordinate[] coordinates, double maxDistance) {
        this.maxDistance = maxDistance;
        this.coordinates = coordinates;
    }
    
    /**
     * Projects the the measurements on a grid using nearest neighbor interpolation.
     * 
     * @param srcEnv    The area of the grid
     * @param x         The number of grid points along the x axis
     * @param y         The number of grid points along the y axis
     * @return  A grid of interpolated values
     */
    public double[][] computeSurface(Envelope srcEnv, int x, int y) {

        GridTransform trans = new GridTransform(srcEnv, x, y);
        
        double[][] grid = new double[x][y];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                double xcoord = trans.transformX(j);
                double ycoord = trans.transformY(i);
                
                grid[i][j] = getClosestCoordinateValue(xcoord, ycoord);
            }
        }
        return grid;
    }
    
    
    /*
     * Method finds the variable closest to the gridPoint.
     */
    private double getClosestCoordinateValue(double x, double y) {
              
        Coordinate p = new Coordinate(x, y);
        
        if (coordinates.length == 0)    {
            return defaultValue;
        }

        double minDistance = p.distance(coordinates[0]);
        int minDistanceIndex = 0;
        
        for (int i = 0; i < coordinates.length; i++) {
            
            double dist = p.distance(coordinates[i]);
            
            if (dist < minDistance)  {
                minDistance = dist;
                minDistanceIndex = i;
            }
        }
        /*
         * Checks if the minimal Distance is big enough
         */
        if (minDistance < maxDistance) {
            return coordinates[minDistanceIndex].getZ();
        } else {
            return defaultValue;
        }
    }
}
