/*
Copyright (c) 2013, Bernard Butler (Waterford Institute of Technology, Ireland), Project: FAME (08/SRC/I1403)
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 -  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 -  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 -  Neither the name of WATERFORD INSTITUTE OF TECHNOLOGY nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package example.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

	public static String toString(Date aDate) {
    	SimpleDateFormat formatter;
    	String pattern = "yyyy-MM-dd HH:mm:ss z";
    	formatter = new SimpleDateFormat(pattern);
    	return formatter.format(aDate);
	}

	public static String toString(long aLong) {
    	return String.valueOf(aLong);
	}

	public static String toString(int anInt) {
    	return String.valueOf(anInt);
	}

	public static String toString(float aFloat) {
    	return String.valueOf(aFloat);
	}
	
	public static int randomIntBetween(int min, int max) {
//	See http://stackoverflow.com/questions/363681/generating-random-number-in-a-range-with-java
        Random rand = new Random();
        int randInt = rand.nextInt(max - min + 1) + min;
        return randInt;
    }
}
