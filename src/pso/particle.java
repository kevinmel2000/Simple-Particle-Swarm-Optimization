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
public class particle {
    double[] particleVector;
    double fitness;
    
    public particle(int nSolution){
        particleVector= new double[nSolution];
    }
    
    public void setParticle(int index, double solution){
        particleVector[index]= solution;
    }
    
    public void setFitness(double fitness){
        this.fitness= fitness;
    }
    
    public double getParticleVector(int index){
        return particleVector[index];
    }
    
    public double getFitness(){
        return fitness;
    }
    
    
}
