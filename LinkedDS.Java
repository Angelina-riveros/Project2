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
		for(int i =0; i < alphabet.length; i++){
			if(alphabet[i].equals(item)){
				place = i;
				break;
			}
		}
		if(place == -1){
			return;
		}
		if(size==0){
			firstItem = item;
			lastItem = item;
		}else{
			int firstItemPlace = -1;
			for(int i =0; i < alphabet.length; i++){
				if(alphabet[i].equals(firstItem)){
					firstItemPlace = i;
					break;
				}
			}
			Node newNode = new Node(place);
		//	if (array[firstItemPlace] == null){
		//		array[firstItemPlace] = newNode;
		//	}else{
		//		Node temp = array[firstItemPlace];
		//		while(temp.next != null){
		//			temp = temp.next;
		//		}
		//		temp.next = newNode;
		//	}
		//	lastItem = item;
			newNode.next = array[firstItemPlace];
			array[firstItemPlace]=newNode;
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
		if(isEmpty()){
			throw new EmptySequenceException("This is empty");
		}
		T temp = firstItem;
		if(size == 1){
			firstItem = null;
			lastItem = null;
		}else{
			int firstItemPlace = -1;
			for(int i =0; i < alphabet.length; i++){
				if(alphabet[i].equals(firstItem)){
					firstItemPlace = i;
					break;
				}
			}
			Node firstNode = array[firstItemPlace];
			array[firstItemPlace] = firstNode.next;
			if (array[firstItemPlace] != null) {
				firstItem = alphabet[array[firstItemPlace].item];
			} else {
				// Find the new first item by checking all lists
				firstItem = null;
				for (int i = 0; i < alphabet.length; i++) {
					if (array[i] != null) {
						firstItem = alphabet[i];
						break;
					}
				}
			}
		}
		size--;
		return temp;
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
		int itemPlace = -1;
		for(int i =0; i < alphabet.length; i++){
			if(alphabet[i].equals(item)){
				itemPlace = i;
				break;
			}
		}
		if(itemPlace == -1){
			return 0;
		}
		int frequency = 0;
		if(lastItem.equals(item) && lastItem != null){
			frequency++;
		}
		for(int i =0; i < alphabet.length; i++){
			Node current = array[i];
			while(current != null){
				if(alphabet[current.item].equals(item)){
					frequency++;
				}
				current = current.next;
			}
		}
	return frequency;
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
		int firstPlace = -1;
		int secondPlace = -1;
		for(int i =0; i < alphabet.length; i++){
			if(alphabet[i].equals(first)){
				firstPlace = i;
			}
			if(alphabet[i].equals(second)){
				secondPlace = i;

			}
		}
		if(firstPlace == -1 || secondPlace == -1){
			return 0;
		}
		int frequency = 0;
		Node current = array[firstPlace];
		while(current != null){
			if(current.item==secondPlace){
				frequency++;
			}
			current = current.next;
		}
		return frequency;
    }

	/**
	 * Returns an array of all unique successors of an item in the sequence.
	 * The running time should be better than or equal to 
	 * O(frequency of item in sequence * number of successors).
	 * @param item an item
	 * @return an array of all unique successors of item or null if 
     * item doesn't exist in the sequence.
	 */
	public T[] successors(T item){
        //TODO: implement this method
		int itemPlace = -1;
		for(int i =0; i < alphabet.length; i++){
			if(alphabet[i].equals(item)){
				itemPlace = i;
				break;
			}
		}
		if(itemPlace == -1){
			return null;
		}
		boolean[] hasNext = new boolean[alphabet.length];
		int hasNextCount = 0;
		Node current = array[itemPlace];
		while(current != null){
			if(!hasNext[current.item]){
				hasNext[current.item] = true;
				hasNextCount++;
			}
			current = current.next;
		}
		@SuppressWarnings("unchecked")
		T[] successors = (T[]) new Object[hasNextCount];
		int successorIndex = 0;
		for(int i =0; i < alphabet.length; i++){
			if(hasNext[i]){
				successors[successorIndex] = alphabet[i];
				successorIndex++;
			}
		}
		return successors;
    }

    private static class Node {
        private int item; //index in alphabet of item
        private Node next;
    
        private Node(int item){
            this.item = item;
            next = null;
        }
    }
}
