package application;

import application.excel.Excel;
import application.models.lists.Lists;
import application.models.lists.states.DataState;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by Seth on 4/3/2017.
 */
public class ROM {


	private static byte[] bytes;
	private static int header;
	public static boolean showEmptyValues = false;
	public static boolean showMonsterDuplicates = false;
	private static File programDirectory;
	private static File rom;
	private static File romDirectory;


	private static File changeListsDirectory;
	private static int offset;


	public static void openDefaultROM () {
		try {
			rom = new File(romDirectory + "/7th Saga Unedited.smc");
			bytes = Files.readAllBytes(rom.toPath());
			header = 0;

			Lists.CreateLists();
			Lists.InitializeLists();
			Data.serializeDefaultDataToDisk();
		} catch (Exception e) {
			System.out.println("Open default ROM failed");
		}


	}

	private static String removeFileExtension (File file) {
	String fileName = file.getName();
	int pos = fileName.lastIndexOf(".");
if(pos >0)

	{
		fileName = fileName.substring(0, pos);
	}
	return fileName;
}

    public static void openROM(File file)
    {
        rom = file;
		changeListsDirectory = new File(romDirectory + "/ChangeLists/" + removeFileExtension(rom));
        try
        {
            bytes = Files.readAllBytes(rom.toPath());

            int headerTest = ROM.bytes.length % 1024;
            switch (headerTest) {
                case 0:
                    ROM.header = 0;
                    break;
                case 512:
                    ROM.header = 512;
                    break;
                default:
                    ROM.header = 0;
                    System.out.println("improperly configured ROM");
                    break;
            }
            Data.serializeDefaultDataFromDisk();
            Lists.setState(new DataState());
            Lists.InitializeLists();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveROM()
    {
        try
        {
            Lists.saveModels();
            Excel.makeChangeLists();
            Files.write(rom.toPath(), ROM.bytes, StandardOpenOption.WRITE);
            System.out.println("Data saved to Disk" + "\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static File getProgramDirectory(){return programDirectory;}
    public static void setROMDirectory(File f){romDirectory=f;}
    public static File getROMDirectory(){return romDirectory;}
    public static void setProgramDirectory()
    {
        try
        {
            //programDirectory =new File(System.getProperty("java.class.path")).getParentFile();
            programDirectory = programDirectory = new File(".");
    }
        catch (Exception e)
        {
            programDirectory = new File(".");
        }
    }


    public static int getByte(int offset)
    {
        int unsignedByte= bytes[offset+header] & 0xFF;
        return unsignedByte;
    }

    // this returned value is little endian
    public static int getShort(int offset)
    {
        int unsignedByte1 = getByte(offset);
        int unsignedByte2 = getByte(offset+1);

        unsignedByte2<<=8;
        return unsignedByte1 | unsignedByte2;

    }

    // this returned value is little endian
    public static int getTriple(int offset)
    {
        int shortValue = getShort(offset);
        int finalByte = getByte(offset+2);
        finalByte<<=16;
        return finalByte|shortValue;
    }

    public static void setByte(int offset, int value)
    {
        bytes[offset+header]= (byte) value;
    }

    public static void setShort(int offset, int value)
    {
        setByte(offset, value&0xFF);
        value>>=8;
        setByte(offset+1, value&0xFF);
    }

    public static void setTriple(int offset, int value)
    {
        setShort(offset, value);
        value>>=16;
        setByte(offset+2, value&0xFF);
    }

    public static int getNextByte(){return getByte(offset++);}
    public static int getNextShort()
    {
        int value = getShort(offset);
        offset+=2;
        return value;
    }
    public static int getNextTriple(){
        int value = getTriple(offset);
        offset+=3;
        return value;

    }

    public static void setNextByte(int value){setByte(offset,value); offset++;}
    public static void setNextShort(int value){setShort(offset,value); offset+=2;}
    public static void setNextTriple(int value){setTriple(offset,value); offset+=3;}

    public static void setOffset (int pointer){offset=pointer;}
    public static int getOffset(){return offset;}

	public static File getChangeListsDirectory () {
		return changeListsDirectory;
	}




































}
