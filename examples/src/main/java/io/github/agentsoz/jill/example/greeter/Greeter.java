package io.github.agentsoz.jill.example.greeter;

/*
 * #%L
 * Jill Cognitive Agents Platform
 * %%
 * Copyright (C) 2014 - 2018 by its authors. See AUTHORS file.
 * %%
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import io.github.agentsoz.jill.core.beliefbase.BeliefBaseException;
import io.github.agentsoz.jill.core.beliefbase.BeliefSetField;
import io.github.agentsoz.jill.lang.Agent;
import io.github.agentsoz.jill.lang.AgentInfo;
import io.github.agentsoz.jill.util.Log;

import java.io.PrintStream;
import java.util.Random;

/**
 * <p>Greeter class.</p>
 *
 * @author dsingh
 * @version $Id: $Id
 */
@AgentInfo(hasGoals = {"io.github.agentsoz.jill.example.greeter.BeFriendly"})
public class Greeter extends Agent {

  // Defaults
  private static Random rand = new Random();
  private static int numNeighbours = 1;
  private static final String beliefset = "neighbour";

  private static final String[] males =
      {"Alex", "Daniel", "John", "Lionel", "Nick", "Oscar", "Paul", "Rod", "Sam", "Tom"};
  private static final String[] females =
      {"Alice", "Elisa", "Fiona", "Julia", "Kate", "Laura", "Margaret", "Nancy", "Pam", "Rachael"};
  private static final String[] middle =
      {"A.", "B.", "C.", "D.", "E.", "F.", "G.", "H.", "I.", "J.", "K.", "L.", "M.", "N.", "O.",
          "P.", "Q.", "R.", "S.", "T.", "U.", "V.", "W.", "X.", "Y.", "Z."};
  private static final String[] surnames = {"Anderson", "Brown", "Jones", "Martin", "Morton",
      "Smith", "Taylor", "White", "Williams", "Wilson",};

  /**
   * <p>Constructor for Greeter.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public Greeter(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void start(PrintStream writer, String[] params) {
    // Parse the arguments
    parse(params);

    // Create a new belief set about neighbours
    BeliefSetField[] fields = {new BeliefSetField("name", String.class, true),
        new BeliefSetField("gender", String.class, false),};

    try {
      // Attach this belief set to this agent
      this.createBeliefSet(beliefset, fields);

      // Add beliefs about neighbours
      registerNeighbours(rand, numNeighbours);
      Log.debug("Agent " + getName() + " is initialising with " + numNeighbours + " neighbours");

      // Post the goal to be friendly
      post(new BeFriendly("BeFriendly"));
    } catch (BeliefBaseException e) {
      Log.error(e.getMessage());
    }
  }

  /**
   * Helper function to add beliefs about neighbours.
   * 
   * @param rand random number generator to use
   * @param count number of beliefs to add
   * @throws BeliefBaseException thrown if there is a belief base access error
   */
  private void registerNeighbours(Random rand, int count) throws BeliefBaseException {
    int size = (count < 0) ? 0 : count;
    for (int i = 0; i < size; i++) {
      boolean male = (rand.nextDouble() < 0.5) ? true : false;
      this.addBelief(beliefset, buildName(male), male ? "male" : "female");
    }
  }

  /**
   * Builds a new name.
   * 
   * @param male specifies whether the name should be for a male or female
   * @return the constructed name
   */
  private static String buildName(boolean male) {
    StringBuilder name = new StringBuilder();
    name.append(male ? males[rand.nextInt(males.length)] : females[rand.nextInt(females.length)])
        .append(' ').append(middle[rand.nextInt(middle.length)]).append(' ')
        .append(surnames[rand.nextInt(surnames.length)]);
    return name.toString();
  }

  /**
   * Parses the command line arguments.
   *
   * @param args arguments passed to this program
   */
  public static void parse(String[] args) {
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-seed":
          if (i + 1 < args.length) {
            i++;
            int seed = 0;
            try {
              seed = Integer.parseInt(args[i]);
              rand = new Random(seed);
            } catch (NumberFormatException e) {
              Log.warn("Seed value '" + args[i] + "' is not a number");
            }
          }
          break;
        case "-neighbourhoodSize":
          if (i + 1 < args.length) {
            i++;
            try {
              numNeighbours = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
              Log.warn("Neighbourhood size value '" + args[i] + "' is not a number");
            }
          }
          break;
        default:
          // Ignore any other arguments
          break;
      }
    }
  }
}
