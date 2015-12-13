/* Generated By:JavaCC: Do not edit this line. Rcc.java */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.record.compiler.generated;

import org.apache.hadoop.record.compiler.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Rcc implements RccConstants
{
	private static String language = "java";
	private static String destDir = ".";
	private static ArrayList<String> recFiles = new ArrayList<String>();
	private static ArrayList<String> cmdargs = new ArrayList<String>();
	private static JFile curFile;
	private static Hashtable<String, JRecord> recTab;
	private static String curDir = ".";
	private static String curFileName;
	private static String curModuleName;

	public static void main(String[] args)
	{
		System.exit(driver(args));
	}

	public static void usage()
	{
		System.err.println("Usage: rcc --language [java|c++] ddl-files");
	}

	public static int driver(String[] args)
	{
		for (int i = 0; i < args.length; i++)
		{
			if ("-l".equalsIgnoreCase(args[i]) || "--language".equalsIgnoreCase(args[i]))
			{
				language = args[i + 1].toLowerCase();
				i++;
			}
			else
			{
				if ("-d".equalsIgnoreCase(args[i]) || "--destdir".equalsIgnoreCase(args[i]))
				{
					destDir = args[i + 1];
					i++;
				}
				else
				{
					if (args[i].startsWith("-"))
					{
						String arg = args[i].substring(1);
						if (arg.startsWith("-"))
						{
							arg = arg.substring(1);
						}
						cmdargs.add(arg.toLowerCase());
					}
					else
					{
						recFiles.add(args[i]);
					}
				}
			}
		}
		if (recFiles.size() == 0)
		{
			usage();
			return 1;
		}
		for (int i = 0; i < recFiles.size(); i++)
		{
			curFileName = recFiles.get(i);
			File file = new File(curFileName);
			try
			{
				FileReader reader = new FileReader(file);
				Rcc parser = new Rcc(reader);
				try
				{
					recTab = new Hashtable<String, JRecord>();
					curFile = parser.Input();
				} catch (ParseException e)
				{
					System.err.println(e.toString());
					return 1;
				}
				try
				{
					reader.close();
				} catch (IOException e)
				{
				}
			} catch (FileNotFoundException e)
			{
				System.err.println("File " + recFiles.get(i) +
						" Not found.");
				return 1;
			}
			try
			{
				int retCode = curFile.genCode(language, destDir, cmdargs);
				if (retCode != 0)
				{
					return retCode;
				}
			} catch (IOException e)
			{
				System.err.println(e.toString());
				return 1;
			}
		}
		return 0;
	}

	final public JFile Input() throws ParseException
	{
		ArrayList<JFile> ilist = new ArrayList<JFile>();
		ArrayList<JRecord> rlist = new ArrayList<JRecord>();
		JFile i;
		ArrayList<JRecord> l;
		label_1:
		while (true)
		{
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case INCLUDE_TKN:
					i = Include();
					ilist.add(i);
					break;
				case MODULE_TKN:
					l = Module();
					rlist.addAll(l);
					break;
				default:
					jj_la1[0] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
			}
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case MODULE_TKN:
				case INCLUDE_TKN:
					;
					break;
				default:
					jj_la1[1] = jj_gen;
					break label_1;
			}
		}
		jj_consume_token(0);
		{
			if (true)
			{
				return new JFile(curFileName, ilist, rlist);
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public JFile Include() throws ParseException
	{
		String fname;
		Token t;
		jj_consume_token(INCLUDE_TKN);
		t = jj_consume_token(CSTRING_TKN);
		JFile ret = null;
		fname = t.image.replaceAll("^\"", "").replaceAll("\"$", "");
		File file = new File(curDir, fname);
		String tmpDir = curDir;
		String tmpFile = curFileName;
		curDir = file.getParent();
		curFileName = file.getName();
		try
		{
			FileReader reader = new FileReader(file);
			Rcc parser = new Rcc(reader);
			try
			{
				ret = parser.Input();
				System.out.println(fname + " Parsed Successfully");
			} catch (ParseException e)
			{
				System.out.println(e.toString());
				System.exit(1);
			}
			try
			{
				reader.close();
			} catch (IOException e)
			{
			}
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + fname +
					" Not found.");
			System.exit(1);
		}
		curDir = tmpDir;
		curFileName = tmpFile;
		{
			if (true)
			{
				return ret;
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public ArrayList<JRecord> Module() throws ParseException
	{
		String mName;
		ArrayList<JRecord> rlist;
		jj_consume_token(MODULE_TKN);
		mName = ModuleName();
		curModuleName = mName;
		jj_consume_token(LBRACE_TKN);
		rlist = RecordList();
		jj_consume_token(RBRACE_TKN);
		{
			if (true)
			{
				return rlist;
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public String ModuleName() throws ParseException
	{
		String name = "";
		Token t;
		t = jj_consume_token(IDENT_TKN);
		name += t.image;
		label_2:
		while (true)
		{
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case DOT_TKN:
					;
					break;
				default:
					jj_la1[2] = jj_gen;
					break label_2;
			}
			jj_consume_token(DOT_TKN);
			t = jj_consume_token(IDENT_TKN);
			name += "." + t.image;
		}
		{
			if (true)
			{
				return name;
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public ArrayList<JRecord> RecordList() throws ParseException
	{
		ArrayList<JRecord> rlist = new ArrayList<JRecord>();
		JRecord r;
		label_3:
		while (true)
		{
			r = Record();
			rlist.add(r);
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case RECORD_TKN:
					;
					break;
				default:
					jj_la1[3] = jj_gen;
					break label_3;
			}
		}
		{
			if (true)
			{
				return rlist;
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public JRecord Record() throws ParseException
	{
		String rname;
		ArrayList<JField<JType>> flist = new ArrayList<JField<JType>>();
		Token t;
		JField<JType> f;
		jj_consume_token(RECORD_TKN);
		t = jj_consume_token(IDENT_TKN);
		rname = t.image;
		jj_consume_token(LBRACE_TKN);
		label_4:
		while (true)
		{
			f = Field();
			flist.add(f);
			jj_consume_token(SEMICOLON_TKN);
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case BYTE_TKN:
				case BOOLEAN_TKN:
				case INT_TKN:
				case LONG_TKN:
				case FLOAT_TKN:
				case DOUBLE_TKN:
				case USTRING_TKN:
				case BUFFER_TKN:
				case VECTOR_TKN:
				case MAP_TKN:
				case IDENT_TKN:
					;
					break;
				default:
					jj_la1[4] = jj_gen;
					break label_4;
			}
		}
		jj_consume_token(RBRACE_TKN);
		String fqn = curModuleName + "." + rname;
		JRecord r = new JRecord(fqn, flist);
		recTab.put(fqn, r);
		{
			if (true)
			{
				return r;
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public JField<JType> Field() throws ParseException
	{
		JType jt;
		Token t;
		jt = Type();
		t = jj_consume_token(IDENT_TKN);
		{
			if (true)
			{
				return new JField<JType>(t.image, jt);
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public JType Type() throws ParseException
	{
		JType jt;
		Token t;
		String rname;
		switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
		{
			case MAP_TKN:
				jt = Map();
			{
				if (true)
				{
					return jt;
				}
			}
			break;
			case VECTOR_TKN:
				jt = Vector();
			{
				if (true)
				{
					return jt;
				}
			}
			break;
			case BYTE_TKN:
				jj_consume_token(BYTE_TKN);
			{
				if (true)
				{
					return new JByte();
				}
			}
			break;
			case BOOLEAN_TKN:
				jj_consume_token(BOOLEAN_TKN);
			{
				if (true)
				{
					return new JBoolean();
				}
			}
			break;
			case INT_TKN:
				jj_consume_token(INT_TKN);
			{
				if (true)
				{
					return new JInt();
				}
			}
			break;
			case LONG_TKN:
				jj_consume_token(LONG_TKN);
			{
				if (true)
				{
					return new JLong();
				}
			}
			break;
			case FLOAT_TKN:
				jj_consume_token(FLOAT_TKN);
			{
				if (true)
				{
					return new JFloat();
				}
			}
			break;
			case DOUBLE_TKN:
				jj_consume_token(DOUBLE_TKN);
			{
				if (true)
				{
					return new JDouble();
				}
			}
			break;
			case USTRING_TKN:
				jj_consume_token(USTRING_TKN);
			{
				if (true)
				{
					return new JString();
				}
			}
			break;
			case BUFFER_TKN:
				jj_consume_token(BUFFER_TKN);
			{
				if (true)
				{
					return new JBuffer();
				}
			}
			break;
			case IDENT_TKN:
				rname = ModuleName();
				if (rname.indexOf('.', 0) < 0)
				{
					rname = curModuleName + "." + rname;
				}
				JRecord r = recTab.get(rname);
				if (r == null)
				{
					System.out.println("Type " + rname + " not known. Exiting.");
					System.exit(1);
				}
			{
				if (true)
				{
					return r;
				}
			}
			break;
			default:
				jj_la1[5] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
		}
		throw new Error("Missing return statement in function");
	}

	final public JMap Map() throws ParseException
	{
		JType jt1;
		JType jt2;
		jj_consume_token(MAP_TKN);
		jj_consume_token(LT_TKN);
		jt1 = Type();
		jj_consume_token(COMMA_TKN);
		jt2 = Type();
		jj_consume_token(GT_TKN);
		{
			if (true)
			{
				return new JMap(jt1, jt2);
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public JVector Vector() throws ParseException
	{
		JType jt;
		jj_consume_token(VECTOR_TKN);
		jj_consume_token(LT_TKN);
		jt = Type();
		jj_consume_token(GT_TKN);
		{
			if (true)
			{
				return new JVector(jt);
			}
		}
		throw new Error("Missing return statement in function");
	}

	public RccTokenManager token_source;
	SimpleCharStream jj_input_stream;
	public Token token, jj_nt;
	private int jj_ntk;
	private int jj_gen;
	final private int[] jj_la1 = new int[6];
	static private int[] jj_la1_0;
	static private int[] jj_la1_1;

	static
	{
		jj_la1_0();
		jj_la1_1();
	}

	private static void jj_la1_0()
	{
		jj_la1_0 = new int[]{0x2800, 0x2800, 0x40000000, 0x1000, 0xffc000, 0xffc000,};
	}

	private static void jj_la1_1()
	{
		jj_la1_1 = new int[]{0x0, 0x0, 0x0, 0x0, 0x1, 0x1,};
	}

	public Rcc(java.io.InputStream stream)
	{
		this(stream, null);
	}

	public Rcc(java.io.InputStream stream, String encoding)
	{
		try
		{
			jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
		token_source = new RccTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 6; i++)
			jj_la1[i] = -1;
	}

	public void ReInit(java.io.InputStream stream)
	{
		ReInit(stream, null);
	}

	public void ReInit(java.io.InputStream stream, String encoding)
	{
		try
		{
			jj_input_stream.ReInit(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 6; i++)
			jj_la1[i] = -1;
	}

	public Rcc(java.io.Reader stream)
	{
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new RccTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 6; i++)
			jj_la1[i] = -1;
	}

	public void ReInit(java.io.Reader stream)
	{
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 6; i++)
			jj_la1[i] = -1;
	}

	public Rcc(RccTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 6; i++)
			jj_la1[i] = -1;
	}

	public void ReInit(RccTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 6; i++)
			jj_la1[i] = -1;
	}

	final private Token jj_consume_token(int kind) throws ParseException
	{
		Token oldToken;
		if ((oldToken = token).next != null)
		{
			token = token.next;
		}
		else
		{
			token = token.next = token_source.getNextToken();
		}
		jj_ntk = -1;
		if (token.kind == kind)
		{
			jj_gen++;
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	final public Token getNextToken()
	{
		if (token.next != null)
		{
			token = token.next;
		}
		else
		{
			token = token.next = token_source.getNextToken();
		}
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	final public Token getToken(int index)
	{
		Token t = token;
		for (int i = 0; i < index; i++)
		{
			if (t.next != null)
			{
				t = t.next;
			}
			else
			{
				t = t.next = token_source.getNextToken();
			}
		}
		return t;
	}

	final private int jj_ntk()
	{
		if ((jj_nt = token.next) == null)
		{
			return (jj_ntk = (token.next = token_source.getNextToken()).kind);
		}
		else
		{
			return (jj_ntk = jj_nt.kind);
		}
	}

	private java.util.Vector<int[]> jj_expentries = new java.util.Vector<int[]>();
	private int[] jj_expentry;
	private int jj_kind = -1;

	public ParseException generateParseException()
	{
		jj_expentries.removeAllElements();
		boolean[] la1tokens = new boolean[33];
		for (int i = 0; i < 33; i++)
		{
			la1tokens[i] = false;
		}
		if (jj_kind >= 0)
		{
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 6; i++)
		{
			if (jj_la1[i] == jj_gen)
			{
				for (int j = 0; j < 32; j++)
				{
					if ((jj_la1_0[i] & (1 << j)) != 0)
					{
						la1tokens[j] = true;
					}
					if ((jj_la1_1[i] & (1 << j)) != 0)
					{
						la1tokens[32 + j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 33; i++)
		{
			if (la1tokens[i])
			{
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.addElement(jj_expentry);
			}
		}
		int[][] exptokseq = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++)
		{
			exptokseq[i] = jj_expentries.elementAt(i);
		}
		return new ParseException(token, exptokseq, tokenImage);
	}

	final public void enable_tracing()
	{
	}

	final public void disable_tracing()
	{
	}

}
