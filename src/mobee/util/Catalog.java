package mobee.util;

import mobee.config.GlobalConstant;
import mobee.struct.AObject;

public class Catalog extends AObject{

	private int nextid = GlobalConstant.NULLID+1;
	private int increment;
	private AObject[] objects;
	
	public Catalog(String name, int size, int inc) {
		super(name);
		increment = inc;
		objects = new AObject[size];
	}
	
	public AObject get(int index) {
		assert(index >= 0 && index < objects.length);
		return objects[index];
	}
	
	/**
	 * Find an object by name. Can be very expensive for large catalogs,
	 * since a name comparison is performed in sequence on the objects
	 * in the catalog, until a match is found.
	 * @param name
	 * @return
	 */
	public AObject find(String name) {
		for (int i = 0; i < nextid; i++) {
			if (objects[i].getName().equals(name)) {
				return objects[i];
			}
		}
		return null;
	}
		
	public void push(AObject obj) {
		assert(obj != null && obj.getId() == GlobalConstant.NULLID);
		// Grow if we are at capacity
		if (nextid == objects.length) {
			grow();
		}
		obj.setId(nextid);
		objects[nextid++] = obj;
	}
	
	public AObject pop() {
		if (nextid > GlobalConstant.NULLID) {
			nextid--;
			AObject obj = objects[nextid];
			objects[nextid] = null;
			return obj;
		}
		return null;
	}
	
	public int size() {
		return nextid;
	}
	
	/**
	 * Grows the Catalog by a factor of {@link Catalog#DEFAULT_INCREMENT}.
	 */
	private void grow() {
		//Thread t1 = new Thread(new Runnable() {
		//     public void run()
		//     {
		 		AObject[] temp = new AObject[objects.length+increment];
				System.arraycopy(objects, 0, temp, 0, objects.length);
				objects = temp;
		//     }});  
		//t1.start();		
	}
}
