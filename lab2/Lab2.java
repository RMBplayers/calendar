package lab2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.util.*;

public class Lab2 {
	public static void main(String[] args) {
		// Task 1
		int sum = 0;
		int tmp = 0;
		System.out.println("the output is: ");
		for (int i = 0; i<1000; i++){
			tmp = i;
			while(tmp!=0){
				sum += (int)Math.pow((tmp%10),3);
				tmp/=10;
			}
			if (sum == i)
				System.out.println(i);
			sum = 0;
		}
		
		// Task 2
		try{
		Map<String, Integer> wordFrq = new HashMap<String, Integer>();
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the file name: ");
		File txtFile = new File(input.nextLine());
		input = new Scanner(txtFile);
		// record the first file
		while(input.hasNext()){
			String word = input.next();
			if (wordFrq.containsKey(word)){
				int num = wordFrq.get(word);
				wordFrq.put(word, num+1);
			}else{
				wordFrq.put(word, 1);
			}
		}
		// record the second file
		System.out.print("Please enter the file name: ");
		txtFile = new File(input.nextLine());
		input = new Scanner(txtFile);
		Map<String, Integer> nextWordFrq = new HashMap<String, Integer>();
		
		while(input.hasNext()){
			String word = input.next();
			if (nextWordFrq.containsKey(word)){
				int num = nextWordFrq.get(word);
				nextWordFrq.put(word, num+1);
			}else{
				nextWordFrq.put(word, 1);
			}
		}
		
		// next compare with the two different Map
		MapIterator it = wordFrq.iterator;
		
		
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
