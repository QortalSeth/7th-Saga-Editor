package application.staticClasses

import java.io.File
import java.io.FileNotFoundException
import java.io.PrintWriter
import java.util.*


object TextSort
{


	@Throws(FileNotFoundException::class) fun Sort()
	{
		val list = ArrayList<String>()

		val f = File("MonsterIDs.txt")
		val scanner = Scanner(f)
		while (scanner.hasNextLine())
		{
			val line = scanner.nextLine()
			if (line != "")
			{
				list.add(line.trim { it <= ' ' })
			}

		}
		list.sortWith(String.CASE_INSENSITIVE_ORDER)
		scanner.close()

		val p = PrintWriter(f)
		list.forEach { p.println(it) }
		p.print('\n')

		val list2 = ArrayList<String>()
		list.forEach {
			val sb = StringBuilder(it)
			val sb1 = StringBuilder()
			sb1.append(it[3])
			sb1.append(it[4])

			val sb2 = StringBuilder()
			sb2.append(it[0])
			sb2.append(it[1])

			sb.replace(3, 5, sb2.toString())
			sb.replace(0, 2, sb1.toString())
			println(sb.toString())
			list2.add(sb.toString())
		}

		println(list2.toString())
		list2.sortWith(String.CASE_INSENSITIVE_ORDER)
		println(list2.toString())

		list2.forEach { p.println(it) }
		p.close()
	}
	/*
	public static void main(String[] args) throws FileNotFoundException {
		Sort();
	}
*/
}
