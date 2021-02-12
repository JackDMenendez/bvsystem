package org.bv.com.system.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bv.com.datastructures.Element;
import org.bv.com.datastructures.GStack;
import org.bv.com.datastructures.GenStack;
import org.bv.com.datastructures.GenStackElement;
import org.bv.com.datastructures.Stackable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenStackTest {
	private static int counter = 0;
	class TestData implements Stackable<TestData> {
		private final Element<TestData> element;
		private int id = 0;
		public TestData() {
			id = ++counter;
			element = new Element<>(this);
		}
		public int getId() { return id; }
		@Override
		public GenStackElement<TestData> stackElement() {
			return element;
		}
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testStack() {
		final GStack<TestData> stack = new GenStack<>("TEST");
		final var counterMax = 200;
		assertNull(stack.peek());
		for(var i=0; i<counterMax; i++) {
			stack.push(new TestData());
		}
		assertTrue(null != stack.peek());
		assertEquals(counterMax, stack.peek().getId());
		for(var i=counterMax;i>0;i--) {
			assertTrue(null != stack.peek());
			final var dataIdx = stack.pop().getId();
			assertEquals(i,dataIdx);
		}
		assertNull(stack.peek());
		System.out.println(stack.toString());
	}

}
