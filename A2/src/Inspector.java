import java.util.*;
import java.lang.reflect.*;


public class Inspector extends ObjectInspector{
	private boolean rec;
	
	public Inspector() {}	
	
	public Class<? extends Object> getClass( Object obj )
	{
		String fullName = obj.getClass().getName();		
		String cleanName = fullName.split(";")[0];
		String className = cleanName.substring( cleanName.indexOf("[L") + 2 );

		Class<? extends Object> cls = null;
		try 
		{
			cls = Class.forName(className);
		}
		catch (ClassNotFoundException e) 
		{
			//Do nothing
		}
		return cls;
	}
								
	// inspect object according to boolean value recursive
	public void inspect(Object obj, boolean recursive)
	{
		Vector objectsToInspect = new Vector();
		Class ObjClass = obj.getClass(); 	
		Class[] interfaces = ObjClass.getInterfaces();
		Class SuperClass;
		rec = recursive;
		
		// Check to see if obj is a array or not
		if( ObjClass.isArray() )
		{
			System.out.println("here");
			System.out.println("------------------------------------------------------");
			System.out.println("                   Inspecting Array                   ");
			//print name
			System.out.println("Name of the Array: " + ObjClass.getName());
			//print component type
			System.out.println("Component Type: " + ObjClass.getComponentType());
			//print the length of array
			System.out.println("Array Length: " + Array.getLength(obj));
			
			ObjClass = getClass( obj );
		}else{
			System.out.println("------------------------------------------------------");		
			System.out.println("Inside inspector: " + ObjClass.getName() + " (recursive = "+recursive+")");
		}	
		
		
		
		//name of the immediate superclass
		SuperClass = ObjClass.getSuperclass();
		System.out.println("Immediate superclass: " + SuperClass.getName());
		
		//name of interfaces current class implements
		for(int i = 0; i < interfaces.length ; i++)
		{
			System.out.println("Interface " +  (1+i) + ": " + interfaces[i].getName());
		}
		
		//inspect current class' constructors
		inspectConstructors(obj,ObjClass,objectsToInspect);
		System.out.println("------------------------------------------------------");
		
		//inspect current class' methods
		inspectMethods( obj, ObjClass,objectsToInspect);
		
		//inspect the current class
		System.out.println("------------------------------------------------------");
		inspectFields(obj, ObjClass,objectsToInspect);
		
		System.out.println("------------------------------------------------------");
		if(recursive)
		    inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
		 
	}
	
	
	public static boolean isArray(Object obj) {
		     if (obj != null)
		        return obj.getClass().isArray();
		     return false;
	}
	
	
    //-----------------------------------------------------------
	/* Inspect the objects in fields
	 * @param: Object : base object
	 * @param: Class  : class object
	 * @param: Vector : objects to be inspected
	 * @param: boolean: recursive value indicates the need to find all 
	 * 					field object inside a class recursively
	 */
	 public void inspectFieldClasses(Object obj,Class ObjClass,
		     						Vector objectsToInspect,boolean recursive)
	 {
		 // If there is still object in a class to be inspected
		 if(objectsToInspect.size() > 0 )
			System.out.println("---- Inspecting Field Classes ----");
			
		 
		 Enumeration e = objectsToInspect.elements();
		 while(e.hasMoreElements())
		 {	
			 Field f = (Field) e.nextElement();
			 int mod = f.getModifiers();
			 String retval = Modifier.toString(mod);
			 try {
			 System.out.println("Field: " + f.getName() + " = " + f.get(obj));
			 System.out.println("Field type: " + f.getType());
			 System.out.println("Field modifier: " + retval);}
			 catch(Exception e1) {}
			 
			 
			 
			 /*
			  * while all the objects to inspect item should be object
			  * inspect that object recursively.
			  */
			 try
			 {
				 System.out.println("******************");
				 inspect( f.get(obj), recursive);
				 System.out.println("******************");
			 }
			catch(Exception exp) 
			{ 
				break;
				//exp.printStackTrace();
			}
		 }
	 }
    
    
    
