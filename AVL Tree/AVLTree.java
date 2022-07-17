/**
* Eyalgrinberg_207129792
* Matanshvide_201414315
*/

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with distinct integer keys and info.
 *
 */

public class AVLTree {

	private int height = 0;
	public AVLNode root = null;
	public AVLTree.AVLNode searchNodePointer;
	public int size = 0;

	public AVLTree(AVLNode myRoot) {//constructor
		this.root = myRoot;
		this.searchNodePointer = new AVLNode(); //pointer helps functions
		this.size = 1;
	}

	public AVLTree() {//default constructor
		this.root = null;
		this.searchNodePointer = null;
		this.size = 0;
	}
	
	/**
	 * public boolean empty()
	 *
	 * Returns true if and only if the tree is empty.
	 *
	 */
	public boolean empty() { // complexity O(1)
		if (this.root == null) {
			return true;
		}
		return false; 
	}

	/**
	 * public String search(int k)
	 *
	 * Returns the info of an item with key k if it exists in the tree. otherwise,
	 * returns null.
	 */
	public String search(int k) // complexity O(log(n))
	{
		if (this.root == null) { // empty tree
			return null;
		}
		if (this.root.getKey() == k) { // found
			searchNodePointer = this.root; //point here for future functions
			return this.root.info;
		}
		if (this.root.getKey() > k) { // go left
			if (this.root.getLeft().getHeight() == -1) {
				searchNodePointer = this.root;
				return null;
			}
			AVLTree LeftTree = new AVLTree((AVLTree.AVLNode) this.root.getLeft()); // Left sub-tree
			String result = LeftTree.search(k);
			this.searchNodePointer = LeftTree.searchNodePointer; // update end of tree to enable future insertion
			return result;
		} else { // go right
			if (this.root.getRight().getHeight() == -1) {
				searchNodePointer = this.root;
				return null;
			}
			AVLTree rightTree = new AVLTree((AVLTree.AVLNode) this.root.getRight()); // right sub-tree
			String result = rightTree.search(k);
			this.searchNodePointer = rightTree.searchNodePointer; // update end of tree to enable insertion
			return result;
		}
	}

