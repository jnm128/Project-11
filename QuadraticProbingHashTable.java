package lab11;

import java.util.Hashtable;

public class QuadraticProbingHashTable
{
	
    public HashEntry [ ] HashTable;   // The array that holds the hash table
    public int currentSize;       // The number of occupied cells	

	// constructor to create the HashTable of initial size = size
    // and sets all of its elements to null.
    public QuadraticProbingHashTable( int size )
    {
    	currentSize=0;
    	
    	HashTable= new HashEntry[size];
    	for (int i=0;i<size;i++) {
    		HashTable[i]=null;
    	}
    }
 
    // insert into the hash table
    // if the item is already present, do nothing and simply return
    // be careful you might need to rehash - reshape when the load factor is .75
    // Hint: first check the load factor after add - if the size is beyond rehash first!
    public void insert( int x )
    {
    	
    	
    	
    	//load factor= currentSize/Hashtable.length;
    	//;
    
    	double load=(double)(currentSize)/(double)(HashTable.length);
    	
    			if (load>0.75) {
    				rehash();
    			}
    	
    		    //int currentPos=hash(x,HashTable.length);
    			int  currentPos=quad(x);
    			
    		
    			
    			if((HashTable[currentPos]!=null) && (HashTable[currentPos].element==x )&&(HashTable[currentPos].isActive)) 
    				return;
    			//delete {
    			HashTable[currentPos]=new HashEntry(x);
    			currentSize++;
    			
    			 
    			//find the next hash position using quadratic probing
    			

    }
    
    
    
    private int quad(int x) {
    //h(k) +i^2 mod table size
    	
    	
    	int currentPos= hash(x,HashTable.length);
    	int i=0;
    	while(HashTable[currentPos]!=null &&  HashTable[currentPos].isActive&& HashTable[currentPos].element!=x) {
    		currentPos=hash(x+(int)(Math.pow(i, 2)),HashTable.length);
    		i++;
    		
    	}
    
    	return currentPos;
    	}
    
    
    

    // this function will increase the size of the hash table by a factor of two
    // and do the rehash of the current elements inside the hash table
    public void rehash( ) 
    {
    	
    //make new hash table that is twice the size of hashtable
    	//only want to insert values that are active from old table
    	//take all values of hashtable and insert them with insert in new hashtable
      HashEntry [] RehashTable= HashTable;
      HashTable= new HashEntry[HashTable.length*2];
     
      currentSize=0;
      
      for(int i=0;i< RehashTable.length; i++) {
    	 if(RehashTable[i]!=null) {
    		insert(RehashTable[i].element); 
    		
    	 }
    	
    	
      }
      currentSize++;
       
    	
    }
    
    // a simple hash function for int values
    // the hash value should be a number between 0 and tableSize-1
    // use the mod operator as suggested in class
    public int hash(int value, int tableSize )
    {
   
    	if (value<0) {
    		return hash(-1*value,tableSize);
    	}
    	return value % tableSize;
   
    }  
    
    private int getpos(int x) {
    	int currentpos=hash(x,HashTable.length);
    	int i=0;
    	while(HashTable[currentpos].element!=x) {
    		currentpos=hash(x+(int)(Math.pow(i, 2)),HashTable.length);
    		i++;
    	}
    	return currentpos;
    }

    // this function will remove an element from the hash table
    // remember you are not going to remove the element from the hash table (physcially)
    // instead you are supposed to make it inactive
    public void remove( int x )
    {
 
    	int currentPos=getpos(x);
    	
         if(HashTable[currentPos].isActive) {
    		HashTable[currentPos].isActive=false;
    		 
    	}
        
     //currentSize--;

    }

