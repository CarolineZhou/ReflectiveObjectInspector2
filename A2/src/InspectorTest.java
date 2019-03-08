import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InspectorTest 
{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	
	@Test 
	public void inspectConstructorsClassA()
	{
		Inspector inspector = new Inspector();
		Object object = null;
		try 
		{
			object = (Object) new ClassA();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<? extends Object> ObjClass = object.getClass(); 	
		
		inspector.inspectConstructors(object, ObjClass, null);
		String output = outContent.toString();

		String constructor1Expected = "Constructor: ClassA\r\n"
									+ "Modifier: public\r\n";
		int constructor1Index = output.indexOf(constructor1Expected);
		String constructor1Actual = output.substring(constructor1Index, constructor1Index + 39 );

		
		String constructor2Expected = "Constructor: ClassA\r\n"
									+ "Parameter 0 type: int\r\n"
									+ "Modifier: public\r\n";
		
		int constructor2Index = output.indexOf(constructor2Expected);
		String constructor2Actual = output.substring(constructor2Index, constructor2Index + 62 );

		assertEquals(constructor1Expected, constructor1Actual);
		assertEquals(constructor2Expected, constructor2Actual);
	}
	
	
	@Test 
	public void inspectConstructorsClassB()
	{
		Inspector inspector = new Inspector();
		Object object = null;
		try 
		{
			object = (Object) new ClassB();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<? extends Object> ObjClass = object.getClass(); 	
		
		inspector.inspectConstructors(object, ObjClass, null);
		String output = outContent.toString();

		String constructor1Expected = "Constructor: ClassB\r\n"
									+ "Modifier: public\r\n";
		int constructor1Index = output.indexOf(constructor1Expected);
		String constructor1Actual = output.substring(constructor1Index, constructor1Index + 39 );

		
		assertEquals(constructor1Expected, constructor1Actual);
	}

	
	@Test 
	public void inspectConstructorsClassD()
	{
		Inspector inspector = new Inspector();
		Object object = null;
		try 
		{
			object = (Object) new ClassD();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<? extends Object> ObjClass = object.getClass(); 	
		
		inspector.inspectConstructors(object, ObjClass, null);
		String output = outContent.toString();

		String constructor1Expected = "Constructor: ClassD\r\n"
									+ "Modifier: public\r\n";
		int constructor1Index = output.indexOf(constructor1Expected);
		String constructor1Actual = output.substring(constructor1Index, constructor1Index + 39 );

		
		String constructor2Expected = "Constructor: ClassD\r\n"
									+ "Parameter 0 type: int\r\n"
									+ "Modifier: public\r\n";
		
		int constructor2Index = output.indexOf(constructor2Expected);
		String constructor2Actual = output.substring(constructor2Index, constructor2Index + 62 );

		assertEquals(constructor1Expected, constructor1Actual);
		assertEquals(constructor2Expected, constructor2Actual);
	}

	
	@Test
	public void inspectMethodsTestClassA()
	{
		Inspector inspector = new Inspector();
		Object object = null;
		try 
		{
			object = (Object) new ClassA();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<? extends Object> objectsToInspect = new Vector<>();
		Class<? extends Object> ObjClass = object.getClass(); 	
		
		inspector.inspectMethods( object, ObjClass, objectsToInspect );		
		String output = outContent.toString();
		
		int runMethodIndex = output.indexOf("Method: run");
		String runMethodActual = output.substring(runMethodIndex, runMethodIndex + 50 );
		String runMethodExpected = "Method: run\r\n"
								+ "Return Type: void\r\n"
								+ "Modifier: public\r\n";

		int toStringMethodIndex = output.indexOf("Method: toString");
		String toStringMethodActual = output.substring(toStringMethodIndex, toStringMethodIndex + 67 );
		String toStringMethodExpected = "Method: toString\r\n"
										+ "Return Type: java.lang.String\r\n"
										+ "Modifier: public\r\n";
	
		int setValMethodIndex = output.indexOf("Method: setVal");
		String setValMethodActual = output.substring(setValMethodIndex, setValMethodIndex + 101 );
		String setValMethodExpected = "Method: setVal\r\n"
									+ "Exception: java.lang.Exception\r\n"
									+ "Parameter: int\r\n"
									+ "Return Type: void\r\n"
									+ "Modifier: public\r\n";
		
		int printSomethingMethodIndex = output.indexOf("Method: printSomething");
		String printSomethingMethodActual = output.substring(printSomethingMethodIndex, printSomethingMethodIndex + 62 );
		String printSomethingMethodExpected = "Method: printSomething\r\n"
											+ "Return Type: void\r\n"
											+ "Modifier: private\r\n";

		int getValMethodIndex = output.indexOf("Method: getVal");
		String getValMethodActual = output.substring(getValMethodIndex, getValMethodIndex + 52 );
		String getValMethodExpected = "Method: getVal\r\n"
									+ "Return Type: int\r\n"
									+ "Modifier: public\r\n";
		
		assertEquals(runMethodExpected, runMethodActual);
		assertEquals(toStringMethodExpected, toStringMethodActual);
		assertEquals(setValMethodExpected, setValMethodActual);
		assertEquals(printSomethingMethodExpected, printSomethingMethodActual);
		assertEquals(getValMethodExpected, getValMethodActual);
	}

	
	@Test
	public void inspectMethodsTestClassB()
	{
		Inspector inspector = new Inspector();
		Object object = null;
		try 
		{
			object = (Object) new ClassB();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<? extends Object> objectsToInspect = new Vector<>();
		Class<? extends Object> ObjClass = object.getClass(); 	

		inspector.inspectMethods( object, ObjClass, objectsToInspect );		
		String output = outContent.toString();
		
		int runMethodIndex = output.indexOf("Method: run");
		String runMethodActual = output.substring(runMethodIndex, runMethodIndex + 50 );
		String runMethodExpected = "Method: run\r\n"
								+ "Return Type: void\r\n"
								+ "Modifier: public\r\n";

		int toStringMethodIndex = output.indexOf("Method: toString");
		String toStringMethodActual = output.substring(toStringMethodIndex, toStringMethodIndex + 67 );
		String toStringMethodExpected = "Method: toString\r\n"
										+ "Return Type: java.lang.String\r\n"
										+ "Modifier: public\r\n";
	
		int func3ValMethodIndex = output.indexOf("Method: func3");
		String func3ValMethodActual = output.substring(func3ValMethodIndex, func3ValMethodIndex + 68);
		String func3ValMethodExpected = "Method: func3\r\n"
										+ "Parameter: int\r\n"
										+ "Return Type: void\r\n"
										+ "Modifier: public\r\n";
		
		assertEquals(runMethodExpected, runMethodActual);
		assertEquals(toStringMethodExpected, toStringMethodActual);
		assertEquals(func3ValMethodExpected, func3ValMethodActual);
	}

	

	@Test
	public void getClassTest()
	{
		Inspector inspector = new Inspector();
		Class<? extends Object> classA = null;
		Class<? extends Object> classB = null;
		Class<? extends Object> classC = null;
		Class<? extends Object> classD = null;

		try 
		{
			classA = Class.forName( "ClassA" );
			classB = Class.forName( "ClassB" );
			classC = Class.forName( "ClassC" );
			classD = Class.forName( "ClassD" );
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals( classA, inspector.getClass((Object)new ClassA[12]) );
		assertEquals( classB, inspector.getClass((Object)new ClassB[19]) );
		assertEquals( classC, inspector.getClass((Object)new ClassC[14]) );
		assertEquals( classD, inspector.getClass((Object)new ClassD[23]) );
		
		assertEquals( classA, inspector.getClass((Object)new ClassA[12][12]) );
		assertEquals( classB, inspector.getClass((Object)new ClassB[12][134]) );
		assertEquals( classC, inspector.getClass((Object)new ClassC[12][53]) );
		assertEquals( classD, inspector.getClass((Object)new ClassD[12][1]) );		
	}

}