	/**
	 * public int insert(int k, String i)
	 *
	 * Inserts an item with key k and info i to the AVL tree. The tree must remain
	 * valid, i.e. keep its invariants. Returns the number of re-balancing
	 * operations, or 0 if no re-balancing operations were necessary. A
	 * promotion/rotation counts as one re-balance operation, double-rotation is
	 * counted as 2. Returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String i) { // complexity O(log(n))
		if (this.empty()) { // first insertion
			this.root = new AVLNode(k, i);
			this.root.setHeight(0);
			this.root.setRealNode();
			this.size += 1;
			return 0;
		}
		int cnt = 0; //for output
		String searchResult = this.search(k); //set pointer in relevant place
		if (searchResult != null) { // already exist
			return -1;
		}
		this.size += 1; // node inserted --> size increased
		if (k > searchNodePointer.getKey()) { // new node is right child
			searchNodePointer.setRight(new AVLNode(k, i));
			this.searchNodePointer.getRight().setHeight(0);
			this.searchNodePointer.getRight().setParent(this.searchNodePointer); // connect to father
			searchNodePointer.getRight().setRealNode(); // create virtual sons
		} else { // symetric case
			searchNodePointer.setLeft(new AVLNode(k, i));
			this.searchNodePointer.getLeft().setHeight(0);
			this.searchNodePointer.getLeft().setParent(this.searchNodePointer); // connect to father
			searchNodePointer.getLeft().setRealNode(); // create virtual sons
		}
		if (this.searchNodePointer.getHeight() == 0) { // case A: the parent is a leaf
			int checkPromo = this.searchNodePointer.rank; //save current rank to know if promoted
			this.searchNodePointer.setHeight(1 + Math.max(this.searchNodePointer.getLeft().getHeight(),this.searchNodePointer.getRight().getHeight())); // promote if necessary
			if (this.searchNodePointer.rank!=checkPromo) {
				cnt+=1;
			}
			cnt += this.searchNodePointer.heightCheckInsert(); // check balance and add operations to output
		} else { // case B: the parent is unaric
			if (this.searchNodePointer.getKey() > k) {
				this.searchNodePointer.getRight().setHeight(0);
				cnt = this.searchNodePointer.heightCheckInsert(); // check balance and add operations to output
			} else {
				this.searchNodePointer.getLeft().setHeight(0);
				cnt = this.searchNodePointer.heightCheckInsert(); // check balance and add operations to output

			}
			while ((this.searchNodePointer != null) && (this.searchNodePointer.getParent() != null)) { // keep promoting if necessary
				this.searchNodePointer.parent.setHeight(1 + Math.max(this.searchNodePointer.getParent().getRight().getHeight(),this.searchNodePointer.getParent().getLeft().getHeight())); // father rank update
				cnt += this.searchNodePointer.parent.heightCheckInsert(); // check balance and add operations to output
				this.searchNodePointer = this.searchNodePointer.parent; // update pointer going up
			}
		}
		while (this.root.getParent() != null) { // adjust root if necessary
			this.root = (AVLTree.AVLNode) this.root.getParent();
		}
		return cnt; 
	}

	/**
	 * public int delete(int k)
	 *
	 * Deletes an item with key k from the binary tree, if it is there. The tree
	 * must remain valid, i.e. keep its invariants. Returns the number of
	 * re-balancing operations, or 0 if no re-balancing operations were necessary. A
	 * promotion/rotation counts as one re-balance operation, double-rotation is
	 * counted as 2. Returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) { // complexity O(log(n))
		int cnt = 0;
		String searchResult = this.search(k); //set pointer on relevant node
		if (searchResult == null) { // doesn't exist
			return -1;
		}
		if (!this.searchNodePointer.left.isRealNode()) {// this is unaric or leaf
			if (this.searchNodePointer.parent.key > this.searchNodePointer.key) { // this is left child
				this.searchNodePointer.parent.left = this.searchNodePointer.right; //connect father to my older son
				this.searchNodePointer.right.parent = this.searchNodePointer.parent;//connect older son to new father
			} else { // this is right child
				this.searchNodePointer.parent.right = this.searchNodePointer.right;//connect father to my older son
				this.searchNodePointer.right.parent = this.searchNodePointer.parent;//connect older son to new father
			}
			this.searchNodePointer = this.searchNodePointer.parent; //adjust pointer one up
			while (this.searchNodePointer != null) { // check all the way up
				int checkDemotion = this.searchNodePointer.rank; //save current rank to check difference
				this.searchNodePointer.rank = 1 + Math.max(this.searchNodePointer.left.rank, this.searchNodePointer.right.rank);
				if (checkDemotion!=this.searchNodePointer.rank) { //add balance step if rank changed
					cnt +=1;
				}
				cnt += this.searchNodePointer.heightCheckDelete(); //check balance and add operations
				if (this.searchNodePointer.parent == null) {
					this.root = this.searchNodePointer; 
				}
				this.searchNodePointer = this.searchNodePointer.parent; //adjust pointer one up
			}
			this.size -= 1;
			return cnt;
		} else { // this is internal node
			int newKey = this.searchNodePointer.getPredecessor().getKey(); //prepare predecessor for swap
			String newInfo = this.searchNodePointer.getPredecessor().getValue(); // keep predecessor fields
			cnt += this.delete(newKey); // delete predecessor and check way up
			String originalPointer = this.search(k); // point to the original node
			this.searchNodePointer.key = newKey;
			this.searchNodePointer.info = newInfo; // replace current values with predecessor's values
		}
		return cnt; 
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty.
	 */
	public String min() { // complexity O(log(n))

		if (this.empty()) {
			return null;
		}
		if (this.root.getLeft().getHeight() == -1) { //reached left end
			return this.root.getValue();
		}
		AVLTree LeftTree = new AVLTree((AVLTree.AVLNode) this.root.getLeft()); // Left sub-tree
		return LeftTree.min(); 
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty.
	 */
	public String max() { // complexity O(log(n))
		if (this.empty()) {
			return null;
		}
		if (this.root.getRight().getHeight() == -1) { //reached right end
			return this.root.getValue();
		}
		AVLTree rightTree = new AVLTree((AVLTree.AVLNode) this.root.getRight()); // right sub-tree
		return rightTree.max(); 
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty.
	 */
	public int[] keysToArray() { // complexity O(n) ---> no edge is visited more then twice
		if (this.empty()) {
			return new int[0];
		}
		int cnt = 1;
		AVLNode hiker = this.root; // pointer
		while (hiker.left != null) { //go to min
			hiker.left.parent = hiker;
			hiker = hiker.left;
		}
		hiker = hiker.parent;
		if (this.size==1) {//one node tree
			int[] res = {this.root.key};
			return res;
		}
		while (hiker.getSuccessor()!=null) {//update size
			cnt+=1;
			hiker = hiker.getSuccessor();
		}
		hiker = this.root; // pointer
		while (hiker.left != null) { // go to min
			hiker.left.parent = hiker;
			hiker = hiker.left;
		}
		hiker = hiker.parent;
		int[] resultArray = new int[cnt];
		for (int i = 0; i < cnt; i++) { // tree traverse in order
			resultArray[i] = hiker.key;
			if (i == cnt - 1) {
				break;
			}
			hiker = hiker.getSuccessor();
		}
		this.size = cnt;
		return resultArray; 
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] infoToArray() { // complexity O(n) ---> no edge is visited more then twice
		if (this.empty()) {
			return new String[0];
		}
		int cnt = 1; 
		AVLNode hiker = this.root; // pointer
		while (hiker.left != null) { // go to min
			hiker = hiker.left;
		}
		hiker = hiker.parent;
		if (this.size==1) {//one node tree
			String[] res = {this.root.info};
			return res;
		}
		while (hiker.getSuccessor()!=null) { //update size
			cnt+=1;
			hiker = hiker.getSuccessor();
		}
		hiker = this.root; // pointer
		while (hiker.left != null) { // go to min
			hiker = hiker.left;
		}
		hiker = hiker.parent;
		String[] resultArray = new String[cnt];
		for (int i = 0; i < cnt; i++) { // tree traverse in order
			resultArray[i] = hiker.getValue();
			if (i == cnt - 1) {
				break;
			}
			hiker = hiker.getSuccessor();
		}
		this.size = cnt;
		return resultArray; 
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 */
	public int size() { // complexity O(1)
		return this.size; 
	}


	/**
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 */
	public IAVLNode getRoot() { // complexity O(1)
		if (this.empty()) {
			return null;
		}
		return this.root;
	}

	/**
	 * public AVLTree[] split(int x)
	 *
	 * splits the tree into 2 trees according to the key x. Returns an array [t1,
	 * t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * 
	 * precondition: search(x) != null (i.e. you can also assume that the tree is
	 * not empty) postcondition: none
	 */
	public AVLTree[] split(int x) { // complexity O(log(n))
		AVLTree bigTree = new AVLTree();
		AVLTree smallTree = new AVLTree();
		AVLTree[] result = { bigTree, smallTree };
		if ((this.root.key == x) && (this.size == 1)) {
			return result;
		}
		this.search(x);
		bigTree.root = this.searchNodePointer.right; //right son
		smallTree.root = this.searchNodePointer.left;//left son
		this.searchNodePointer.right.parent = null; //cancel pointer connections to create joint node
		this.searchNodePointer.left.parent = null;
		this.searchNodePointer.right = null;
		this.searchNodePointer.left = null;
		AVLNode nextPointer = this.searchNodePointer.parent; //keep father for future joins
		while (nextPointer != null) {
			if (nextPointer.key < this.searchNodePointer.key) { // I am older brother
				AVLTree toJoin = new AVLTree(nextPointer.left); //future subtree
				AVLNode tmp = nextPointer; //next pointer to check
				toJoin.root.parent = null; //clear root's parent field
				nextPointer = nextPointer.parent;//pointer one up
				tmp.left = null;//disconnect joint node
				tmp.right = null;
				tmp.parent = null;
				smallTree.join(tmp, toJoin);//join current subtree with future father and brother
				smallTree.root.left.parent = smallTree.root; //connect new sons to root
				smallTree.root.right.parent = smallTree.root;
			}else { //I am younger son
				AVLTree toJoin = new AVLTree(nextPointer.right);  //future subtree
				AVLNode tmp = nextPointer;//next pointer to check
				toJoin.root.parent = null;//clear root's parent field
				nextPointer = nextPointer.parent;//pointer one up
				tmp.left = null;//disconnect joint node
				tmp.right = null;
				tmp.parent = null;
				bigTree.join(tmp, toJoin);//join current subtree with future father and brother
				bigTree.root.left.parent = bigTree.root;//connect new sons to root
				bigTree.root.right.parent = bigTree.root;
			}
		}
		if (bigTree.root.parent != null) { //make sure roots have no parents
			bigTree.root.parent = null;
		}
		if (smallTree.root.parent != null) {
			smallTree.root.parent = null;
		}
		return result;
	}



	/**
	 * public int join(IAVLNode x, AVLTree t)
	 *
	 * joins t and x with the tree. Returns the complexity of the operation
	 * (|tree.rank - t.rank| + 1).
	 *
	 * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be
	 * empty (rank = -1). postcondition: none
	 */

	public int join(IAVLNode x, AVLTree t) { // complexity O(log(n))
		if (this.empty() && t.empty()) { // both trees are empty
			this.insert(x.getKey(), x.getValue());
			return 0;
		}
		if ((this.empty()) && (!t.empty())) { //one tree is empty
			t.insert(x.getKey(), x.getValue());
			this.root = t.root;
			this.size = t.size;
			return 0;
		}
		if ((!this.empty()) && (t.empty())) {//other tree is empty
			this.insert(x.getKey(), x.getValue());
			return 0;
		}
		AVLNode newX = ((AVLTree.AVLNode) x);//joint node
		if (this.empty()) {//only one is empty
			int result = t.root.rank + 1;
			t.insert(x.getKey(), x.getValue());
			return result;
		}
		if (t.empty()) {
			int result = this.root.rank + 1;
			this.insert(x.getKey(), x.getValue());
			return result;
		}

		AVLNode highTreeNode = t.root;//initials high and low tree randomly
		AVLNode shortTreeNode = this.root;
		if (t.root.rank < this.root.rank) { // change initiation if myTree tree is higher
			highTreeNode = this.root;
			shortTreeNode = t.root;
		}
		int result = highTreeNode.rank - shortTreeNode.rank + 1;//rank difference
		while (highTreeNode.rank > shortTreeNode.rank) { //finding joining point
			if (highTreeNode.key > newX.key) { // go down right
				highTreeNode = highTreeNode.left;
			} else { // go down left
				highTreeNode = highTreeNode.right;
			}
		}
		if (highTreeNode.parent == null) {// both trees have same rank ---->joint is root
			if (highTreeNode.key > newX.key) {
				newX.right = highTreeNode;
				newX.left = shortTreeNode;
				newX.setSize();
			} else {
				newX.left = highTreeNode;
				newX.right = shortTreeNode;
				newX.setSize();
			}
		} else if (highTreeNode.parent.key > newX.key) { // high tree has higher keys
			highTreeNode.parent.left = newX; //joint is son of node in the high tree above joining point
			newX.parent = highTreeNode.parent;//connect
			newX.right = highTreeNode;//joint is father of high tree joining point
			highTreeNode.parent = newX;//connect
			newX.left = shortTreeNode;//joint is father of low tree joining point
			newX.setSize();
			newX.parent.setSize();//updates
			newX.rank = 1 + Math.max(newX.left.rank, newX.right.rank);
			shortTreeNode.parent = newX; // connections done
			newX.setHeight(1 + Math.max(newX.left.rank, newX.right.rank));//update rank
		} else { // short tree has higher keys
			highTreeNode.parent.right = newX;//joint is son of node in the high tree above joining point
			newX.parent = highTreeNode.parent;
			newX.left = highTreeNode;//joint is father of high tree joining point
			highTreeNode.parent = newX;
			newX.right = shortTreeNode;//joint is father of short tree(high keys) joining point
			newX.setSize();//updates
			newX.parent.setSize();
			newX.rank = 1 + Math.max(newX.left.rank, newX.right.rank);
			shortTreeNode.parent = newX; // connections done
			newX.setHeight(1 + Math.max(newX.left.rank, newX.right.rank));// update rank
		}
		AVLNode climber = new AVLNode(); //will check ranks and balance
		if (newX.parent != null) { // check if rotations needed
			climber = newX.parent;
			while (climber != null) {//check all the way up
				climber.setHeight(1 + Math.max(climber.left.rank, climber.right.rank));
				climber.heightCheckInsert();//check balance
				if (climber.parent == null) {
					break; // climber is root
				}
				climber = climber.parent;
			}
		} else { // newX will be root
			climber = newX;
			climber.setHeight(1 + Math.max(climber.left.rank, climber.right.rank));
		}
		this.root = climber;//set root
		this.root.right.parent = climber;//ensure connections
		this.root.left.parent = climber;
		this.size += 1 + t.size;
		this.root.sizeNode = this.size;
		return result;
	}

	/**
	 * public interface IAVLNode ! Do not delete or modify this - otherwise all
	 * tests will fail !
	 */
	public interface IAVLNode {
		public int getKey(); // Returns node's key (for virtual node return -1).

		public String getValue(); // Returns node's value [info], for virtual node returns null.

		public void setLeft(IAVLNode node); // Sets left child.

		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.

		public void setRight(IAVLNode node); // Sets right child.

		public IAVLNode getRight(); // Returns right child, if there is no right child return null.

		public void setParent(IAVLNode node); // Sets parent.

		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.

		public boolean setRealNode(); // Creates virtual sons. Our addition******

		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.

		public void setHeight(int height); // Sets the height of the node.

		public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree (for example AVLNode), do
	 * it in this file, not in another file.
	 * 
	 * This class can and MUST be modified (It must implement IAVLNode).
	 */
	public class AVLNode implements IAVLNode {
		public String info = "\0";
		public int key = -1;

		public AVLNode(int key, String info) {
			this.info = info;
			this.key = key;
		}

		public AVLNode() { // virtual node
			this.info = null;
			this.key = -1;
			this.rank = -1;
		}

		private AVLNode right = null;
		private AVLNode left = null;
		private AVLNode parent = null;
		private AVLNode searchNodePointer = null;
		private int rank = -1;
		private int sizeNode = 0;

		public int getKey() {
			return this.key; 
		}

		public String getValue() {
			return this.info; 
		}

		public void setLeft(IAVLNode node) {
			this.left = (AVLNode) node;
			return; 
		}

		public IAVLNode getLeft() {
			return this.left; 
		}

		public void setRight(IAVLNode node) {
			this.right = (AVLNode) node;
			
		}

		public IAVLNode getRight() {
			return this.right; 
		}

		public void setParent(IAVLNode node) {
			this.parent = (AVLNode) node;
			return; 
		}

		public IAVLNode getParent() {
			return this.parent; 
		}

		public boolean setRealNode() {
			if (this.rank != -1) {
				this.right = new AVLNode();
				this.right.parent = this;
				this.left = new AVLNode();
				this.left.parent = this;
				return true; 
			} else {
				return false;
			}
		}

		public boolean isRealNode() {
			if (this.rank != -1) {
				return true;
			}
			return false;
		}

		public void setHeight(int height) {

			this.rank = height;
			return; 
		}

		public int getHeight() {
			return this.rank; 
		}

		public int heightCheckInsert() { // complexity O(1) what error case
			int cnt = 0;
			int BF = this.getBF();
			if (((BF == 1) || (BF == -1)) && (this.parent != null)) { // rank difference 0,1 --->// promote parent
				int checkPromotion = this.parent.rank;
				this.parent.setHeight(1 + Math.max(this.parent.left.rank, this.parent.right.rank));
				if (checkPromotion!=this.parent.rank) { //add balance move if rank changed
					cnt+=1;
				}
																									
				this.setSize(); 
				cnt += this.parent.heightCheckInsert();
			} else if (BF == 2) { // left imbalance
				if (this.left.getBF() == -1) { // left then right rotation
					cnt += 2;
					rotateLeft(this.left.right);
					rotateRight(this.left);
				} else { // right rotation
					cnt += 1;
					rotateRight(this.left);

				}
			} else if (BF == -2) { // right imbalance
				if ((this.right.getBF() == 1)) {// right then left rotation
					cnt += 2;
					rotateRight(this.right.left);
					rotateLeft(this.right);
				} else { // left rotation
					cnt += 1;
					rotateLeft(this.right);
				}
			}
			return cnt;
		}

		public int heightCheckDelete() { // complexity O(1)
			int cnt = 0;
			int BF = this.getBF();
			if (BF == 2) { // left imbalance
				if (this.left.getBF() == -1) { // left then right
					rotateLeft(this.left.right);
					rotateRight(this.left);
					cnt += 2;
				} else { // BF=0 or BF=1 --> only right rotation
					rotateRight(this.left);
					cnt += 1;
				}
				return cnt;
			} else if (BF == -2) {// right imbalance
				if (this.right.getBF() == 1) { // right then left
					rotateRight(this.right.left);
					rotateLeft(this.right);
					cnt += 2;
				} else {// BF=0 or BF=-1 --> only left rotation
					rotateLeft(this.right);
					cnt += 1;
				}
				return cnt;
			} else { // continue checking upwards
				this.setSize();
				AVLNode tmp = this;
				while (tmp != null) {
					tmp.setSize();
					tmp = tmp.parent;
				}
				return cnt;
			}
		}

		private int getBF() { // complexity O(1)
			if (this.rank == -1) {
				return -3;
			}
			return this.left.rank - this.right.rank;
		}

		private void rotateRight(AVLNode son) { //O(1)
			AVLNode greatGrandpa = son.parent.parent; // save top

			son.parent.left = son.right; // turn left sub-tree into father's right-tree
			son.right.parent = son.parent; // turn former son into grandson
			son.right = son.parent; // turn son to father
			son.right.parent = son;// turn father to son
			son.parent = greatGrandpa; // connect to top ---> nodes are placed
			son.right.setHeight(1 + Math.max(son.right.right.rank, son.right.left.rank)); // demote
			son.setHeight(1 + Math.max(son.right.rank, son.left.rank));// promote 
			if (greatGrandpa != null) {
				if (greatGrandpa.key > son.key)
					greatGrandpa.left = son;
				else
					greatGrandpa.right = son;

			}
			son.right.setSize();
			AVLNode tmp = son;
			while (tmp != null) {
				tmp.setSize();
				tmp = tmp.parent;
			}
		}

		private void rotateLeft(AVLNode son) { //O(1)
			AVLNode greatGrandpa = son.parent.parent; // save top
			son.parent.right = son.left; // turn left sub-tree into father's left-tree
			son.left.parent = son.parent; // turn former son into grandson
			son.left = son.parent; // turn son to father
			son.left.parent = son;// turn father to son
			son.parent = greatGrandpa; // connect to top ---> nodes are placed
			son.left.setHeight(1 + Math.max(son.left.right.rank, son.left.left.rank)); // demote
			son.setHeight(1 + Math.max(son.right.rank, son.left.rank));// promote 
			if (greatGrandpa != null) {
				if (greatGrandpa.key > son.key)
					greatGrandpa.left = son;
				else
					greatGrandpa.right = son;
			}
			son.left.setSize();
			AVLNode tmp = son;
			while (tmp != null) {
				tmp.setSize();
				tmp = tmp.parent;
			}

		}

		private AVLNode getPredecessor() { //O(logn) 
			AVLNode tmp = new AVLNode(this.getKey(), "");
			tmp.parent = this.parent;
			tmp.left = this.left;
			if (tmp.getLeft() == null) {
				while (tmp.parent.key > tmp.key) { // this is left child --> predecessor is up
					tmp = tmp.parent;
				}
				return tmp.parent;
			} else { // this has a left child --> predecessor is down
				tmp = tmp.left;
				while (tmp.right.key != -1) {
					tmp = tmp.right;
				}
				return tmp;
			}
		}

		private AVLNode getSuccessor() { //O(logn) 
			AVLNode tmp = new AVLNode(this.getKey(), "");
			tmp.parent = this.parent;
			tmp.right = this.right;
			if (tmp.getParent() == null) {
				tmp = tmp.right;
				while ((tmp.left!=null)&&(tmp.left.rank != -1)) { 
					tmp = tmp.left;
				}
				return tmp;
			}
			if ((tmp.getRight()!=null)&&(tmp.getRight().getHeight() == -1)) { 
				while ((tmp.parent != null) && (tmp.parent.key < tmp.key)) { // this is right child --> successor is up
					tmp = tmp.parent;
				}
				return tmp.parent;
			} else if(tmp!=null) { // this has a right child --> successor is down
				tmp = tmp.right;
				while ((tmp.left!=null)&&(tmp.left.key != -1)) {
					tmp = tmp.left;
				}
				return tmp;
			}
			return tmp;
		}

		private void setSize() { //O(1)
			if (this.isRealNode()) {
				this.sizeNode = this.right.sizeNode + this.left.sizeNode;
			}
			if (this.left.rank != -1) {
				this.sizeNode += 1;
				if (this.right.rank != -1) {
					this.sizeNode += 1;
				}
			}

		}
	}
}