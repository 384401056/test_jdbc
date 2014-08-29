package com.test.callback;


public class Demo02 {

	
	public static void main(String[] args) {
		
		Demo02 demo = new Demo02();
		
		demo.method_1(10, 20,new MyHanderInt() {
			@Override
			public int doThis(int a, int b) {
				return a+b;
			}
		});
		
	}
	
	
	
	public void method_1(int a,int b,MyHanderInt handler){
		System.out.println(handler.doThis(a, b));
	}
	
	
}
