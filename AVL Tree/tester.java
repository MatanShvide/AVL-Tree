import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;









public class tester {
	
	

	
	public static void main(String[] args) {
		Random treeSize = new Random();
		int n = 16000;
		//int treeSize1 = treeSize.nextInt(100); //size of first tree
		//int treeSize2 = treeSize.nextInt(100); //size of second tree
		int treeSize3 = n;
		//AVLTree tree1 = new AVLTree();
		//AVLTree tree2 = new AVLTree();
		AVLTree tree3 = new AVLTree(); 
		//int[] orderOfInsertions1 = new int[treeSize1];
		//int[] orderOfInsertions2 = new int[treeSize2];
		int[] orderOfInsertions3 = new int[treeSize3];
		//int [] bank1= new int[1000];
		//int [] bank2= new int[1000];
		int [] bank3= new int[n];
		/*for (int i=0; i<treeSize1; i++) { //create first tree
			int key = tester.getRandomInt1(treeSize, bank1); //get random number under 500
			String info = "String of" + key;
			tree1.insert(key, info);
			orderOfInsertions1[i] = key; //keep order of insertions for deeper checking if necessary
		}
		for (int i=0; i<treeSize2; i++) { //create second tree
			int key = tester.getRandomInt2(treeSize, bank2); //get random number above 500
			String info = "String of" + key;
			tree2.insert(key, info);
			orderOfInsertions2[i] = key; //keep order of insertions for deeper checking if necessary
		}*/
		//int[] tree3Inserts = {513, 977, 558, 702, 776, 839, 840, 592, 529, 965, 567, 966, 774, 617, 825, 952, 768, 879, 540, 859, 808, 703, 714, 589, 566, 853, 829, 883, 555, 743};
		for (int i=0; i<treeSize3; i++) { //create second tree
			int key = getRandom3(treeSize, bank3);//tester.getRandomInt2(treeSize, bank3); //get random number above 500
			String info = "String of" + key;
			tree3.insert(key, info);
			orderOfInsertions3[i] = key; //keep order of insertions for deeper checking if necessary
		}
		//int[] toCheckOrder1 = tree1.keysToArray(); //tree one result in order
		//int[] toCheckOrder2 = tree2.keysToArray(); //tree two result in order
		//int[] toCheckOrder3 = tree3.keysToArray(); //tree two result in order
		//int n = toCheckOrder1.length;
		//int m = toCheckOrder2.length;
		/*for (int i=0; i<n; i++) {
			if ((i!=n-1)&&(toCheckOrder1[i]>toCheckOrder1[i+1])) { // let me know who is not in order
				System.out.println("node" + i + "not in order");
			}
		}*/
		/*System.out.println("---------------Tree1--------------");
		System.out.println("Tree1 order of insertions");
		System.out.println(Arrays.toString(orderOfInsertions1)); //insertion order originally
		System.out.println("Tree1 inserted:");
		System.out.println(Arrays.toString(toCheckOrder1)); //tree one in order
		if (orderOfInsertions1.length!=toCheckOrder1.length) { // if not the same length - some wasn't inserted  - error
			System.out.println((orderOfInsertions1.length - toCheckOrder1.length) + " Missing nodes weren't inserted" );
			}
		if (!tree1.empty()) {
			tree1.print(tree1.root, 0); //checking for imbalance or disconnections
		}
		System.out.println("Tree1 checked for order, connections and BF");
		
		
		
		for (int i=0; i<m; i++) { // let me know who is not in order
			if ((i!=m-1)&&(toCheckOrder2[i]>toCheckOrder2[i+1])) {
				System.out.println("node" + i + "not in order");
			}
		}
		System.out.println("---------------Tree2--------------");
		System.out.println("Tree2 order of insertions");
		System.out.println(Arrays.toString(orderOfInsertions2)); //insertion order originally
		System.out.println("Tree2 inserted:");
		System.out.println(Arrays.toString(toCheckOrder2)); //tree two in order
		if (orderOfInsertions2.length!=toCheckOrder2.length) { // if not the same length - some wasn't inserted  - error
			System.out.println((orderOfInsertions2.length - toCheckOrder2.length) +" Missing node wasn't inserted");
			}
		if (!tree2.empty()) {
			tree2.print(tree2.root, 0); //checking for imbalance or disconnections
		}
		System.out.println("Tree2 checked for order, connections and BF");
		
		int joinKey =  500;
		AVLTree jointRoot = new AVLTree();
		jointRoot.insert(joinKey, "String of" + joinKey);
		tree2.join(jointRoot.root, tree1); //join the trees with 500
		System.out.println("---------------JointTree--------------");
		tree2.print(tree2.root, 0); //check new tree for imbalance or disconnections
		int[] jointOrder = tree2.keysToArray();
		System.out.println(Arrays.toString(jointOrder)); // new tree in order
		int l = jointOrder.length;
		for (int i=0; i<l; i++) {
			if ((i!=l-1)&&(jointOrder[i]>jointOrder[i+1])) { // who is not in order
				System.out.println("nodes" + "index " + (i) + " " + jointOrder[i] + "index " + (i+1) + " " + jointOrder[i] + " not in order");
			}
		}
		if (jointOrder.length!=((toCheckOrder2.length)+(toCheckOrder1.length)+1)) { //are we missing nodes?
			System.out.println(jointOrder.length - ((toCheckOrder2.length)+(toCheckOrder1.length)) +" Missing node weren't inserted");
			}
		System.out.println("jointTree checked for order, connections and BF");
		
		
		System.out.println("---------------After Deletions--------------");
		int z = tree2.size;
		System.out.println("Original Size was " + z);
		tree2.delete(jointOrder[z-2]);
		tree2.delete(jointOrder[(z/2)]);
		tree2.delete(jointOrder[z/4]);
		tree2.delete(jointOrder[z-8]);
		tree2.delete(jointOrder[1]);
		z = tree2.size;
		System.out.println("After 5 deletions size is " + z);
		tree2.print(tree2.root, 0); //check new tree for imbalance or disconnections
		int[] jointAfterDeletions = tree2.keysToArray();
		System.out.println(Arrays.toString(jointAfterDeletions)); // new tree in order
		System.out.println("joint Tree after deletions checked for order, connections and BF");*/
		
		
		
		System.out.println("---------------Tree3--------------");
		System.out.println("Tree3 order of insertions");
		//System.out.println(Arrays.toString(orderOfInsertions3)); //insertion order originally
		System.out.println("Tree3 inserted:");
		//System.out.println(Arrays.toString(toCheckOrder3)); //tree two in order
		//if (orderOfInsertions3.length!=toCheckOrder3.length) { // if not the same length - some wasn't inserted  - error
		//	System.out.println((orderOfInsertions3.length - toCheckOrder3.length) +" Missing node wasn't inserted");
	//		}
		if (!tree3.empty()) {
			tree3.print(tree3.root, 0); //checking for imbalance or disconnections
		}
		System.out.println("Tree3 checked for order, connections and BF");
		
		System.out.println("---------------After split random--------------");
		System.out.println("splitting by " + orderOfInsertions3[12]);//+ tree2.root.getRight().getLeft().getLeft().getKey());
		AVLTree[] afterSplit = tree3.split(orderOfInsertions3[12]);//tree2.root.getRight().getKey());
		System.out.println("---------------After split extreme--------------");
		AVLTree tree4 = new AVLTree();
		for (int i=0; i<n; i++) {
			tree4.insert(orderOfInsertions3[i], null);
		}
		System.out.println("splitting by " + tree4.root.getPredecessor().key);//+ tree2.root.getRight().getLeft().getLeft().getKey());
		AVLTree[] afterSplit2 = tree4.split(tree4.root.getPredecessor().key);//tree2.root.getRight().getKey());
		//System.out.println("---------------Big Tree--------------");
		//afterSplit[0].print(afterSplit[0].root, 0); //check big tree for imbalance or disconnections
		//int[] bigTreeAfterSplit = afterSplit[0].keysToArray();
		//System.out.println(Arrays.toString(bigTreeAfterSplit)); // new tree in order
		//System.out.println("---------------Small Tree--------------");
		//afterSplit[1].print(afterSplit[1].root, 0); //check  small tree for imbalance or disconnections
		//int[] smallTreeAfterSplit = afterSplit[1].keysToArray();
		//System.out.println(Arrays.toString(smallTreeAfterSplit)); // new tree in order
		//int smallT = smallTreeAfterSplit.length;
		//int bigT = bigTreeAfterSplit.length;
		//int missed = orderOfInsertions3.length - smallT - bigT - 1;
		//if (missed!=0) {
		//	System.out.println("Missing " + missed + " nodes");
		//}
		System.out.println("split Trees after deletions checked for order, connections and BF");
	}
	
	
	
	
	
	
	
	public static int getRandomInt1(Random randomGen, int[] bank) { //only under 500
		int key = randomGen.nextInt(499);
		while (bank[key]==1) {
			key = randomGen.nextInt(499);	
	}
		bank[key]=1;
		return key;
	}
	public static int getRandomInt2(Random randomGen, int[] bank) { //only above 500
		int key = randomGen.nextInt(1000);
		while ((bank[key]==1)||(key<=500)) {
			key = randomGen.nextInt(1000);	
	}
		bank[key]=1;
		return key;
	}
	public static int getRandom3(Random randomGen, int[] bank3) { 
		int key = randomGen.nextInt(bank3.length);
		while ((bank3[key]==1)) {
			key = randomGen.nextInt(bank3.length);	
	}
		bank3[key]=1;
		return key;
	}	
	}