    //-----------------------------------------------------------
	 /* Inspecting the current class' fields
	  * @param: Object : the base object
	  * @param: Class  : the class object of the object (meta object)
	  * @param: Vector : objects still need to be inspected	 
	  */
	 public void inspectFields(Object obj,Class ObjClass,Vector objectsToInspect)
	 {
		 /* if all the class object's fields length is more than one;
		  * getDeclaredFields() include all field in the current class
		  * no matter the accessibility
		  */
		 for( int i = 0; i < ObjClass.getDeclaredFields().length; i++)
		 {
			 
			 Field f = ObjClass.getDeclaredFields()[i];										// f is a pointer of first element in the fields
			 int mod = f.getModifiers();
			 String retval = Modifier.toString(mod);
			 
			 
			 f.setAccessible(true);															// each field the f is pointing can be accessed
			
			 /* if field type is not primitive:
			  * 	add to the objectsToInspect vector
			  */
			 if(! f.getType().isPrimitive()  ) {
				 if(rec == true)
				 {
					 objectsToInspect.addElement( f );
				 }else {
					 try {
						 System.out.println("Object");
					 System.out.println("Field: " + f.getName() + " = " + f.get(obj));
					 System.out.println("Field type: " + f.getType());
					 System.out.println("Field modifier: " + retval);}
					 catch(Exception e) {}
				 }	 
			 }
			 else {
				 System.out.println("  *Field* ");
				 try
				 {
						 System.out.println("Field: " + f.getName() + " = " + f.get(obj));
				 }
				 catch(Exception e) {}   
				
				 
				 try
				 {
					 System.out.println("Field type: " + f.getType());
				 }
				 catch(Exception e) {}
				 
				 
				 try
				 {
					 System.out.println("Field modifier: " + retval);
				 }
				 catch(Exception e) {}
			 }
			 
		 }

		/*  keep inspect of the parent class and keep track of objects 
		 * that is still needed to be inspected
		 */
		if(ObjClass.getSuperclass() != null)
			inspectFields(obj, ObjClass.getSuperclass() , objectsToInspect);
	 }

	 
	 public void inspectMethods(Object obj,Class ObjClass,Vector objectsToInspect)
	 {
		//inspect the method in current class		
			for(int y = 0; y < ObjClass.getDeclaredMethods().length; y++)
			{
				System.out.println("  *Method " + (y+1) + "*");
				Method m = ObjClass.getDeclaredMethods()[y];
				m.setAccessible(true);
				
				// name
				try
				 {
					 System.out.println("Method: " + m.getName());
				 }
				 catch(Exception e) {}  
				
				// exception thrown
				Class[] exceptions = m.getExceptionTypes();
				for(int i = 0; i < exceptions.length; i++) 
				{
					try
					 {
						 System.out.println("Exception: " + exceptions[i].getName());
					 }
					 catch(Exception e) {}  
				}
						
				Class[] parameters = m.getParameterTypes();
				for(int i = 0; i < parameters.length; i++) 
				{
					try
					 {
						 System.out.println("Parameter: " + parameters[i].getName());
					 }
					 catch(Exception e) {}  
				}
				
				Class returnType = m.getReturnType();
				try
				 {
					 System.out.println("Return Type: " + returnType.getName());
				 }
				 catch(Exception e) {}
				
				int mod = m.getModifiers();
				String retval = Modifier.toString(mod);
				try
				 {
					 System.out.println("Modifier: " + retval);
				 }
				 catch(Exception e) {}			
				
			}
	 }
	 
	 
	 public void inspectConstructors(Object obj,Class ObjClass,Vector objectsToInspect)
	 {
		//inspect the constructors
		 	Constructor[] constructors = ObjClass.getDeclaredConstructors();
		 	int count = 1;
			for(Constructor c : constructors)
			{
				
				c.setAccessible(true);
				
				System.out.println("  *CONSTRUCTOR  " + count + "*");
				count++;
				try
				 {
					 System.out.println("Constructor: " + c.getName());
				 }
				 catch(Exception e) {}  
				
				Class[] param = c.getParameterTypes();
				for(int x = 0; x < param.length; x++) 
				{
					try
					 {
						 System.out.println("Parameter " + x + " type: " + param[x].getName());
					 }
					 catch(Exception e) {}  
				}
				
				int mod = c.getModifiers();
				String retval = Modifier.toString(mod);
				try
				 {
					 System.out.println("Modifier: " +  retval);
				 }
				 catch(Exception e) {}
					
			}		
	 }

	 
	 
	 
}
