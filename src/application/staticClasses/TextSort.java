package application.staticClasses;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TextSort {



	public static void Sort() throws FileNotFoundException
	{
		List<String> list = new ArrayList<String>();

		File f = new File("MonsterIDs.txt");
		Scanner scanner = new Scanner(f);
		while(scanner.hasNextLine())
		{
			String line=scanner.nextLine();
			if(line!="")
			{
				list.add(line.trim());
			}

		}
		list.sort(String.CASE_INSENSITIVE_ORDER);
		scanner.close();

		PrintWriter p = new PrintWriter(f);

		for(String s: list)
		{
			p.println(s);
		}

		p.print('\n');

		List<String> list2 = new ArrayList<String>();
		for(String s: list)
		{
			StringBuilder sb= new StringBuilder(s);
			StringBuilder sb1= new StringBuilder();
			sb1.append(s.charAt(3));
			sb1.append(s.charAt(4));

			StringBuilder sb2= new StringBuilder();
			sb2.append(s.charAt(0));
			sb2.append(s.charAt(1));
			
			sb.replace(3, 5, sb2.toString());
			sb.replace(0, 2, sb1.toString());
			System.out.println(sb.toString());
			list2.add(sb.toString());
		}
		
		System.out.println(list2.toString());
		list2.sort(String.CASE_INSENSITIVE_ORDER);
		System.out.println(list2.toString());
		
		for(String s: list2)
		{
			p.println(s);
			
		}
		p.close();
	}
/*
	public static void main(String[] args) throws FileNotFoundException {
		Sort();
	}
*/	
}
