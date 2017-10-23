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
    int c1;
    int c2;
    int population;
    int vectorSolution ;
    int iteration;
    
    int currentBestIndex;
    particle[] Particle;
    particle[] Pbest;
    particle[] oldPbest;
     
    velocity[] Velocity;
    velocity[] oldVelocity;
    
    public PSO_process(){
        
    }
     
    public void setParam(int iteration, int c1, int c2, int population, int nSolution){
        this.c1= c1;
        this.c2= c2;
        this.iteration = iteration;
        this.population= population;
        this.vectorSolution= nSolution;
    }
    
    public void process_PSO(){
          Initialization();
           int currentIteration =0;
          for (int i = 0; i < iteration; i++) {
           
            if (currentIteration==0) {
                System.out.print("iterasi ke 0 ======\n");
                Evaluation();
                view();
                setPbest(currentIteration);
                setGbest();
                viewCurrentPbest();
                setVelocity(currentIteration);
                System.out.print("\n");
                currentIteration++;
              }
            else{//iteration >0
                System.out.print("iterasi ke "+currentIteration+"======\n");
                setNewParticle();
                Evaluation();
                view();
                setPbest(currentIteration);
                viewCurrentPbest();
                setGbest();
                setVelocity(currentIteration);
                System.out.print("\n");
                currentIteration++;
            }
          
          
        }
         
          
    }
    
    public void Initialization(){
        Particle = new particle[population];
        Pbest = new particle[population];
        oldVelocity = new velocity[population];
        oldPbest = new particle[population];
         
        for (int i = 0; i < population; i++) {
              Particle[i] = new particle(vectorSolution);
              Pbest[i] = new particle(vectorSolution);
              oldPbest[i] = new particle(vectorSolution);
          }
         for (int i = 0; i < population; i++) {
            oldVelocity[i]= new velocity(vectorSolution);
        }
        
        for (int i = 0; i < population; i++) {
            double x,y,z;
            x=randProbability(0,20);
            y=randProbability(0,20);
            z=randProbability(0,20);
            
            Particle[i].setParticle(0, x);
            Particle[i].setParticle(1, y);
            Particle[i].setParticle(2, z);
        }
      
    }
    
    public void Evaluation(){
        for (int i = 0; i < population; i++) {
            double fitness;
            double x= Particle[i].getParticleVector(0);
            double y= Particle[i].getParticleVector(1);
            double z= Particle[i].getParticleVector(2);
            fitness= objectiveFunction(x,y,z);
            Particle[i].setFitness(fitness);
        }
        
    }
    
    public void setNewParticle(){
        for (int i = 0; i < population; i++) {
            double x,y,z;
            double vx,vy,vz;
            x= Particle[i].getParticleVector(0);
            y= Particle[i].getParticleVector(1);
            z= Particle[i].getParticleVector(2);
            vx = Velocity[i].getVelocity(0);
            vy = Velocity[i].getVelocity(1);
            vz = Velocity[i].getVelocity(2);
            
            Particle[i].setParticle(0, x+vx);
            Particle[i].setParticle(1, y+vy);
            Particle[i].setParticle(2, z+vz);
            
        }
    }
    
    public void viewCurrentPbest(){
        for (int i = 0; i < population; i++) {
            System.out.print("Pbest["+i+"] f(x)="+Pbest[i].getFitness()+"\n");
        }
    }
    
    
    public void setPbest(int currentIteration){
        
        if (currentIteration==0) {
            for (int i = 0; i < population; i++) {
                 double tempx,tempy,tempz,tempfx;
                 tempx= Particle[i].getParticleVector(0);
                 tempy= Particle[i].getParticleVector(1);
                 tempz= Particle[i].getParticleVector(2);
                 tempfx = Particle[i].getFitness();
                 Pbest[i].setParticle(0,tempx);
                 Pbest[i].setParticle(1,tempy);
                 Pbest[i].setParticle(2,tempz);
                 Pbest[i].setFitness(tempfx);
            }
            
            for (int i = 0; i < population; i++) {
                oldPbest[i] = Pbest[i];
            }
        }
        else{
            for (int i = 0; i < population; i++) {
                
                //INI KHUSUS KALO OLD PBEST LBH BAIK DRPD PBEST YG BARU
                if (oldPbest[i].getFitness() >=0 && oldPbest[i].getFitness()< Particle[i].getFitness()) {
                    double tempx,tempy,tempz,tempfx;
                    tempx= oldPbest[i].getParticleVector(0);
                    tempy= oldPbest[i].getParticleVector(1);
                    tempz= oldPbest[i].getParticleVector(2);
                    tempfx= oldPbest[i].getFitness();
                    Pbest[i].setParticle(0,tempx);
                    Pbest[i].setParticle(1,tempy);
                    Pbest[i].setParticle(2,tempz);
                    Pbest[i].setFitness(tempfx);
                }
                else  {
                    if (Particle[i].getFitness() >= 0 && oldPbest[i].getFitness()> Particle[i].getFitness()) {
                      double tempx,tempy,tempz,tempfx;;
                      tempx= Particle[i].getParticleVector(0);
                      tempy= Particle[i].getParticleVector(1);
                      tempz= Particle[i].getParticleVector(2);
                      tempfx= Particle[i].getFitness();
                      Pbest[i].setParticle(0,tempx);
                      Pbest[i].setParticle(1,tempy);
                      Pbest[i].setParticle(2,tempz);
                      Pbest[i].setFitness(tempfx);
                        
                    }
                    
                    
                }
                
            }
            
        }
    }
    
    public void setGbest(){
        int bestIndex;
        bestIndex=getBestSolutionIndex(Pbest);
        currentBestIndex= bestIndex;
        double bestx,besty,bestz,bestfitness;
        bestfitness=Pbest[bestIndex].getFitness();
        bestx= Pbest[bestIndex].getParticleVector(0);
        besty= Pbest[bestIndex].getParticleVector(1);
        bestz= Pbest[bestIndex].getParticleVector(2);
        System.out.print("Gbest= P["+bestIndex+", f(x):"+bestfitness+",x:"+bestx+",y:"+besty+",z:"+bestz+"\n");
   
    }
    
    public void setVelocity(int currentIteration ){
        Velocity = new velocity[population];
        double velocity;
    
        for (int i = 0; i < population; i++) {
            Velocity[i]= new velocity(vectorSolution);
        }
        
        if (currentIteration==0) { //jika iterasi ==0
            for (int i = 0; i < population; i++) {
                for (int j = 0; j < vectorSolution; j++) {
                     //set Velocity
                     double pb,x,gbest;
                     double vBefore=0;
                     double r1= randProbability(0,1);
                     double r2= randProbability(0,1);
                     pb=Pbest[i].getParticleVector(j);
                     x= Particle[i].getParticleVector(j);
                     gbest=Particle[currentBestIndex].getParticleVector(j);
                     velocity = vBefore+ c1 * r1 *(pb-x)+ c2 * r2 * (gbest-x );
                     Velocity[i].setVelocity(j, velocity);
                 }
             }
            
            for (int i = 0; i < population; i++) {
                double a,b,c;
              a=  Velocity[i].getVelocity(0);
              b=  Velocity[i].getVelocity(1);
              c=  Velocity[i].getVelocity(2);
              System.out.print("V["+i+"] pada x : "+a+",pada y:"+b+",pada z="+c+"\n");
            }
            for (int i = 0; i < population; i++) {
                oldVelocity[i]= Velocity[i];
            }
           
        }
        else{  //iterasi >0 
             for (int i = 0; i < population; i++) {
                for (int j = 0; j < vectorSolution; j++) {
                     //set Velocity
                     double pb,x,gbest;
                     double vBefore=oldVelocity[i].getVelocity(j);
                     double r1= randProbability(0,1);
                     double r2= randProbability(0,1);
                     pb=Pbest[i].getParticleVector(j);
                     x= Particle[i].getParticleVector(j);
                     gbest=Particle[currentBestIndex].getParticleVector(j);
                     velocity = vBefore+ c1 * r1 *(pb-x)+ c2 * r2 * (gbest-x );
                     Velocity[i].setVelocity(j, velocity);
                 }
             }
            
            for (int i = 0; i < population; i++) {
                double a,b,c;
              a=  Velocity[i].getVelocity(0);
              b=  Velocity[i].getVelocity(1);
              c=  Velocity[i].getVelocity(2);
              System.out.print("V["+i+"] pada x : "+a+",pada y:"+b+",pada z="+c+"\n");
            }
            for (int i = 0; i < population; i++) {
                oldVelocity[i]= Velocity[i];
            }
           
        }   
    }
    
    
    private double randProbability(int min, int max){
        Random random = new Random();
        double value = min + (max - min) * random.nextDouble();    
        return value;
    }
    
    public double objectiveFunction(double x, double y, double z){
        double value=0;
        value = (x*x)+y+z;
        return value;
    }
    
    public void view(){
                
        for (int i = 0; i < population; i++) {
            double x,y,z,fitness;
            x= Particle[i].getParticleVector(0);
            y= Particle[i].getParticleVector(1);
            z= Particle[i].getParticleVector(2);
            fitness= Particle[i].getFitness();
            System.out.print("P["+i+"],");
            System.out.print("x=" + x + ",y =" + y + ",z=" + z + ",f(x)="+ fitness+"\n");
        }
    }
    
    private int getBestSolutionIndex(particle[] particle){
        int index=0;
        double best,possibleBest; 
        best = particle[0].getFitness();
        for(int i=1; i<population; i++)
        {
            possibleBest = particle[i].getFitness();
            
            if(possibleBest < best )
            {
                best = possibleBest;
                index = i;    
            }
         }
           return index;
    }
    
    public particle[] getParticle(){
        return Particle;
    }
}
