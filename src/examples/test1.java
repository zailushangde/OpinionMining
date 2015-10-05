package examples;
import java.util.Comparator;
	import java.util.TreeSet;
public class test1 {

	

	
	 public static void main(String [] a){
	  TreeSet set=new TreeSet(
	  new Comparator(){
	   int r=0;
	   public int compare(Object a, Object b) {
	    int n1=Integer.parseInt(a.toString());
	    int n2=Integer.parseInt(b.toString());
	    if(n1%2!=n2%2){
	     r=(n2%2-n1%2);
	    }else if(n1%2==1){
	     if(n1>n2) r=1;
	     else if(n1<n2) r=-1;
	    }else if(n1%2==0){
	     if(n1>n2) r=-1;
	     else if(n1<n2) r=1;
	    }
	    return r;
	    
	   }
	  }
	  );
	  set.add("1");
	  set.add("2");
	  set.add("3");
	  set.add("4");
	  set.add("5");
	  set.add("6");
	  set.add("7");
	  set.add("8");
	  set.add("9");
	  set.add("10");
	  System.out.println(set);
	  
	 }
	
}
