package io.github.agentsoz.jill.example.hanoi;

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

import io.github.agentsoz.jill.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Board class.</p>
 *
 * @author dsingh
 * @version $Id: $Id
 */
public class Board {

  @SuppressWarnings("unchecked")
  List<Integer>[] pins = (ArrayList<Integer>[]) (new ArrayList[3]);

  /**
   * Creates a new game board.
   *
   * @param ndiscs number of discs in the game
   */
  public Board(int ndiscs) {
    for (int i = 0; i < pins.length; i++) {
      pins[i] = new ArrayList<Integer>();
    }
    for (int i = ndiscs; i > 0; i--) {
      pins[0].add(i);
    }

  }

  /**
   * Move a disc from pin A to pin B.
   *
   * @param pinA the ID of the source pin
   * @param pinB the ID of the destination pin
   * @return true if the move was made, false otherwise
   */
  public boolean move(int pinA, int pinB) {
    // Nothing to do if the pin number is invalid
    if (pinA < 0 || pinA >= pins.length || pinB < 0 || pinB >= pins.length) {
      Log.warn("Invalid board pin specified " + pinA + ". Should be between 0.." + (pins.length - 1)
          + " (inclusive).");
      return false;
    } else if (pins[pinA].isEmpty()) {
      Log.warn("No disc on pin" + pinA);
      return false;
    } else if (pinA == pinB) {
      Log.info("Moving disc from pin" + pinA + " on to itself (means the board will not change)");
      return true;
    }
    int discOnA = pins[pinA].get(pins[pinA].size() - 1);
    int discOnB =
        (pins[pinB].isEmpty()) ? Integer.MAX_VALUE : pins[pinB].get(pins[pinB].size() - 1);
    if (discOnB < discOnA) {
      Log.warn("Cannot move disc" + discOnA + " (pin" + pinA + ") on to smaller disc" + discOnB
          + " (pin" + pinB + ")");
      return false;
    }
    pins[pinB].add(pins[pinA].remove(pins[pinA].size() - 1));
    return true;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    if (pins.length == 0) {
      return str.toString();
    }
    for (int i = 0; i < pins.length; i++) {
      if (pins[i].isEmpty()) {
        str.append('|');
      }
      for (Integer j : pins[i]) {
        str.append('|');
        str.append(j);
      }
      str.append('\n');
    }
    return str.substring(0, str.length() - 1);
  }

}
