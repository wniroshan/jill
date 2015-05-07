package mobss.core;

/*
 * #%L
 * MOBSS, Masses of BDI-agents for Social Simulation
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

import mobss.util.Catalog;

/**
 * A catalog of all known agent, goal and plan types
 * @author dsingh
 *
 */
public class GlobalState {

	public static Catalog agentTypes = new Catalog("agentTypes", 5,5);
	public static Catalog goalTypes = new Catalog("goalTypes", 10,5);
	public static Catalog planTypes = new Catalog("planTypes", 20,5);
	
	public static Catalog agents;

}