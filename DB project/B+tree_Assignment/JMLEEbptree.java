//-c index.dat 4				Data File Creation 
//-i index.dat input.csv		Insertion
//-s index.dat 87				Single Key Search
//-r index.dat 50 100			Ranged Search 
//-d index.dat delete.csv		Deletion

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

class Node{
	public int nodeFlag;				//-1:empty 0:non_leaf 1:leaf
	public int num;						//leaf:num of key , non_leaf:num of child
	public int rightNode;				//leaf:rightSibling node , non_leaf:rightmost childnode
	public int selfPoniter;
	public int parentPointer;
	public int data[][];				//leaf:key and value , non_leaf:key and leftchild
	
	Node(int size){
		nodeFlag = -1;
		num = 0;
		rightNode = -1;
		selfPoniter = -1;
		parentPointer = -1;
		
		data = new int[size][2];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < 2; j++) {
				data[i][j] = -1;
			}
	}
}


public class JMLEEbptree {
	private static int size;
	private static int headOfLeaf;
	private static int rootPointer;
	private static  ArrayList<Node> index = new ArrayList<Node>();
	
	
	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			System.out.println("Parameter mismnatch.");
			System.exit(1);	
		}
		
		//creation
		if(args[0].equals("-c")) {
			if(args.length < 3) {
				System.out.println("Parameter mismnatch.");
				System.exit(1);	
			}
			if(!isNumber(args[2])) {
				System.out.println(args[2] + " is not size. Please input integer.");
				System.exit(1);	
			}
			
			size = Integer.parseInt(args[2]);
			headOfLeaf = -1;
			rootPointer = -1;
			
			//갱신
			WriteIndex(Creation(args[1]));
			
		}
		
		//insertion
		else if(args[0].equals("-i")) {
			if(args.length < 3) {
				System.out.println("Parameter mismnatch.");
				System.exit(1);	
			}
			File indexData = new File(args[1]);
			File inputData = new File(args[2]);
			BufferedReader br = new BufferedReader(new FileReader(inputData));
			String strLine;
			String[] data;
			
			//파일 존재 확인
			if(!indexData.exists()) {
				System.out.println(args[1] + " is not exist");
				System.exit(1);		//終了
			}
			if(!inputData.exists()) {
				System.out.println(args[2] +" is not exist");
				System.exit(1);		//終了
			}
			
			ReadIndexFile(indexData);
			
			//inputFile 읽기
			while((strLine = br.readLine()) != null){
				data = strLine.split(",");
				
				if(!isNumber(data[0])) {
					System.out.println("the input file does not have integer datas.");
					System.exit(0);
				}
				if(!isNumber(data[1])) {
					System.out.println("the input file does not have all integer datas.");
					System.exit(0);
				}
				System.out.println("key = "+Integer.parseInt(data[0]));
				System.out.println("value = "+Integer.parseInt(data[1]));
				
				
				//중복 확인
				if(headOfLeaf != -1 && existKey(Integer.parseInt(data[0])))
					System.out.println("the key already exists");
				else {
					Insertion(Integer.parseInt(data[0]),Integer.parseInt(data[1]));
					WriteIndex(indexData);
					System.out.println("Success!");
				}
			}
			br.close();
		}
		
		
		
		
		
		
		//deletion
		else if(args[0].equals("-d")) {
			if(args.length < 3) {
				System.out.println("Parameter mismnatch.");
				System.exit(1);	
			}
			
			File indexData = new File(args[1]);
			File deleteData = new File(args[2]);
			BufferedReader br = new BufferedReader(new FileReader(deleteData));
			String data;
			
			//파일 존재 확인
			if(!indexData.exists()) {
				System.out.println(args[1] + " is not exist");
				System.exit(1);		//終了
			}
			if(!deleteData.exists()) {
				System.out.println(args[2] +" is not exist");
				System.exit(1);		//終了
			}
			
			ReadIndexFile(indexData);
			
			//deleteFile 읽기
			while((data = br.readLine()) != null){
				if(!isNumber(data)) {
					System.out.println("the delete file does not have all integer datas.");
					System.exit(0);
				}
				
				System.out.println("key = "+Integer.parseInt(data));
				
				//중복 확인
				if(!existKey(Integer.parseInt(data)))
					System.out.println("the key does not exist in the file");
				else {
					Deletion(Integer.parseInt(data));
					WriteIndex(indexData);
					System.out.println("Success!");
				}
			
			}
			br.close();
		}
		
		//search
		else if(args[0].equals("-s")) {
			if(args.length < 3) {
				System.out.println("Parameter mismnatch.");
				System.exit(1);	
			}
			File indexData = new File(args[1]);
			
			//파일 존재 확인
			if(!indexData.exists()) {
				System.out.println(args[1] + " is not exist");
				System.exit(1);		//終了
			}
			if(!isNumber(args[2])) {
				System.out.println(args[2] + " is not key. Please input integer.");
				System.exit(1);	
			}
			
			ReadIndexFile(indexData);
			
			Search(Integer.parseInt(args[2]));
			
		}
		
		//range search
		else if(args[0].equals("-r")) {
			if(args.length < 4) {
				System.out.println("Parameter mismnatch.");
				System.exit(1);	
			}
			File indexData = new File(args[1]);
			if(!indexData.exists()) {
				System.out.println(args[1] + " is not exist");
				System.exit(1);		//終了
			}
			if(!isNumber(args[2])) {
				System.out.println(args[1] + " is not key. Please input integer.");
				System.exit(1);	
			}
			if(!isNumber(args[3])) {
				System.out.println(args[2] + " is not key. Please input integer.");
				System.exit(1);	
			}
			
			ReadIndexFile(indexData);
			
			RangedSearch(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			
		}

	}
	
	private static void ReadIndexFile(File file) throws Exception {
		int pos = 0;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String strLine;
		String[] data;
		
		//meta_data 읽기
		strLine = br.readLine();
		data = strLine.split(",");
		size = Integer.parseInt(data[0]);
		//maxLevel = Integer.parseInt(data[1]);
		headOfLeaf = Integer.parseInt(data[1]);
		rootPointer = Integer.parseInt(data[2]);
		
		//node 읽기
		while((strLine = br.readLine()) != null){
			pos = 0;
			data = strLine.split(",");
			Node temp = new Node(size);
			//node에 삽입
			temp.nodeFlag = Integer.parseInt(data[pos++]);
			temp.num = Integer.parseInt(data[pos++]);
			temp.rightNode = Integer.parseInt(data[pos++]);
			temp.selfPoniter = Integer.parseInt(data[pos++]);
			temp.parentPointer = Integer.parseInt(data[pos++]);
			for(int i = 0; i < size; i++)
				for(int j = 0; j < 2; j++) 
					temp.data[i][j] = Integer.parseInt(data[pos++]);
			
			//remainder 생성
			while(temp.selfPoniter != index.size()) {
				Node remainder = null;
				index.add(remainder);
			}
			
			index.add(temp);
			if(!index.get(temp.selfPoniter).equals(temp)) {		
				System.out.println("index selfPointer mismatch");
				System.exit(0);
			}
		}
		br.close();
	}
	
	private static void WriteIndex(File file) throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		//meta data
		pw.println(size + "," + headOfLeaf + "," + rootPointer);	
		
		//index data
		for(int i = 0; i < index.size(); i++) {
			if( index.get(i) == null || index.get(i).nodeFlag == -1)
				continue;
			
			else if(i != index.get(i).selfPoniter) {
				System.out.println("index selfPointer mismatch");
				System.exit(0);
			}
			
			pw.print(index.get(i).nodeFlag + "," + index.get(i).num + "," +  index.get(i).rightNode + "," + index.get(i).selfPoniter + "," + index.get(i).parentPointer);
			for(int j = 0; j < size; j++)
				for(int z = 0; z < 2; z++) {
					pw.print("," + index.get(i).data[j][z]);
				}
			pw.println();
		}
		
		pw.close();
	}
	
	private static File Creation(String name) throws IOException {
		File newfile = new File(name);
		if(newfile.exists()) newfile.delete();	//존재하면 삭제
		
		if(newfile.createNewFile()) {
			System.out.println("Success!");
		}
		else
			System.out.println("Failed : error ");
		
		return newfile;
	}
	
	private static void Insertion(int key, int value) {
		Node temp;
		int pos;
		
		if(index.size() == 0) {
			Node root = new Node(size);
			index.add(root);
			
			root.nodeFlag = 1;
			root.selfPoniter = 0;
			root.data[0][0] = key;
			root.data[0][1] = value;
			root.num++;
			
			rootPointer = root.selfPoniter;
			headOfLeaf = root.selfPoniter;
		}
		//leaf까지 이동
		else {
			temp = index.get(rootPointer);
			while(temp.nodeFlag == 0) {
				for(pos = 0; pos < temp.num - 1; pos++) {
					if(key < temp.data[pos][0]) {
						temp = index.get(temp.data[pos][1]);
						break;
					}
					else if(pos == temp.num - 2) {
						temp = index.get(temp.rightNode);
						break;
					}
						
				}	
			}
			insertData(temp,key,value);
		}
	}
	
	private static void Deletion(int key) {
		Node temp;
		int pos;
		
		temp = index.get(rootPointer);
		
		//leaf까지 이동
		while(temp.nodeFlag == 0) {
			for(pos = 0; pos < temp.num - 1; pos++) {
				if(key < temp.data[pos][0]) {
					temp = index.get(temp.data[pos][1]);
					break;
				}
				else if(pos == temp.num - 2) {
					temp = index.get(temp.rightNode);
					break;
				}
					
			}	
		}
		deleteData(temp,key);
	}
		
		
	
	
	private static void Search(int key) {
		int i;
		Node temp = index.get(rootPointer);
		
		while(temp.nodeFlag == 0) {
			for(i = 0; i < temp.num - 1; i++) {
				if(key < temp.data[i][0]) {
					System.out.print(temp.data[i][0]+",");
					temp = index.get(temp.data[i][1]);
					break;
				}
				else if(i == temp.num - 2) {
					System.out.print(temp.data[i][0]+",");
					temp = index.get(temp.rightNode);
					break;
				}
					
			}	
		}
		System.out.println();
		for(i = 0; i < temp.num; i++) {
			if(temp.data[i][0] == key) {
				System.out.println(temp.data[i][1]);
				return;
			}	
		}
		System.out.println("NOT FOUND");
	}
	
	private static void RangedSearch(int start_key, int end_key) {
		Node temp = index.get(headOfLeaf);
		
		while(true){
			for(int i = 0; i < temp.num; i++) {
				if(start_key <= temp.data[i][0] && temp.data[i][0] <= end_key)
					System.out.println(temp.data[i][0] + "," + temp.data[i][1]);
			}
			if(temp.rightNode == -1)
				break;
			else {
				temp = index.get(temp.rightNode);
				continue;
			}
		}
	}
	
	private static void insertData(Node node, int key, int value_or_leftchild) {
		int pos;
		Node split;
		
		if(node.nodeFlag == 1) {
			for(pos = 0; pos < node.num; pos++) {
				if(key < node.data[pos][0]) 
					break;
			}
			for(int i = node.num; i > pos; i--) {
				node.data[i][0] = node.data[i - 1][0];
				node.data[i][1] = node.data[i - 1][1];
			}	
			node.data[pos][0] = key;
			node.data[pos][1] = value_or_leftchild;
			node.num++;
			
			if(node.num == size) {
				split = SplitChild(node);
				if(split.selfPoniter != rootPointer) {
					insertData(index.get(node.parentPointer), split.data[0][0], split.data[0][1]);
				}
			}
		}
		else if(node.nodeFlag == 0) {
			for(pos = 0; pos < node.num-1; pos++) {
				if(key < node.data[pos][0]) 
					break;
			}
			for(int i = node.num-1; i > pos; i--) {
				node.data[i][0] = node.data[i - 1][0];
				node.data[i][1] = node.data[i - 1][1];
			}	
			
			node.data[pos][0] = key;
			node.data[pos][1] = value_or_leftchild;
			node.num++;
			
			if(value_or_leftchild == node.rightNode)
				node.rightNode = (index.get(node.data[pos][1])).rightNode;
			else
				node.data[pos+1][1] = (index.get(node.data[pos][1])).rightNode;
			
			if(node.data[size-1][0] != -1) {
				split = SplitChild(node);
				
				if(split.selfPoniter != rootPointer) {
					index.get(node.parentPointer).rightNode = split.rightNode;
					insertData(index.get(node.parentPointer), split.data[0][0], split.data[0][1]);
				}
			}
		}
	}
	
	private static void deleteData(Node node, int key) {
		int pos;
		
		if(node.nodeFlag == 1) {
			for(pos = 0; pos < node.num; pos++) {
				if(key == node.data[pos][0]) 
					break;
			}
			for(int i = pos; i < node.num; i++) {
				node.data[i][0] = node.data[i + 1][0];
				node.data[i][1] = node.data[i + 1][1];
			}	
			node.num--;
			
			//대상의 leafnode의 최소값을 위에 올린다
			if(node.num != 0) {
				for(int i = 0; i < index.get(node.parentPointer).num - 1; i++) {
					if(index.get(node.parentPointer).data[i][0] == key)
						index.get(node.parentPointer).data[i][0] = node.data[0][0];
				}
			}
			
			//node의 key 부족
			if(node.num < (size-1)/2 && node.selfPoniter != rootPointer) {
				
				//오른쪽에서 받는다
				if(node.rightNode != -1 && index.get(node.rightNode).num - 1 >= (size-1)/2) {

					borrowRightKey(node, index.get(node.rightNode));
					
				}
				//왼쪽에서 받는다
				else if(getLCP(index.get(node.parentPointer), key) != -1 && index.get(getLCP(index.get(node.parentPointer), key)).num-1 >= (size-1)/2) {

					borrowLeftKey(node, index.get(getLCP(index.get(node.parentPointer), key)));
					
				}
				//오른쪽과 merge
				else if(node.rightNode != -1) {

					node.nodeFlag = -1;
					
					LeafInorder(node.selfPoniter);
					deleteData(index.get(node.parentPointer), index.get(node.rightNode).data[0][0]);
					
					//System.out.println("index.get(node.parentPointer).nodeFlag = "+index.get(node.parentPointer).nodeFlag);
					if(index.get(node.parentPointer).nodeFlag == -1) {
						index.get(headOfLeaf).parentPointer = -1;
						rootPointer = index.get(headOfLeaf).selfPoniter;
					}
				}
				//왼쪽과 merge
				else {
					System.out.println("4");
					node.nodeFlag = -1;
					
					LeafInorder(node.selfPoniter);
					deleteData(index.get(node.parentPointer), key);
					if(index.get(node.parentPointer).nodeFlag == -1) {
						index.get(headOfLeaf).parentPointer = -1;
						rootPointer = index.get(headOfLeaf).selfPoniter;
					}
				}
				
			}
			
			//root가 비면
			else if(node.selfPoniter == rootPointer) {
				node.nodeFlag = -1;
				rootPointer = -1;
				headOfLeaf = -1;
			}
			
			
		}
		
		else {
			for(pos = 0; pos < node.num-1; pos++) {
				if(key == node.data[pos][0]) 
					break;
			}
			for(int i = pos; i < node.num-1; i++) {
				node.data[i][0] = node.data[i + 1][0];
				node.data[i][1] = node.data[i + 1][1];
			}
			
			
			node.num--;
			
			//root가 비면
		    if(node.data[0][0] == -1 && node.selfPoniter == rootPointer) 
				node.nodeFlag = -1;
			
			
			
			//node의 key 부족
		    else if(node.num-1 < (size-1)/2 && node.selfPoniter != rootPointer) {
				
				//오른쪽에서 받는다
				if(getRSP(node) != -1 && index.get(getRSP(node)).num -2 >= (size-1)/2) {
					borrowRightKey(node, index.get(getRSP(node)));
					
				}
				//왼쪽에서 받는다
				else if(getLSP(node) != -1 && index.get(getLSP(node)).num -2 >= (size-1)/2) {
					borrowLeftKey(node, index.get(getLSP(node)));
					
				}
				//오른쪽과 merge
				else if(getRSP(node) != -1) {
					MergeRight(node, index.get(getRSP(node)));
					if(node.parentPointer == rootPointer && index.get(rootPointer).data[0][0] == -1) {
						index.get(rootPointer).nodeFlag = -1;
						rootPointer = node.selfPoniter;
					}
				}
				//왼쪽과 merge
				else {
					MergeLeft(node, index.get(getLSP(node)));
					if(node.parentPointer == rootPointer && index.get(rootPointer).data[0][0] == -1) {
						index.get(rootPointer).nodeFlag = -1;
						rootPointer = node.selfPoniter;
					}
				}
			}
			
			
		}
	}
	
	private static Node SplitChild(Node root) {
		
		int i, j;
		int mid = (size - 1)/2;
		Node newParent;
		Node newSibling;
		
		newParent = new Node(size);
		newParent.nodeFlag = 0;
	
		newSibling = new Node(size);
		index.add(newSibling);
		newSibling.selfPoniter = index.size() - 1;
		newSibling.nodeFlag = root.nodeFlag;
		newSibling.parentPointer = root.parentPointer;
		
		if(root.nodeFlag == 1) {
			for(i = mid+1; i < size; i++) {
				for(j = 0; j < 2; j++) {
					newSibling.data[i - (mid+1)][j] = root.data[i][j];
					root.data[i][j] = -1;
				}
				newSibling.num++;
				root.num--;
			}
			
			newSibling.rightNode = root.rightNode;
			root.rightNode = newSibling.selfPoniter;
		
			newParent.data[0][0] = newSibling.data[0][0];
			newParent.data[0][1] = root.selfPoniter;
			newParent.rightNode = newSibling.selfPoniter;
			
			if (root.selfPoniter == rootPointer) {
				newParent.num += 2;
				index.add(newParent);
				newParent.selfPoniter = index.size() - 1;
				rootPointer = newParent.selfPoniter;
				
				root.parentPointer = newParent.selfPoniter;
				newSibling.parentPointer = newParent.selfPoniter;
			}
			return newParent;
		}
		
		else {
			for(i = mid+1; i < size; i++) { 
				for(j = 0; j < 2; j++) {
					newSibling.data[i - (mid+1)][j] = root.data[i][j];
					root.data[i][j] = -1;
				}
				
				index.get(newSibling.data[i - (mid+1)][1]).parentPointer = newSibling.selfPoniter;
				
				newSibling.num++;
				root.num--;
			}
			
			newSibling.rightNode = root.rightNode;
			index.get(newSibling.rightNode).parentPointer = newSibling.selfPoniter;
			newSibling.num++;
			root.rightNode = root.data[mid][1];
			
			newParent.data[0][0] = root.data[mid][0];
			newParent.data[0][1] = root.selfPoniter;
			newParent.rightNode = newSibling.selfPoniter;
			
			root.data[mid][0] = -1;
			root.data[mid][1] = -1;
			root.num--;
			
			if (root.selfPoniter == rootPointer) {
				newParent.num += 2;
				index.add(newParent);
				newParent.selfPoniter = index.size() - 1;
				rootPointer = newParent.selfPoniter;
				
				root.parentPointer = newParent.selfPoniter;
				newSibling.parentPointer = newParent.selfPoniter;
			}
			
			return newParent;
		}
	}
	
	private static void borrowRightKey(Node p_node, Node p_sibling) {
		
		if(p_node.nodeFlag == 1) {
			p_node.data[0][0] = p_sibling.data[0][0];
			p_node.data[0][1] = p_sibling.data[0][1];
			p_node.num++;
			
			for(int i = 0; i < p_sibling.num; i++) {
				p_sibling.data[i][0] = p_sibling.data[i+1][0];
				p_sibling.data[i][1] = p_sibling.data[i+1][1];
			}
			p_sibling.num--;
			
			//대상의 leafnode의 최소값을 위에 올린다
			for(int i = 0; i < index.get(p_node.parentPointer).num - 1; i++) {
				if(index.get(p_node.parentPointer).data[i][0] == p_node.data[0][0])
					index.get(p_node.parentPointer).data[i][0] = p_sibling.data[0][0];
			}
		}
		
		else {
			Node temp = index.get(p_node.parentPointer);
			
			for(int i = 0; i < temp.num-1; i++) {
				if(p_node.selfPoniter == temp.data[i][1]) {
					insertData(p_node, temp.data[i][0], p_node.rightNode);
					p_node.rightNode = p_sibling.data[0][1];
					index.get(p_sibling.data[0][1]).parentPointer = p_node.selfPoniter;
	
					temp.data[i][0] = p_sibling.data[0][0];
					
					for(int j = 0; j < p_sibling.num-1; j++) {
						p_sibling.data[j][0] = p_sibling.data[j+1][0];
						p_sibling.data[j][1] = p_sibling.data[j+1][1];
					}
					p_sibling.num--;
					
					break;
				}	
			}
		}
		
	}
	
	private static void borrowLeftKey(Node p_node, Node p_sibling) {
		if(p_node.nodeFlag == 1) {
			p_node.data[0][0] = p_sibling.data[p_sibling.num-1][0];
			p_node.data[0][1] = p_sibling.data[p_sibling.num-1][1];
			p_node.num++;
			
			p_sibling.data[p_sibling.num-1][0] = -1;
			p_sibling.data[p_sibling.num-1][1] = -1;
			p_sibling.num--;
			
			//대상의 leafnode의 최소값을 위에 올린다
			for(int i = 0; i < index.get(p_node.parentPointer).num - 1; i++) {
				if(index.get(p_node.parentPointer).data[i][1] == p_sibling.selfPoniter)
					index.get(p_node.parentPointer).data[i][0] = p_node.data[0][0];
			}
		}
		
		else {
			Node temp = index.get(p_node.parentPointer);
			
			for(int i = 0; i < temp.num-1; i++) {
				if(p_sibling.selfPoniter == temp.data[i][1]) {
					insertData(p_node, temp.data[i][0], p_sibling.rightNode);
					index.get(p_sibling.rightNode).parentPointer = p_node.selfPoniter;
					
					temp.data[i][0] = p_sibling.data[p_sibling.num-2][0];
					p_sibling.rightNode =  p_sibling.data[p_sibling.num-2][1];
					
					p_sibling.data[p_sibling.num-2][0] = -1;
					p_sibling.data[p_sibling.num-2][1] = -1;
					p_sibling.num--;
					
					break;
				}	
			}
		}
	}
	
	private static void MergeRight(Node p_node, Node p_sibling) {
		Node temp = index.get(p_node.parentPointer);
		
		for(int i = 0; i < temp.num-1; i++) {
			if(p_node.selfPoniter == temp.data[i][1]) {
				insertData(p_node, index.get(p_sibling.data[0][1]).data[0][0], p_node.rightNode);
				deleteData(temp, temp.data[i][0]);
				
				for(int j = 0; j < p_sibling.num-1; j++) {
					insertData(p_node, p_sibling.data[j][0], p_sibling.data[j][1]);
					index.get(p_sibling.data[j][1]).parentPointer = p_node.selfPoniter;
				}
				index.get(p_sibling.rightNode).parentPointer = p_node.selfPoniter;
				p_node.rightNode = p_sibling.rightNode;
				p_sibling.nodeFlag = -1;
				
				if(temp.nodeFlag == -1) {
					p_node.parentPointer = -1;
					rootPointer = p_node.selfPoniter;
				}
				break;
			}
		}
	}
	
	private static void MergeLeft(Node p_node, Node p_sibling) {
		Node temp = index.get(p_node.parentPointer);
		
		for(int i = 0; i < temp.num-1; i++) {
			if(p_sibling.selfPoniter == temp.data[i][1]) {
				insertData(p_node, index.get(p_node.rightNode).data[0][0], p_sibling.rightNode);
				deleteData(temp, temp.data[i][0]);
				
				for(int j = 0; j < p_sibling.num-1; j++) {
					insertData(p_node, p_sibling.data[j][0], p_sibling.data[j][1]);
					index.get(p_sibling.data[j][1]).parentPointer = p_node.selfPoniter;
				}
				
				index.get(p_sibling.rightNode).parentPointer = p_node.selfPoniter;
				p_sibling.nodeFlag = -1;
				
				if(temp.nodeFlag == -1) {
					p_node.parentPointer = -1;
					rootPointer = p_node.selfPoniter;
				}
				
				break;
			}
		}
	}
		
	private static boolean isNumber(String num) {
	    try {
	        Integer.parseInt(num);
	        return true;
	        } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	private static boolean existKey(int key) {
		Node temp = index.get(headOfLeaf);
		
		while(true){
			for(int i = 0; i < temp.num; i++) {
				if(key == temp.data[i][0])
					return true;
			}
			if(temp.rightNode == -1)
				break;
			else {
				temp = index.get(temp.rightNode);
				continue;
			}
		}
		return false;
	}
	
	private static int getLCP(Node node, int key) {		//getLeftChildPointer
		for(int i = 0; i < node.num - 1; i++) {
			if(node.data[i][0] == key)
				return node.data[i][1];
		}
		
		return -1;
	}
	
	private static int getRSP(Node p_node) {			//getRightSiblingPointer
		Node temp = index.get(p_node.parentPointer);
		
		for(int i = 0; i < temp.num-1; i++) {
			if(p_node.selfPoniter == temp.data[i][1]) {
				if(temp.data[i+1][1] != -1)
					return temp.data[i+1][1];
				else
					return temp.rightNode;
			}
				
		}
		return -1; //rightmost node
	}
	
	private static int getLSP(Node p_node) {			//getRightSiblingPointer
		Node temp = index.get(p_node.parentPointer);
		
		if(p_node.selfPoniter == temp.data[0][1])
			return -1;	//leftmost node
		
		for(int i = 1; i < temp.num-1; i++) {
			if(p_node.selfPoniter == temp.data[i][1])
				return temp.data[i-1][1];
		}
		return temp.data[temp.num-2][1]; //rightmost node의 왼쪽
	}
	
	//
	private static void LeafInorder(int p_pointer) {
		Node temp = index.get(headOfLeaf);
		if(p_pointer == headOfLeaf) {
			headOfLeaf = index.get(temp.rightNode).selfPoniter;
			return;
		}
		
		while(temp.rightNode != -1) {
			if(temp.rightNode == p_pointer) {
				temp.rightNode = index.get(p_pointer).rightNode;
				if(index.get(temp.parentPointer).rightNode == p_pointer)
					index.get(temp.parentPointer).rightNode = temp.selfPoniter;
				
				return;
			}
			temp = index.get(temp.rightNode);
		}
	}
}

