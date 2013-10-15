package com.verify;

public class IntersectVerify {
	private /*@ spec_public @*/ static int m_a = 1;
	private /*@ spec_public @*/ static int m_b = 2;
	private /*@ spec_public @*/ static int m_c = 1;

	private /*@ spec_public @*/ static int m_m = 2;
	private /*@ spec_public @*/ static int m_y = 5;
	private /*@ spec_public @*/ static boolean m_result = false;
	private /*@ spec_public @*/ static int valAtPoint;
	private /*@ spec_public @*/ static int m_absValue;

	//@ assignable \nothing;
	//@ ensures \result == m*x + y;
	public static int findLinearValue(int m, int y, int x) {
		int val = 0;
		val = m*x;
		val = val + y;
		return val;
	}

	//@ requires a != 0;
	//@ assignable \nothing;
	//@ ensures \result == a*x*x + b*x + c;
	public static int findQuadraticValue(int a, int b, int c, int x)
	{
		int val = 0;
		val = a*x*x;
		val = val + b*x;
		val = val + c;
		return val;
	}

	//@ assignable \nothing;
	//@ ensures ((x == y) ==> (\result == 1)) || ((x != y) ==> (\result == 0));
	public static int sameVal(int x, int y) {
		if (x == y) {
			return 1;
		}
		else {
			return 0;
		}
	}

	//@ assignable \nothing;
	//@ ensures (\result >= 0) && (x < 0 ==> \result == -x) && (x >= 0 ==> \result == x);
	public static int abs_value(int x) {
		if (x <= 0) {
			return (-x);
		} else {
			return x;
		}
	}

	//@ requires m_a != 0;
	//@ assignable m_result, valAtPoint, m_absValue;
	//@ ensures ((m_result == true) ==> (m_absValue >= 0)) && ((m_result == false) ==> (m_absValue == -1 && valAtPoint == -1));
	public static void main(String[] args) {
		int iPoint = 2;
		int linearVal = findLinearValue(m_m, m_y, iPoint);
		int quadVal = findQuadraticValue(m_a, m_b, m_c, iPoint);
		int same = sameVal(linearVal, quadVal);
		if (same == 1) {
			m_result = true;
			valAtPoint = linearVal;
			m_absValue = abs_value(linearVal);
		}
		else {
			m_result = false;
			valAtPoint = -1;
			m_absValue = -1;
		}
	}
}