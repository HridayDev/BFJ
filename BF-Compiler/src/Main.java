import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	boolean inLoop = false;
	int loop;
	File BfFile;
	private char[] BfCode;
	private int[] bf = new int[30000];
	private int codeLen;
	private int pointer = 0;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		getFile(false);
		lex();
		doStuff();
	}

	private void getFile(boolean readFromConsole) {
		String fileName;
		if (readFromConsole) {
			Scanner input = new Scanner(System.in);
			System.out.print("File: ");
			fileName = input.nextLine();
			input.close();
		} else {
			fileName = "BF-Code.bf";
		}

		BfFile = new File(fileName);
		if (!BfFile.exists()) {
			System.err.println(
					"Error Reading File, \n\tFile \"" + fileName + "\" Does Not Exist, \n\tEnter A Valid File Name.");
		} else {
			System.out.println("\nFile Selected: " + BfFile.getName() + "\n\n\n\nBf Code Console: ");
		}
	}

	private void lex() {
		try {

			BufferedReader in = new BufferedReader(new FileReader(BfFile));
			String code = "";

			String line;
			while ((line = in.readLine()) != null) {
				code += line;
			}

			code = code.replaceAll("\\s", "");

			codeLen = code.length();
			BfCode = new char[codeLen];
			for (int i = 0; i < code.length(); i++) {
				BfCode[i] = code.charAt(i);
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doStuff() {
		for (int i = 0; i < codeLen; i++) {
			if (pointer < 0) {
				System.err.println("Pointer error");
				System.exit(0);

			} else if (BfCode[i] == '<') {
				pointer--;
			} else if (BfCode[i] == '>') {
				pointer++;
			} else if (BfCode[i] == '+') {
				bf[pointer]++;
			} else if (BfCode[i] == '-') {
				bf[pointer]--;
			} else if (BfCode[i] == '.') {
				System.out.print((char) bf[pointer]);
			} else if (BfCode[i] == ',') {
				Scanner input = new Scanner(System.in);
				int in = (int) input.next().charAt(0);
				input.close();
				bf[pointer] = in;

			} else if (BfCode[i] == '[') {
				inLoop = true;
				loop = i;
			} else if (BfCode[i] == ']') {
				if (inLoop && bf[pointer] != 0) {
					i = loop;
				} else {
					inLoop = false;
				}
			} // no need for else because it will ignore

		}
	}

}
