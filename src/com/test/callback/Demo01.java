package com.test.callback;


/*
 * 回调的练习。
 */

public class Demo01 {

	static Myhandler_sx b = new Myhandler_sx();
	
	public static void main(String[] args) {
		
		Demo01 demo = new Demo01();
		
		demo.method_b(b);
		
	}
	
	public void method_b(Myhandler myhandler){
		
		myhandler.handler("超人");

	}

	
}

class Myhandler_sx implements Myhandler{

	@Override
	public void handler(String str) {
		System.out.println(str);
	}
	
}
