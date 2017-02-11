package myList;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class MyNode implements Comparable<Object> {

	private Object mValue = null;
	private MyNode mNext = null;

	MyNode() {
	}

	MyNode(Object value, MyNode next) {
		mValue = value;
		mNext = next;
	}

	Object getValue() {
		return mValue;
	}

	void setValue(Object mValue) {
		this.mValue = mValue;
	}

	MyNode getNext() {
		return mNext;
	}

	void setNext(MyNode mNext) {
		this.mNext = mNext;
	}

	@Override
	public int compareTo(Object o) {
		
	   return CompareToBuilder.reflectionCompare(this, o);

	}

}
