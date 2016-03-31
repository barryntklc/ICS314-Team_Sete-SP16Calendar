import java.io.Serializable;

/**
 * Stores a key-value pair
 * 
 * @author Chunmeista
 */
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
			//TODO add case where more than one delimiter is found
		}
	}
	
	public String toString() {
		return "(" + key + "," + val + ")";
	}
}
