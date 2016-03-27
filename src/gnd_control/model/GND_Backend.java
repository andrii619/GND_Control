package gnd_control.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <b>GND_Backend</b> class implements the Backend interface.
 * @see Bachend
 *
 */
public class GND_Backend implements Backend, Serializable {
	private static final File dataDir=new File("data");
	private static final File dataFile=new File(dataDir+File.separator+"users.ser");
	static final long serialVersionUID=1L;

	public GND_Backend()
	{
		//check if data folder exists. If now create the data folder.
				if(!dataDir.exists())
				{
					dataDir.mkdir();
					dataDir.setWritable(true, false);//anyone can write to folder.
					dataDir.setReadable(true, false);//anyone can read.
				}
	}
	
	@Override
	public List<Profile> getProfiles() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Profile> existingProfiles=new ArrayList<Profile>();
		if(!dataFile.exists())
    	{
    		return existingProfiles;
    	}
		ObjectInputStream in;
    	try{
    	in=new ObjectInputStream(new FileInputStream(dataFile));
		existingProfiles= (List<Profile>)in.readObject();
    	} catch(IOException e)
    	{
    		return existingProfiles;
    	}
		in.close();
		if(existingProfiles==null)
			return new ArrayList<Profile>();
    	return existingProfiles;
	}

	@Override
	public void writeProfile(Profile profile) {
		// TODO Auto-generated method stub
		if(profile == null)
		{
			return;
		}
		List<Profile> existingProfiles=null;
		ObjectInputStream in;
    	if(!dataFile.exists())
    	{
    			try {
    					dataFile.createNewFile();
    					dataFile.setReadable(true,false);
    					dataFile.setWritable(true,false);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    	}
    	try {
			in=new ObjectInputStream(new FileInputStream(dataFile));
			existingProfiles= (ArrayList<Profile>)in.readObject();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			existingProfiles=new ArrayList<Profile>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(existingProfiles == null)
    	{
    		return;
    		//existingProfiles=new ArrayList<Profile>();
    	}
    	for(int i=0; i<existingProfiles.size(); i++)
    	{
    		if(existingProfiles.get(i).getName().compareTo(profile.getName())==0)
    		{
    			existingProfiles.remove(i);
    		}
    	}
    	existingProfiles.add(profile);
    	try {
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(dataFile));
			out.writeObject(existingProfiles);//write the user object to file
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteProfile(String name) {
		// TODO Auto-generated method stub
		List<Profile> existingProfiles=null;
		ObjectInputStream in;
    	if(!dataFile.exists())
    	{
    			try {
    					dataFile.createNewFile();
    					dataFile.setReadable(true,false);
    					dataFile.setWritable(true,false);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    	}
    	try {
			in=new ObjectInputStream(new FileInputStream(dataFile));
			existingProfiles= (ArrayList<Profile>)in.readObject();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			existingProfiles=new ArrayList<Profile>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(existingProfiles == null)
    		return;
    	for(int i=0; i<existingProfiles.size(); i++)
    	{
    		if(existingProfiles.get(i).getName().compareTo(name)==0)
    		{
    			existingProfiles.remove(i);
    		}
    	}
    	try {
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(dataFile));
			out.writeObject(existingProfiles);//write the user object to file
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Profile readProfile(String name) {
		// TODO Auto-generated method stub
		if(name==null)
			return null;
		ObjectInputStream in;
		try{
			in=new ObjectInputStream(new FileInputStream(dataFile));
			List<Profile> profiles= (ArrayList<Profile>)in.readObject();
			Profile n=null;
			if(profiles==null)
			{
				return null;
			}
			for(int i=0;i<profiles.size();i++)
			{
				if(profiles.get(i)==null)
				{
					System.out.println("null profile");
					continue;
					
				}
				if(profiles.get(i).getName()==null)
				{
					System.out.println("null name");
					continue;
				}
				if(profiles.get(i).getName().compareTo(name)==0)
				{
					n=profiles.get(i);
					break;
				}
			}
			in.close(); //close the reader.
			return n;
		}catch(IOException e)
		{
			return null;
		}catch(ClassNotFoundException e)
		{
			return null;
		}
	}

}
