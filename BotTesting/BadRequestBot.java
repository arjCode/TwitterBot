package BotTesting;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.*;
import twitter4j.*;


public class BadRequestBot{

	private static String cons_key = "QJSMpetYqSOoo5jKZ1Yovxk4c";
	private static String cons_secret = "lP7YUSIPHOpzm5F2KWg7lzmeCm8g6H7SwPL1sonPfp1HXU21ua";
	private static String access_token = "849773052682534912-hrxIN0JGYFxmfjcEM5o4uOPGcDw8Qb3";
	private static String access_token_secret = "LUyOXetFzom30Ie7nxz6DX16Ug7udhJzpjCSAmzNHAeS9";

	public static Twitter configureBot(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(cons_key)
		.setOAuthConsumerSecret(cons_secret)
		.setOAuthAccessToken(access_token)
		.setOAuthAccessTokenSecret(access_token_secret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}

	public static void main(String... args)
	{

		Twitter badSpamTest = configureBot();
		

		int zip[] = new int[100]; //create array for zipcodes
		int min = 0;

		//random number generator parameters
		Random r = new Random();
		int Low = 10000;
		int High = 99999;
		int i = 0;


		//fill int array with random numbers 
		zip[i] = (int)(r.nextInt(High-Low) + Low);

		//if the current element is included in 40000 to 49999 then go back and refill that element
		if(zip[i] > 40000 && zip[i] < 50000)
		{
			i--;
		}

		//if zip code is in accepted range then tweet
		else if(zip[i] < 40000 || zip[i] > 50000)
		{

			//create string to tweet
			String content = zip[i] +" #LouCrimeZip";

			try {
				Tweet("loumetrobot", content, badSpamTest);
			} catch (TwitterException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		else
		{
			System.out.println("Something has gone wrong.");
		}




	}


	public static void Tweet (String userName, String msg, Twitter badSpamTest) throws TwitterException, InterruptedException 
	{
		String tweet = "@" + userName + " " + msg;

		try
		{
			if (tweet.length() <= 140)
			{		
				//Send the Tweet
				Status status = badSpamTest.updateStatus(tweet);

			}
			else
			{
				//Parse string based on spaces and put the words into an array.
				String delims = "[ ]+";
				String[] tokens = msg.split(delims);
				//Setup new strings
				String newTweet = "";
				String newTweet2 = "..."; //Letting user know that this is from a previous tweet
				//If the new string in less then 100 characters, then add token and space
				//This way words stay intact during tweets
				for (String token : tokens)
				{
					if (newTweet.length() < 100)
						newTweet += token + " ";
					//Once over 100 characters, start new Tweet string
					else
						newTweet2 += token + " ";
				}

				//adding continuation at the end of tweet to let user know more tweets are coming.
				newTweet += "...";
				//Try sending new tweets
				Tweet(userName, newTweet, badSpamTest);
				Tweet(userName, newTweet2, badSpamTest);
			}
		}
		catch (TwitterException tex)
		{
			//WritingToFile.LogError(tex.getExceptionCode(), tex.getErrorMessage());
			 System.out.println(tex.getStatusCode());
			 System.out.println("Bad One Tweet");


		}

		catch (Exception ex)
		{
			//WritingToFile.LogError(ex.toString(), WritingToFile.exceptionStacktraceToString(ex));
			//WritingToFile.CSVFile("InfoLog.csv", tweet, userName, "", "", "NOT SENT");
		}
	}

}

