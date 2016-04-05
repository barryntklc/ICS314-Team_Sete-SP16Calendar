import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores a key-value pair
 * 
 * @author Chunmeista
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
	public String toString() {
		return pairs.toString();
	}
	
	//get key:value pair
	public String[] getPair(int index) {
		String[] returnBuf = {pairs.get(index).key, pairs.get(index).val};
		
		return returnBuf;
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

		/*
		public String[] getPair() {
			String[] returnBuf = {key, val};
			return returnBuf;
		}
		*/
		
		public String toString() {
			return "(" + key + "," + val + ")";
		}
	}


	  
	
}
