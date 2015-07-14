package agentsoz.jill;

/*
 * #%L
 * Jill Cognitive Agents Platform
 * %%
 * Copyright (C) 2014 - 2015 by its authors. See AUTHORS file.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import agentsoz.jill.core.GlobalState;
import agentsoz.jill.core.IntentionSelector;
import agentsoz.jill.core.ProgramLoader;
import agentsoz.jill.lang.Agent;
import agentsoz.jill.util.AObjectCatalog;
import agentsoz.jill.util.ArgumentsLoader;
import agentsoz.jill.util.Log;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Parse the command line options
		ArgumentsLoader.parse(args);
		
		// Pause for key press from user if requested
		if (ArgumentsLoader.doPauseForUserInput()) { pauseForUserInput(); }
		
		// Configure logging
        Log.createLogger("", ArgumentsLoader.getLogLevel(), ArgumentsLoader.getLogFile());

		
		int NUMAGENTS = ArgumentsLoader.getNumAgents(); 
		int INCREMENT = 10000;

		GlobalState.agents = new AObjectCatalog("agents", NUMAGENTS, INCREMENT);

		long t0, t1;
		
		// Create the agents
		t0 = System.currentTimeMillis();
		ProgramLoader.load(ArgumentsLoader.getAgentClass(), NUMAGENTS, GlobalState.agents);
		t1 = System.currentTimeMillis();
		Log.info("Created " + GlobalState.agents.size() + " agents ("+(t1-t0)+" ms)");


		// Redirect the agent program output if specified
		PrintWriter writer = null;
		if (ArgumentsLoader.getProgramOutputFile() != null) {
			try {
				writer = new PrintWriter(ArgumentsLoader.getProgramOutputFile(), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Start the agents
		t0 = System.currentTimeMillis();
		for (int i = 0; i < GlobalState.agents.size(); i++) {
			// Get the agent
			Agent agent = (Agent)GlobalState.agents.get(i);
			// Start the agent
			agent.start(writer, ArgumentsLoader.getProgramArguments());
		}
		t1 = System.currentTimeMillis();
		Log.info("Started " + GlobalState.agents.size() + " agents ("+(t1-t0)+" ms)");

		// Wait till we are all done
		t0 = System.currentTimeMillis();
		int cycles = ArgumentsLoader.getNumCycles();
		int ncores = ArgumentsLoader.getNumThreads();
		int nagents = GlobalState.agents.size();
		int poolsize = (nagents > ncores) ? (nagents/ncores) : 1;
		int npools = (nagents > ncores) ? ncores : nagents;
		int cycle = 0;
		while (cycles == -1 || cycle  < cycles) {
	        ExecutorService executor = Executors.newFixedThreadPool(ncores);
	        for (int i = 0; i < npools; i++) {
	        	int start = i*poolsize;
	        	int size = (i+1 < npools) ? poolsize : GlobalState.agents.size()-start;
	        	//int size = (start+poolsize<=GlobalState.agents.size()) ? poolsize : 
	        	//	poolsize - (GlobalState.agents.size()-start);
				executor.execute(new IntentionSelector(ArgumentsLoader.getRandomSeed(), start,size));
	        }
			executor.shutdown();
			try {
				executor.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Log.warn(e.getMessage());
			}
			if (cycles != -1) {
				cycle++;
			}
		}
		t1 = System.currentTimeMillis();
		Log.info("Finished running "+cycle+" execution cycles with " + GlobalState.agents.size() + " agents ("+(t1-t0)+" ms)");

		// Finish the agents
		t0 = System.currentTimeMillis();
		for (int i = 0; i < GlobalState.agents.size(); i++) {
			// Get the agent
			Agent agent = (Agent)GlobalState.agents.get(i);
			// Terminate the agent
			agent.finish();
		}
		if (writer != null) {
			writer.close();
		}
		t1 = System.currentTimeMillis();
		Log.info("Terminated " + GlobalState.agents.size() + " agents ("+(t1-t0)+" ms)");

	}
	
	/**
	 * Waits for user to press a key before continuing.
	 * Useful for connecting to a profiler
	 */
	private static void pauseForUserInput() {
		System.out.println("Press the Enter/Return key to continue..");
		Scanner in = new Scanner(System.in);
		in.nextLine();
		in.close();
	}
}