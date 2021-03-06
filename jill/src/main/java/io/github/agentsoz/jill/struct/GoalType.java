package io.github.agentsoz.jill.struct;

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

/**
 * <p>GoalType class.</p>
 *
 * @author dsingh
 * @version $Id: $Id
 */
public class GoalType extends GoalPlanType {
  private Class<?> goalClass;

  /**
   * <p>Constructor for GoalType.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public GoalType(String name) {
    super(name);
  }

  /**
   * <p>Getter for the field <code>goalClass</code>.</p>
   *
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getGoalClass() {
    return goalClass;
  }

  /**
   * <p>Setter for the field <code>goalClass</code>.</p>
   *
   * @param goalClass a {@link java.lang.Class} object.
   */
  public void setGoalClass(Class<?> goalClass) {
    this.goalClass = goalClass;
  }
}
