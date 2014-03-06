package SimpleAgent;

import mobee.lang.Agent;
import mobee.lang.Plan;
import mobee.lang.PlanInfo;
import mobee.lang.PlanStep;

@PlanInfo(handlesGoal="SimpleAgent.GoalA", postsGoals={"SimpleAgent.GoalB", "SimpleAgent.GoalC"})
public class PlanA extends Plan { 

	public PlanA(Agent agent, String name) {
		super(agent, name);
		body = steps;
	}

	public String context() {
		return "neighbour(X) && close(X)";
	}
	
	PlanStep[] steps = {
			new PlanStep() {
				public void step() {
					((TestAgent)getAgent()).setI(((TestAgent)getAgent()).getI() | 0x0001);
				}
			},
			new PlanStep() {
				public void step() {
					post(new GoalB("gB"));
				}
			},
			new PlanStep() {
				public void step() {
					post(new GoalC("gC"));
				}
			},
	};
	
}