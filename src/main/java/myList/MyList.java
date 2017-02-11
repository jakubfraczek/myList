package myList;

import javax.swing.tree.MutableTreeNode;

public class MyList {

	private MyNode mHead = new MyNode();
	private int mListSize = 0;

	private MyNode begin() {
		return mHead;
	}

	private MyNode last() {
		MyNode temp = begin();
		while (temp.getNext() != null) {
			temp = temp.getNext();
		}
		return temp;
	}

	public int length() {
		return mListSize;
	}

	public void addAtFront(Object value) {
		MyNode newNode = new MyNode(value, mHead);
		mHead = newNode;
		mListSize++;
	}

	public void add(Object value) {
		mListSize++;
		MyNode newNode;
		if (mHead.getValue() == null) {
			newNode = new MyNode(value, mHead.getNext());
			mHead = newNode;
		} else {
			newNode = new MyNode(value, last().getNext());
			last().setNext(newNode);
		}
	}

	public void clear() {
		mHead.setNext(null);
		mHead.setValue(null);
		mListSize = 0;
	}

	public void remove(int index) {
		isOutOfBounds(index);
		if (index == 0) {
			mHead = findeNodeAtIndex(1);
			mListSize--;
		} else {
			MyNode beforeNode = findeNodeAtIndex(index - 1);
			beforeNode.setNext(findeNodeAtIndex(index + 1));
			mListSize--;
		}
	}

	public void removeValue(Object value) {
		int index = getIndexOf(value);
		remove(index);
	}

	public void removeAllOf(Object value) {
		MyNode temp = mHead;
		while (temp != null) {
			if (temp.getValue() == value) {
				MyNode next = temp.getNext();
				removeValue(value);
				temp = next;
			} else {
				temp = temp.getNext();
			}

		}
	}

	public Object getValue(int index) {
		MyNode myNode = findeNodeAtIndex(index);
		return myNode.getValue();
	}

	public Object getValue(MyNode myNode) {
		return myNode.getValue();
	}

	public void addAtIndex(int index, Object value) {
		MyNode temp = new MyNode(value, findeNodeAtIndex(index - 1).getNext());
		findeNodeAtIndex(index - 1).setNext(temp);
		mListSize++;
	}

	public int getIndexOf(Object value) {
		int index = 0;
		MyNode temp = mHead;
		while (temp != null) {
			if (value != temp.getValue()) {
				temp = temp.getNext();
			} else {
				return index;
			}
			index++;
		}
		return -1;
	}

	public MyList getAllIndexesOf(Object value) {
		int index = 0;
		MyList out = new MyList();
		MyNode temp = begin();
		while (temp != null) {
			if (temp.getValue().equals(value)) {
				out.add(index);
			}
			temp = temp.getNext();
			index++;
		}
		return out;
	}

	private void isOutOfBounds(int index) {
		if (index < 0 || index >= mListSize) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mListSize);
		}
	}

	private MyNode findeNodeAtIndex(int index) {
		isOutOfBounds(index);
		MyNode temp = begin();
		for (int i = 0; i < index; i++) {
			temp = temp.getNext();
		}
		return temp;
	}

	public void swap(int indexOfFirstElement, int indexOfSecoundElement) {
		isOutOfBounds(indexOfFirstElement);
		isOutOfBounds(indexOfSecoundElement);
		if (indexOfFirstElement == indexOfSecoundElement) {
			return;
		} else if (indexOfFirstElement == 0) {
			MyNode temp1 = findeNodeAtIndex(indexOfFirstElement);
			MyNode beforeTemp2 = findeNodeAtIndex(indexOfSecoundElement - 1);
			MyNode temp2 = beforeTemp2.getNext();
			MyNode next = temp1.getNext();

			temp1.setNext(temp2.getNext());
			beforeTemp2.setNext(temp1);
			temp2.setNext(next);
			mHead = temp2;

		} else if (indexOfSecoundElement == 0) {
			swap(indexOfSecoundElement, indexOfFirstElement);
		} else {
			MyNode beforeTemp1 = findeNodeAtIndex(indexOfFirstElement - 1);
			MyNode temp1 = beforeTemp1.getNext();
			MyNode beforeTemp2 = findeNodeAtIndex(indexOfSecoundElement - 1);
			MyNode temp2 = beforeTemp2.getNext();
			MyNode next = temp2.getNext();

			beforeTemp2.setNext(temp1);
			beforeTemp1.setNext(temp2);
			temp2.setNext(temp1.getNext());
			temp1.setNext(next);
		}
	}

	public void write() {
		MyNode temp = mHead;
		int index = 0;
		while (temp != null) {
			System.out.println("Index: " + index++ + ", value: " + temp.getValue());
			temp = temp.getNext();
		}
	}

	public void removeDuplicated() {
		MyNode temp1 = mHead;
		while (temp1 != null) {
			MyNode temp2 = mHead;
			while (temp2 != null) {
				if (temp2.getNext() != null && temp2.getValue() == temp2.getNext().getValue()) {
					int index = getIndexOf(temp2.getNext().getValue());
					remove(index);
				}
				temp2 = temp2.getNext();
			}
			temp1 = temp1.getNext();
		}
	}

	public MyList sort() {
		MyList out = new MyList();
		MyNode temp = mHead;
		MyNode[] in = new MyNode[mListSize];
		for (int i = 0; i < mListSize; i++) {
			in[i] = temp;
			temp = temp.getNext();
		}		
		quickSortInner(in, 0, in.length - 1);	
				
		for(MyNode i : in) {
			out.add(i.getValue());
		}
		return out;
	}

	private void quickSortInner(MyNode[] out, int low, int high) {
		if (low < high) {
			int p = partition(out, low, high);
			quickSortInner(out, low, p - 1);
			quickSortInner(out, p + 1, high);
		}
	}

	private int partition(MyNode[] out, int low, int high) {
		MyNode pivot = out[high];
		int i = low;
		MyNode temp;

		for (int j = low; j <= (high - 1); j++) {
			if (out[j].compareTo(pivot) > 0) {
				temp = out[i];
				out[i] = out[j];
				out[j] = temp;
				i += 1;
			}
		}
		temp = out[i];
		out[i] = out[high];
		out[high] = temp;
		return i;
	}
	
	public boolean isSorted() {
		boolean flag = true;
		MyNode temp = mHead;
		while (temp != null) {
			if (temp.getNext() != null && temp.compareTo(temp.getNext()) < 0) {
				flag = false;
				break;
			}
			temp = temp.getNext();
		}

		return flag;
	}

	public boolean isEmpty() {
		return mListSize == 0;
	}

}
