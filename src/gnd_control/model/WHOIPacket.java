package gnd_control.model;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
import com.MAVLink.common.msg_rc_channels_override;

public class WHOIPacket {
	private ByteBuffer buffer;
	private String command;
	
	public WHOIPacket(MAVLinkPacket packet) // just build the string command
	{
		this.buffer= ByteBuffer.allocate(256);
		command = new String();
		MAVLinkMessage msg = packet.unpack();
		switch(msg.msgid)
		{
			case msg_rc_channels_override.MAVLINK_MSG_ID_RC_CHANNELS_OVERRIDE:
				// expect only one channel to be overridden 
				msg_rc_channels_override tmp=(msg_rc_channels_override)msg;
				/// 001 set throttle
				/// 010 set pitch
				/// 011 set yaw
				/// 100 set roll
				if(tmp.chan1_raw!=MAVLinkPayload.UNSIGNED_SHORT_MAX_VALUE)//yaw 
				{
					// subtract 1000 bias
					tmp.chan1_raw-=1000;
					short data=0;
					data |= (0b011)<<10;
					data |= tmp.chan1_raw;
					if(data<=0x1FFF)
					{
						String t = String.format("%04X", data);
						t=t.substring(2, t.length());
						this.command=String.format("$CCMUC,0,1,%s\r\n", t);//"$CCMUC,0,1,\r\n";
						System.out.println("Command:"+this.command);
					}
				}
				else if(tmp.chan2_raw!=MAVLinkPayload.UNSIGNED_SHORT_MAX_VALUE)//pitch
				{
					// subtract 1000 bias
					tmp.chan2_raw-=1000;
					short data=0;
					data |= (0b010)<<10;
					data |= tmp.chan2_raw;
					if(data<=0x1FFF)
					{
						String t = String.format("%04X", data);
						t=t.substring(2, t.length());
						this.command=String.format("$CCMUC,0,1,%s\r\n", t);
						System.out.println("Command:"+this.command);
					}
				}
				else if(tmp.chan3_raw!=MAVLinkPayload.UNSIGNED_SHORT_MAX_VALUE)//throttle
				{
					tmp.chan3_raw-=1000;
					short data=0;
					data |= (0b001)<<10;
					data |= tmp.chan3_raw;
					if(data<=0x1FFF)
					{
						String t = String.format("%04X", data);
						t=t.substring(2, t.length());
						this.command=String.format("$CCMUC,0,1,%s\r\n", t);
						System.out.println("Command:"+this.command);
					}
				}
				else if(tmp.chan4_raw!=MAVLinkPayload.UNSIGNED_SHORT_MAX_VALUE)// roll
				{
					tmp.chan4_raw-=1000;
					short data = 0;
					data |= (0b100)<<10;
					data |= tmp.chan4_raw;
					if(data<=0x1FFF)
					{
						String t = String.format("%04X", data);
						t=t.substring(2, t.length());
						this.command=String.format("$CCMUC,0,1,%s\r\n", t);
						System.out.println("Command:"+this.command);
					}
				}
				else
				{
					this.command=null;
				}
				break;
			default:
				
		}
	}
	
	public byte[] encodePacket()
	{
		if(this.command==null)
			return null;
		try {
			return this.command.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
