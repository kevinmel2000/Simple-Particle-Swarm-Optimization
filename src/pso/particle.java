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
    int[] particleVector;
    double fitness;
    private double mVelocity = 0.0;
    
    public particle(int nSolution){
        particleVector= new int[nSolution];
         this.mVelocity = 0.0;
    }
    
    public void setParticle(int index, int solution){
        particleVector[index]= solution;
    }
    
    public void setFitness(double fitness){
        this.fitness= fitness;
    }
    
    public int getParticleVector(int index){
        return particleVector[index];
    }
    
    public double getFitness(){
        return fitness;
    }
     public double velocity()
     {
            return this.mVelocity;
     }
        
    public void velocity(double velocityScore)
     {
           this.mVelocity = velocityScore;
         
     }
    
    
    
}
