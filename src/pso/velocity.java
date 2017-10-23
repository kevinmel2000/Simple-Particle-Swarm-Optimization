/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso;

/**
 *
 * @author Nanda-PC
 */
public class velocity {
    double[]v;
    
    public velocity(int nSolution ){
        v = new double [nSolution];
    }
    
    
    
    public void setVelocity(int index, double velo){
        v[index]= velo;
    }
    
    public double getVelocity(int index){
        return v[index];
    }
}
