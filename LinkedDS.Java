public class LinkedDS<T> implements SequenceInterface<T> {

		private Node[] array; //1-D array of linked lists
		private int size; //the number of items in the sequence
		private T[] alphabet; //the possible item values (e.g., the decimal digits)
		private T firstItem; //the first item
		private T lastItem; //the last item

		public LinkedDS(T[] alphabet){
		//TODO: implement this constructor
		this.alphabet = alphabet;
		this.size = 0;
		this.array = new Node[alphabet.length];
		this.firstItem = null;
		this.lastItem = null;
		}


		/** Add a new Object to the logical start of the sequence in O(1) time
	 * @param item the item to be added.
	 */
	public void push(T item){
		//TODO: implement this method
		int place = -1;
		for (int i = 0; i < alphabet.length; i++) {
				if (alphabet[i].equals(item)) {
						place = i;
						break;
				}
		}
		if (place == -1) {
				return;
		}
		if (size == 0) {
				firstItem = item;
				lastItem = item;
		} else {
				int firstItemPlace = -1;
				for (int i = 0; i < alphabet.length; i++) {
						if (alphabet[i].equals(firstItem)) {
								firstItemPlace = i;
								break;
						}
				}
				Node newNode = new Node(firstItemPlace);
				newNode.next = array[place];
				array[place] = newNode;
				firstItem = item;
		}
		size++;
	}

		/** Delete the item at the logical start of the sequence in O(1) item
	 * @return the deleted item
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public T pop(){
				//TODO: implement this method
		if (isEmpty()) {
			throw new EmptySequenceException("Sequence is empty");
		}

		T result = firstItem;

		// Find the index of first item in alphabet
		int firstIndex = -1;
		for (int i = 0; i < alphabet.length; i++) {
				if (alphabet[i].equals(firstItem)) {
						firstIndex = i;
						break;
				}
		}

		// Handle case when sequence will become empty
		if (size == 1) {
				firstItem = null;
				lastItem = null;
		} else {
				Node firstSuccessor = array[firstIndex];

				if (firstSuccessor!=null) {
						firstItem = alphabet[firstSuccessor.item];
						array[firstIndex] = firstSuccessor.next;
				} else {
						firstItem = null;
						lastItem = null;
				}
		}

		size--;
		return result;
		}



	/** Check if the sequence is empty in O(1) time
	 * @return true if the sequence is empty, and false otherwise
	 */
	public boolean isEmpty(){
		//TODO: implement this method
				return size==0;
		}

	/** Return the number of items in the sequence in O(1) time
	 * @return the number of items currently in the sequence
	 */
	public int size(){
		//TODO: implement this method
				return size;
		}

	/**
	 * @return the the logically first item in the sequence in O(1) time
	 */
	public T first(){
		//TODO: implement this method
		if(isEmpty()){
			return null;
		}
		return firstItem;
		}

	/**
	 * @return the the logically last item in the sequence in O(1) time
	 */
	public T last(){
		//TODO: implement this method
		if (isEmpty()) {
			return null;
		}
		return lastItem;
		}

		/** Return the number of times in the sequence that an item appears.
		 * The running time is O(frequency of item in sequence).
	 * @param item an T item
	 * @return the number of occurences in the sequence of item
	 */
	public int getFrequencyOf(T item){
				//TODO: implement this method
		if (isEmpty()) {
			return 0;
		}
		int itemIndex = -1;
		for (int i = 0; i < alphabet.length; i++) {
			if (alphabet[i].equals(item)) {
					itemIndex = i;
					break;
			}
		}
		if (itemIndex == -1) {
			return 0;
		}
		int count = 0;
		if (firstItem.equals(item)) {
			count++;
		}
		for (int i = 0; i < array.length; i++) {
			Node current = array[i];
			while (current != null) {
					if (current.item == itemIndex) {
							count++;
					}
					current = current.next;
			}
		}

		return count;
	}


	/** Return the number of times in the sequence that an ordered pair of 
	 * items appear in the sequence. The running time is O(frequency of first 
	 * item).
	 * @param first the first item in the ordered pait
	 * @param second the second item in the ordered pair
	 * @return the number of occurences in the sequence of (first, second)
	 */
	public int getFrequencyOf(T first, T second){
				//TODO: implement this method
		if (isEmpty() || size == 1) {
			return 0;
		}
		int firstIndex = -1;
		int secondIndex = -1;
		for (int i = 0; i < alphabet.length; i++) {
			if (alphabet[i].equals(first)) {
					firstIndex = i;
			}
			if (alphabet[i].equals(second)) {
					secondIndex = i;
			}
		}
		if (firstIndex == -1 || secondIndex == -1) {
			return 0;
		}
		int count = 0;
		Node current = array[firstIndex];
		while (current != null) {
			if (current.item == secondIndex) {
					count++;
			}
			current = current.next;
		}
		return count;
		}

	/**
	 * Returns an array of all unique successors of an item in the sequence.
	 * The running time should be better than or equal to 
	 * O(frequency of item in sequence * number of successors).
	 * @param item an item
	 * @return an array of all unique successors of item or null if 
		 * item doesn't exist in the sequence.
	 */
	@SuppressWarnings("unchecked")
	public T[] successors(T item){
				//TODO: implement this method
		int itemIndex = -1;
		for (int i = 0; i < alphabet.length; i++) {
				if (alphabet[i].equals(item)) {
						itemIndex = i;
						break;
				}
		}
		if (itemIndex == -1) {
				return null;
		}
		boolean[] hasSuccessor = new boolean[alphabet.length];
		int uniqueCount = 0;
		Node current = array[itemIndex];
		while (current != null) {
				if (!hasSuccessor[current.item]) {
						hasSuccessor[current.item] = true;
						uniqueCount++;
				}
				current = current.next;
		}
		T[] result = (T[]) java.lang.reflect.Array.newInstance(
				alphabet.getClass().getComponentType(), uniqueCount);
		int index = 0;
		for (int i = 0; i < alphabet.length; i++) {
				if (hasSuccessor[i]) {
						result[index++] = alphabet[i];
				}
		}
		return result;
		}
		private static class Node {
				private int item;
				private Node next;
				private Node(int item){
						this.item = item;
						next = null;
				}
		}
}
