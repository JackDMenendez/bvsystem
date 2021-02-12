package org.bv.com.system.test;

import static org.junit.jupiter.api.Assertions.*;

import org.bv.com.system.Delegate;
import org.bv.com.system.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {
 
	class EventArgument { 
		private int testValue_;
		public EventArgument(int testValue) {
			testValue_ = testValue;
		}
		public int getTestValue() {
			return testValue_;
		}
	}
	
	static Event<EventArgument> staticTestEvent = new Event<EventArgument>();
	
	Event<EventArgument> testEvent = new Event<EventArgument>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		TestIncrement = 0;
	}

	@AfterEach
	void tearDown() throws Exception {
	}
    int TestValue = 3;
    int TestIncrement = 0;
    void Handler(Object sender, EventArgument e) {
    	TestIncrement++;
    	assertEquals(TestValue,e.getTestValue());
    }
	@Test
	void testInvoke() {
		var test1 = testEvent.Add((s,e)->{
			TestIncrement++;
			assertEquals(TestValue, e.getTestValue());
		});
		Assertions.assertFalse(testEvent.Empty());
		var test2 = testEvent.Add((s,e)->Handler(s,e));
		var test3 = testEvent.Add(new Delegate<EventArgument>((s,e)->{
			TestIncrement++;
		    assertEquals(TestValue,e.getTestValue());	  
		}));
		testEvent.invoke(this, new EventArgument(TestValue) );
		assertEquals(3,TestIncrement);
		var test4 = testEvent.Add(new Delegate<EventArgument>((s,e)->{
			fail("Should not run this delegate");
		}));
		testEvent.Remove(test4);
		testEvent.invoke(this, new EventArgument(TestValue) );
		testEvent.Remove(test1);
		testEvent.Remove(test2);
		testEvent.Remove(test3);
		Assertions.assertTrue(testEvent.Empty());
	}
}
