package com.test.debug;

public class FindString {
	public static void main(String[] args) {
		String[] str = {"Hello","Beautiful","World"};
		int index = locateString(str, 3, "Hello");
		System.out.println(index);
	}
	
	static int locateString(String[] arr, int numElements, String str) {
		for (int i = 0; i < numElements; i++) {
			if (arr[i].equals(str)) {
				return i;
			}
		}
		return -1;
	}
}