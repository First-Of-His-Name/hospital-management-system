package application;

public class MyException extends Exception {
	String s;
	public MyException(String s){
		this.s=s;
	}
    public String toString(){ 
       return (""+s) ;
    }
}
