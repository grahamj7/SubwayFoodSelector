package managergui;

import javax.sound.sampled.*;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

@SuppressWarnings("unused")
public class Refund
{
	public Refund(){
		AudioInputStream din = null;
		Random rand = new Random();
		try {
			AudioInputStream in =AudioSystem.getAudioInputStream(new URL("http://wiki.teamfortress.com/w/images/b/bd/Engineer_no01.wav?t=20100709022822"));  
			/*if(rand.nextInt(3)==3)
			{
				 in = AudioSystem.getAudioInputStream(new URL("http://wiki.teamfortress.com/w/images/b/bd/Engineer_no01.wav?t=20100709022822"));
			}
			else if(rand.nextInt(3)==1)
			{
				 in = AudioSystem.getAudioInputStream(new URL("http://wiki.teamfortress.com/w/images/6/6c/Heavy_no02.wav?t=20110320071341"));

			}
			else if(rand.nextInt(3)==2){
				 in = AudioSystem.getAudioInputStream(new URL("http://wiki.teamfortress.com/w/images/0/09/Scout_no02.wav?t=20100625222242"));

			} */
			
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			if(line != null) {
				line.open(decodedFormat);
				byte[] data = new byte[4096];
				// Start
				line.start();

				int nBytesRead;
				while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
					line.write(data, 0, nBytesRead);
				}
				// Stop
				line.drain();
				line.stop();
				line.close();
				din.close();
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(din != null) {
				try { din.close(); } catch(IOException e) { }
			}
		}
	}
	public static void main(String[] args) {
		
	}

}