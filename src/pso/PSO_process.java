/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso;

import java.util.Random;

/**
 *
 * @author Nanda-PC
 */
public class PSO_process {
    int iteration,maxrange, minrange, population, nSolution, vmax, target;
    
    int currentBestIndex;
    particle[] Particle;
    particle[] aParticle;
     

    
    public PSO_process(){
        
    }
     
    public void setParam(int iteration, int maxrange, int minrange, int population, int nSolution, int vmax, int target){
        this.iteration  = iteration;
        this.maxrange   = maxrange;
        this.minrange   = minrange;
        this.population = population;
        this.nSolution  = nSolution;
        this.vmax       = vmax;
        this.target     = target;
    }
    
    public void process_PSO(){
        
        int gBest = 0;
        int gBestTest = 0;
        int epoch = 0;
        boolean done = false; 
       
        aParticle = new particle[1];
        aParticle[0] = new particle(nSolution);
          Initialization();  
          
          System.out.print("ini partikel di awal iterasi = \n ");
          for (int i = 0; i < population; i++) {
            System.out.print("P["+i+"]");
              for (int j = 0; j < nSolution; j++) {
                  System.out.print(Particle[i].getParticleVector(j)+",");
              }
             System.out.print('\n');
        }
          
          
         
              if (epoch < iteration) {
 
                    do{
                        for(int i = 0; i < population; i++)
                         {
                                if(testProblem(i) == target){
                                    done = true;
                                }
                          }
                          
                           for (int j = 0; j < nSolution; j++) {
                               int fitness = Particle[gBest].getParticleVector(j);
                               aParticle[0].setParticle(j, fitness);  

                               }
                           if(Math.abs(target - testProblem(gBestTest)) < Math.abs(target - testProblem(gBest))){
                              gBest = gBestTest;
                          }

                           getVelocity(gBest);
                           updateparticles(gBest);
                           epoch++;
                       }while(done!=true);
                  
              }
         
       printSolution();
          
    }
     private  void getVelocity(int gBestindex)
    {
        //  refrensi Kennedy & Eberhart(1995).
        //    vx[][] = vx[][] + 2 * rand() * (pbestx[][] - presentx[][]) + 
        //                      2 * rand() * (pbestx[][gbest] - presentx[][])

        int testResults = 0;
        int bestResults = 0;
        double vValue = 0.0;

        bestResults = testProblem(gBestindex);

        for(int i = 0; i < population ; i++)
        {
            testResults = testProblem(i);
            aParticle[0] = Particle[i];
            vValue = aParticle[0].velocity() + 2 * new Random().nextDouble() * (aParticle[0].getFitness() - testResults) + 2 * new Random().nextDouble() * (bestResults - testResults);
            if(vValue > vmax){
                aParticle[0].velocity(vmax);
            }else if(vValue < -vmax){
                aParticle[0].velocity(-vmax);
            }else{
                aParticle[0].velocity(vValue);
            }
        }
        return;
    }
     
    private void Initialization(){
        Particle = new particle[population];
      
       
         for (int i = 0; i < population; i++) {
              Particle[i] = new particle(nSolution);
          }
     
         
         for(int i = 0; i < population; i++)
        {
         
            int total = 0;
            for(int j = 0; j < nSolution; j++)
            {
//                newParticle.data(j, getRandomNumber(START_RANGE_MIN, START_RANGE_MAX));
//                total += newParticle.data(j);
                Particle[i].setParticle(j, getRandomNumber(minrange, maxrange));
                total += Particle[i].getParticleVector(j);
            } 
              Particle[i].setFitness(total);
        } // i
      
    }
    
    private static int getRandomNumber(int low, int high)
    {
        return (int)((high - low) * new Random().nextDouble() + low);
    }
    
    private  int minimum()
    {
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while(!done)
        {
            foundNewWinner = false;
            for(int i = 0; i < population; i++)
            {
                if(i != winner){            
                    if(Math.abs(target - testProblem(i)) < Math.abs(target - testProblem(winner))){
                        winner = i;
                        foundNewWinner = true;
                    }
                }
            }

            if(foundNewWinner == false){
                done = true;
            }
        }

        return winner;
    }
    
     private  int testProblem(int index)
    {
        int total = 0;
      

        for(int i = 0; i < nSolution; i++)
        {
            total += Particle[index].getParticleVector(i);
        }
        return total;
    }
     
    private  void updateparticles(int gBestindex)
    {
        particle[] gBParticle;
        gBParticle  = new particle[1];
        gBParticle[0] = new particle(nSolution);
        
        for (int i = 0; i < nSolution; i++) {
            gBParticle[0].setParticle(i, Particle[gBestindex].getParticleVector(i) );
        }
        
        for(int i = 0; i < population; i++)
        {
            for(int j = 0; j < nSolution; j++)
            {
               
                if (Particle[i].getParticleVector(j)!= gBParticle[0].getParticleVector(j) ) {
                    Particle[i].setParticle(j, Particle[i].getParticleVector(j) + (int)Math.round(Particle[i].velocity()));
                }
                
            } // j

            // Check pBest value.
            int total = testProblem(i);
            
            if (Math.abs(target-total)< Particle[i].getFitness()) {
                Particle[i].getFitness();
            }

        } // i
        return;
    }
    
    private  void printSolution()
    {
        int i = 0;
        for(; i < population; i++)
        {
            if(testProblem(i) == target){
                break;
            }
        }
        System.out.println("Particle " + i + " menemukan solusi ");
        for(int j = 0; j < nSolution; j++)
        {
            if(j < nSolution - 1){
               
                int data= Particle[i].getParticleVector(j);
                System.out.print(data + " + ");
            }else{
                
                int data = Particle[i].getParticleVector(j);
                System.out.print(data+" = " + target);
            }
        } // j
        System.out.print("\n");
        return;
    }
    
}
