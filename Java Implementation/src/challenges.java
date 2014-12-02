
import java.io.*;
import java.net.*;
import org.json.*;
import org.joda.time.DateTime;


import java.util.Scanner;



public class challenges {
	
	//This is the bread and butter of the program.  I used a tutorial that helped me figure this out, but basically what
	//it does is open a new connection object, writes the JSON to it, and closes it.  This was for some reason hard
	//in Java.
	public static BufferedReader Post ( String json, String url) throws Exception{
		OutputStreamWriter output;
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true);
		connection.connect();
		
		output = new OutputStreamWriter(connection.getOutputStream());
		output.write(json);
		output.close();
		return new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}
	
	//The post in the challenge methods are virtually identical to the main method. I just changed the names of keys and what
	//not.  This method executes the string flip using the reverse method in stringbuilder.
	public static void challengeone (String token) throws Exception{
		BufferedReader breader;
		String forwards;
		
		JSONObject post = new JSONObject();
		JSONObject post2 = new JSONObject();
		post.put("token",token);
		breader = Post(post.toString(), "http://challenge.code2040.org/api/getstring");
		JSONObject results = new JSONObject(breader.readLine());
		forwards = results.getString("result");
		
		breader.close();
		
		
		post2.put("token", token);
		post2.put("string", new StringBuilder(forwards).reverse().toString());
		breader = Post(post2.toString(),"http://challenge.code2040.org/api/validatestring" );
		System.out.println("Did it work? " + breader.readLine());
		breader.close();
		
		return;
		
	}
	
	//This implements the haystack search using a simple loop.  I wanted to use binary search, but I would need more
	//time to find out how to do that properly with a JSON array
	public static void challengetwo (String token) throws Exception{
		BufferedReader breader;
		
		JSONObject post = new JSONObject();
		JSONObject post2 = new JSONObject();
		post.put("token",token);
		breader = Post(post.toString(), "http://challenge.code2040.org/api/haystack");
		JSONObject results = new JSONObject(breader.readLine());
		JSONObject dictionary = results.getJSONObject("result");
		String needle = dictionary.getString("needle");
		JSONArray haystack = dictionary.getJSONArray("haystack");
		breader.close();
		
		int i;
		for (i = 0; i < haystack.length(); i++)
		{
			if (haystack.getString(i).equals(needle)) {
				System.out.println("Found it!");
				break;
			}
			if ((haystack.length()-1) == i) {
				System.out.println("There is no needle, in this haystack...");
				break;
			}
		}
		
		
		post2.put("token", token);
		post2.put("needle", i);
		breader = Post(post2.toString(),"http://challenge.code2040.org/api/validateneedle" );
		System.out.println("Did it work? " + breader.readLine());
		breader.close();
		
		return;
		
	}
	
	//This implements the prefix search.  I thought this would be a nested for loop, but then I found out about the
	//startsWith method.
	public static void challengethree (String token) throws Exception{
		BufferedReader breader;
		
		JSONObject post = new JSONObject();
		JSONObject post2 = new JSONObject();
		post.put("token",token);
		breader = Post(post.toString(), "http://challenge.code2040.org/api/prefix");
		JSONObject results = new JSONObject(breader.readLine());
		JSONObject dictionary = results.getJSONObject("result");
		String prefix = dictionary.getString("prefix");
		JSONArray haystack = dictionary.getJSONArray("array");
		breader.close();
		
		int i;
		JSONArray array = new JSONArray();
		for (i = 0; i < haystack.length(); i++)
		{
			if (haystack.getString(i).startsWith(prefix) != true) {
				array.put(haystack.getString(i));
			}
		}
		
		System.out.println("Found " + array.length() + " entries without the prefix " + prefix); 
		
		
		post2.put("token", token);
		post2.put("array", array);
		breader = Post(post2.toString(),"http://challenge.code2040.org/api/validateprefix" );
		System.out.println("Did it work? " + breader.readLine());
		breader.close();
		
		return;
		
	}
	
	//This was the date time challenge, that was actually really easy once I dropped date 4j and used joda
	public static void challengefour (String token) throws Exception{
		BufferedReader breader;
		
		JSONObject post = new JSONObject();
		JSONObject post2 = new JSONObject();
		post.put("token",token);
		breader = Post(post.toString(), "http://challenge.code2040.org/api/time");
		JSONObject results = new JSONObject(breader.readLine());
		JSONObject dictionary = results.getJSONObject("result");
		String olddatestamp = dictionary.getString("datestamp");
		int seconds = dictionary.getInt("interval");
		breader.close();
		//Had a lot of trouble with this.  First I tried date4j which didnt do anything for me, so instead I resorted to joda.
		//It simply creates a new DateTime and adds the interval to it.  Joda is a great library.
		DateTime datestamp = DateTime.parse(olddatestamp);
		DateTime newdatestamp = datestamp.plusSeconds(seconds);

		
		System.out.println(datestamp.toString() + " is now " + newdatestamp.toString()); 
		
		
		post2.put("token", token);
		post2.put("datestamp", newdatestamp.toString());
		breader = Post(post2.toString(),"http://challenge.code2040.org/api/validatetime" );
		System.out.println("Did it work? " + breader.readLine());
		breader.close();
		
		return;
		
	}
	
	public static void status(String token) throws Exception {
		BufferedReader breader;
		
		JSONObject post = new JSONObject();
		post.put("token",token);
		breader = Post(post.toString(), "http://challenge.code2040.org/api/status");
		System.out.println(breader.readLine());
		
		breader.close();
	}

	
	public static void main(String[] args) throws Exception {
		BufferedReader breader;
		
		JSONObject post = new JSONObject();
		post.put("email","kmjf@princeton.edu");
		post.put("github", "https://github.com/darthfrazier");
		breader = Post(post.toString(), "http://challenge.code2040.org/api/register");
		JSONObject results = new JSONObject(breader.readLine());
		String token = results.getString("result");
		System.out.println(token);
		breader.close();
		
		Scanner user_input = new Scanner(System.in);
		System.out.println("Connection complete. Continue with part 1? (y/n)");
		char answer = user_input.next(".").charAt(0);
		if (answer == 'y' || answer == 'Y') {
			challengeone(token);
		}
		else {
			status(token);
			user_input.close();
			return;
		}
		
		System.out.println("Continue with part 2? (y/n)");
		answer = user_input.next(".").charAt(0);
		if (answer == 'y' || answer == 'Y') {
			challengetwo(token);
		}
		else {
			status(token);
			user_input.close();
			return;	
		}
			
		System.out.println("Continue with part 3?");
		answer = user_input.next(".").charAt(0);
		if (answer == 'y' || answer == 'Y') {
			challengethree(token);
		}
		else {
			status(token);
			user_input.close();
			return;
		}	
			
		System.out.println("Continue with part 4?");
		answer = user_input.next(".").charAt(0);
		if (answer == 'y' || answer == 'Y') {
			challengefour(token);

		}
		else {
			status(token);
			user_input.close();
			return;
		}
		
		user_input.close();
		System.out.println();
		status(token);
		return;
		
		
			

	}
	
	
}