    // this function finds an element in the hash table
    // x is the value we are looking for
    // This function returns the index in which the value resides
    // if not in the hashtable return -1
    public int find( int x )
    {
    	int currentpos=hash(x,HashTable.length);
    	int i=0;
    	while(HashTable[currentpos]!=null&& HashTable[currentpos].element!=x) {
    		currentpos=hash(x+(int)(Math.pow(i, 2)),HashTable.length);
    		i++;
    	}
    	if(HashTable[currentpos]!=null) 
    		return currentpos;
    		else
    			return -1;
    	
    	
    
    }
    
    
    // DO NOT CHNAGE THIS FUNCTION!
    public String toString(){
    	String toReturn = "";
    	for (int i = 0; i < HashTable.length; i++){
    		if (HashTable[i] == null){
    				toReturn += ("eF ");
    		}else{
    			if (HashTable[i].isActive)
    				toReturn += (HashTable[i].element + "T ");
    			else
    				toReturn += (HashTable[i].element + "F ");
    		}
    	}
    	return toReturn;
    }
    
    
    public static void main(String[] args){
    	
    	
    	// ********************* TESTS FOR LAB ****************************//
    
    	QuadraticProbingHashTable h1 = new QuadraticProbingHashTable(10);
    	
    	if (!h1.toString().equals("eF eF eF eF eF eF eF eF eF eF "))
    		System.err.print("TEST FAILED: constructor ( 0 )");
    	   	
    	h1.insert(89);
    	h1.insert(58);
    	h1.insert(6);
    	
    	if (!h1.toString().equals("eF eF eF eF eF eF 6T eF 58T 89T "))
    		System.err.println("TEST FAILED: insert ( 1 )");
    	  		
    	h1.insert(16);
    	
    	if (!h1.toString().equals("eF eF eF eF eF eF 6T 16T 58T 89T "))
    			System.err.println("TEST FAILED: insert ( 2 )");
    	   
    	h1.insert(9);
    	if (!h1.toString().equals("9T eF eF eF eF eF 6T 16T 58T 89T "))
			System.err.println("TEST FAILED: insert ( 3 )");   
    	
    	QuadraticProbingHashTable h2 = new QuadraticProbingHashTable(7);
    	
    	h2.insert(0);
    	h2.insert(1);
    	h2.insert(2);
    	h2.insert(3);
    	h2.insert(4);
    	h2.insert(5);
    	
    	if (!h2.toString().equals("0T 1T 2T 3T 4T 5T eF eF eF eF eF eF eF eF "))
			System.err.println("TEST FAILED: insert ( 4 )"); 
    		
    	System.out.println("Lab Testing Done!!!");
    /*
    	*/
    	// ********************* TESTS FOR ASSIGNMENT ****************************//
    	
    	QuadraticProbingHashTable h3 = new QuadraticProbingHashTable(11);
    	
    	if (!h3.toString().equals("eF eF eF eF eF eF eF eF eF eF eF "))
			System.err.println("TEST FAILED: insert ( 5 )");    	    	
    	
    	h3.insert(44);    	
    	h3.insert(4);
    	h3.remove(44);
    	
    	if (!h3.toString().equals("44F eF eF eF 4T eF eF eF eF eF eF "))
			System.err.println("TEST FAILED: remove ( 6 )");    	    	
    	
    	h3.insert(77);
    	
    	if (!h3.toString().equals("77T eF eF eF 4T eF eF eF eF eF eF "))
			System.err.println("TEST FAILED: insert ( 7 )");    	    	    	
    	
    	h3.insert(16);    	
    	h3.insert(28);
    	h3.insert(21);    	
    	h3.insert(11);    	
    	h3.insert(22);
    	h3.insert(33);  
    	
    	if (!h3.toString().equals("77T 11T eF 33T 4T 16T 28T eF eF 22T 21T "))
			System.err.println("TEST FAILED: insert ( 8 )");    	    	

    	h3.insert(55);
    	
    	if (!h3.toString().equals("22T eF eF eF 4T eF 28T eF eF eF eF 77T 11T eF eF 33T 16T eF eF eF 55T 21T "))
			System.err.println("TEST FAILED: insert ( 9 )");    	    	    	
    
    	if (h3.find(4) != 4)
    		System.err.print("TEST FAILED: find ( 10 )");
    	
    	if (h3.find(44) != -1)
    		System.err.print("TEST FAILED: find ( 11 )");
    	
    	if (h3.find(77) != 11)
    		System.err.print("TEST FAILED: find ( 12 )");    
    		/**/
    	System.out.println("Assignment Testing Done!!!");
    	
    }

}