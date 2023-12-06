package agh.ics.oop;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private List<Simulation> simulationList;
    private List<Thread> simulationsThreads = new ArrayList<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulationList){
        this.simulationList = simulationList;
    }

    public void runSync(){
        for (Simulation simulation : simulationList){
            simulation.run();
        }
    }

    public void runAsync(){
        for (int i = 0; i < simulationList.size(); i++){
            simulationsThreads.add(new Thread(simulationList.get(i)));
        }
        for (Thread simulationThread : simulationsThreads){
            simulationThread.start();
        }
    }

    public void runAsyncInThreadPool(){
        for (Simulation sim : simulationList){
            executorService.submit(sim);
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread simulationThread : simulationsThreads){
            simulationThread.join();
        }
        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)){
            executorService.shutdownNow();
        }
    }
}
