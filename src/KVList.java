import java.util.ArrayList;

/**
 * KVList
 * 
 * Stores key-value pairs
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class KVList {

	private ArrayList<KVPair> pairs = new ArrayList<KVPair>();
	
	public KVList() {

	}
	
	public void add(String key, String val) {
		pairs.add(new KVPair(key, val));
	}
	public void add(String str[]) {
		pairs.add(new KVPair(str));
	}
	
	public void remove(String gKey){
		int index = getIndex(gKey);
//		System.out.println("INDEX!: "+index);
		if(index!=-1&&index<pairs.size()){
		pairs.remove(index);
		}
	}
	public String toString() {
		String buffer = "";
		
		for (int i = 0; i < pairs.size(); i++) {
			buffer = buffer + pairs.get(i).toString() + "\n";
		}
		return buffer;
	}
	
	
	//get key:value pair
	public String[] getPair(int index) {
		String[] returnBuf = {pairs.get(index).key, pairs.get(index).val};
		
		return returnBuf;
	}
	
	public String getKey(int index) {
		return pairs.get(index).key;
	}
	
	//get by key, first result
	public String getVal(String key) {
		if (pairs.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < pairs.size(); i++) {
				if (pairs.get(i).key.equals(key)) {
					return pairs.get(i).val;
				}
			}
			return null;
		}
	}
	
	public String getVal(int index) {
		return pairs.get(index).val;
	}
	
	public int getIndex(String gKey){
		int ind = -1;
		for(int i = 0; !pairs.get(i).key.equals(gKey)&&i<pairs.size()-1;i++){
			ind=i+1;
		}
		
		
		return ind;
	}
	
	//set by key, first result
	public boolean setVal(String key, String val) {
		if (pairs.size() == 0) {
			return false;
		} else {
			boolean contains = false;
			
			for (int i = 0; i < pairs.size(); i++) {
				if (pairs.get(i).key.equals(key)) {
					contains = true;
					pairs.get(i).val = val;
				}
			}
			if (contains == false) {
				add(key, val);
			}
			return true;
		}
	}
	
	public int size() {
		return pairs.size();
	}
	
	public boolean contains() {
		return false;
	}

	public class KVPair {
		public String key = "";
		public String val = "";

		public KVPair() {
		}

		public KVPair(String key, String val) {
			this.key = key;
			this.val = val;
		}

		public KVPair(String str[]) {
			if (str.length == 2) {
				this.key = str[0];
				this.val = str[1];
			} else if (str.length == 1) {
				this.key = str[0];
			} else {
				this.key = str[0];
				
				String strBuffer = "";
				
				for (int i = 1; i < str.length; i++) {
					strBuffer = strBuffer + str[i];
					if (i < str.length - 1) {
						strBuffer = strBuffer + ':';
					}
				}
				this.val = strBuffer;
			}
		}
		
		public String printPair() {
			return key + ": " + val;
		}
		
		public String toString() {
			return key + ":" + val;
		}
	}


	  
	
}
