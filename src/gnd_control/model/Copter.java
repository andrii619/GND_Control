package gnd_control.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.*;
import com.MAVLink.enums.MAV_AUTOPILOT;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_MODE;
import com.MAVLink.enums.MAV_MODE_FLAG_DECODE_POSITION;
import com.MAVLink.enums.MAV_TYPE;

public class Copter implements Vehicle, ConnectionObserver, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int vehicleId;
	private boolean active;
	private float roll;
	private float pitch;
	private float yaw;
	private float heading;
	private float groundSpeed;
	private float airSpeed;
	private float climbRate;
	private float altitude;
	private float base_mode;
	private float custom_mode;
	
	MAV_AUTOPILOT firmwareType;
	MAV_TYPE vehicleType;
	
	private GPosition home_location;
	private GPosition current_location;
	
	private Battery battery;
	private Attitude attitude;
	
	private boolean armed;
	private boolean connected;
	
	private List<Connection> connections;
	private List<VehicleStateListener> listeners;
	private VehicleStateListener controlListener;
	private HashMap<String,Float> parameters;
	private Timer timer;
	
	public Copter(Connection c)
	{
		this.home_location = new GPosition(40.521899f, -74.459634f);
		this.connected = false;
		connections  = new ArrayList<Connection>();
		connections.add(c);
		c.addObserver(this);
		
		listeners=new ArrayList<VehicleStateListener>();
		
		timer=new Timer();
		
	}
	public Copter()
	{
		this.home_location = new GPosition(40.521899f, -74.459634f);
		this.connected = false;
		connections  = new ArrayList<Connection>();
		listeners=new ArrayList<VehicleStateListener>();
		
		//// delete later
		connections.add(new TCPConnection("temp","127.0.0.1",10000));
		base_mode=0;
		custom_mode=0;
		////
		//timer=new Timer();
	}

	public void addConnection(Connection c)
	{
		this.connections.add(c);
		c.addObserver(this);
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	public void request_parameters()
	{
		msg_param_request_list m =new msg_param_request_list();
		MAVLinkPacket p = m.pack();
		for(int i = 0; i< connections.size(); i++)
		{
			connections.get(i).sendMAV(p);;
		}
	}

	@Override
	public GeoPosition getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHomeLocation(GPosition p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_mode() {
		// TODO Auto-generated method stub
		msg_set_mode m =new msg_set_mode();
		//m.sysid = 255; // id of sending system
		/// custom mode 
		// base mode 
		m.sysid=1;
		m.compid=1;
		// target mode
		m.base_mode=1;//MAV_MODE.MAV_MODE_GUIDED_DISARMED;
		m.custom_mode=4;//MAV_MODE.MAV_MODE_GUIDED_DISARMED;
		//m.custom_mode = MAV_MODE.MAV_MODE_STABILIZE_DISARMED;
		MAVLinkPacket p = m.pack();
		for(int i = 0; i< connections.size(); i++)
		{
			connections.get(i).sendMAV(p);;
		}
	}
	public void send_heartbeat()
	{
		msg_heartbeat m = new msg_heartbeat();
		///m.sysid=255;
		//m.compid=190; // mission planner
		
		
		for(int i=0; i< connections.size();i++)
		{
			connections.get(i).sendMAV(m.pack());
		}
		
	}


	@Override
	public void takeoff(int height) {
		// TODO Auto-generated method stub
		msg_command_long m = new msg_command_long();
		m.compid = 0;
		m.confirmation=0;
		m.target_system = 1;
		m.target_component = 1;
		m.sysid = 255;
		m.command = MAV_CMD.MAV_CMD_NAV_TAKEOFF;
		m.param1=0;
		m.param2=0;
		m.param3=0;
		m.param4=0;
		m.param5=0;
		m.param6=0;
		m.param7 = height;
		MAVLinkPacket p = m.pack();
		for(int i = 0; i< connections.size(); i++)
		{
			connections.get(i).sendMAV(p);;
		}
	}

	@Override
	public double get_battery_level() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean is_armable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addVehicleStateListener(VehicleStateListener l) {
		// TODO Auto-generated method stub
		if(l==null)
			return;
		listeners.add(l);
	}
	public void addControlListener(VehicleStateListener l)
	{
		if(l==null)
			return;
		this.controlListener=l;
	}
	public void transition() {
		// TODO Auto-generated method stub
		msg_command_long m = new msg_command_long();
		m.command = 187; // transition command
		m.confirmation=0;
		m.compid=0;
		m.param1=1;
		m.param2=0;
		m.param3=0;
		m.param4=0;
		m.param5=0;
		m.param6=0;
		m.param7=0;
		for(int i=0; i< connections.size();i++)
		{
			connections.get(i).sendMAV(m.pack());
		}
	}
	@Override
	public void set_armed(boolean armed) {
		// TODO Auto-generated method stub
		msg_command_long m=new msg_command_long();
		m.command=MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
		m.compid = 0;
		m.confirmation=0;
		if(armed)
			m.param1=1;
		else
			m.param1=0;
		m.param2=0;
		m.param3=0;
		m.param4=0;
		m.param5=0;
		m.param6=0;
		m.param7=0;
		
		for(int i=0; i< connections.size();i++)
		{
			connections.get(i).sendMAV(m.pack());
		}
		
	}
	@Override
	public void setLongtitude(float longtitude) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleMAVLink_Message(MAVLinkMessage m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLatitude(double latitude) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLongtitude() {
		// TODO Auto-generated method stub
		
	}
	
	public void takeoff(float altitude)
	{
		
	}

	@Override
	public void set_velocity(double x_velocity, double y_velocity, double z_velocity, int duration) {
		// TODO Auto-generated method stub
		
	}
	public void handleHeartbeat(msg_heartbeat m)
	{
		this.connected=true;
		
		//check for arm/disarm change
		boolean newArmed = (m.base_mode & MAV_MODE_FLAG_DECODE_POSITION.MAV_MODE_FLAG_DECODE_POSITION_SAFETY)!=0;
		if(newArmed!=armed)
		{
			armed=newArmed;
			//notify all observers
			if(this.controlListener!=null)
				controlListener.armedChanged(armed);
			for(int i=0; i< this.listeners.size();i++)
			{
				this.listeners.get(i).armedChanged(armed);
			}
		}
		
		//check for flight mode change
		if(m.base_mode != base_mode || m.custom_mode != custom_mode)
		{
			base_mode=m.base_mode;
			custom_mode=m.custom_mode;
			if(this.controlListener!=null)
				controlListener.flightModeChanged(getFlightMode());
		}
		
	}
	private String getFlightMode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void handleMAVPacket(MAVLinkPacket p) {
		// TODO Auto-generated method stub
		MAVLinkMessage m = p.unpack();
		int id = m.msgid;
		System.out.println("Got: "+m.toString());
		switch(id)
		{
        case msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT:
            //return  new msg_heartbeat(this);
        	msg_heartbeat msg = (msg_heartbeat)m;
        	handleHeartbeat(msg);
        	System.out.println("Copter got heartbeat.MAVVers: "+msg.mavlink_version+" SYSID: "+msg.sysid+
        			" COMP ID "+msg.compid+" mode "+msg.custom_mode);
        	break;
             
        	
        case msg_sys_status.MAVLINK_MSG_ID_SYS_STATUS:
            //return  new msg_sys_status(this);
        	System.out.println("Copter got SYS_STATUS");
        	break;
             
        case msg_system_time.MAVLINK_MSG_ID_SYSTEM_TIME:
            //return  new msg_system_time(this);
        	break;
             
        case msg_ping.MAVLINK_MSG_ID_PING:
            //return  new msg_ping(this);
        	break;
             
        case msg_change_operator_control.MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL:
            //return  new msg_change_operator_control(this);
        	break;
             
        case msg_change_operator_control_ack.MAVLINK_MSG_ID_CHANGE_OPERATOR_CONTROL_ACK:
            //return  new msg_change_operator_control_ack(this);
        	break;
             
        case msg_auth_key.MAVLINK_MSG_ID_AUTH_KEY:
            //return  new msg_auth_key(this);
        	break;
             
        case msg_set_mode.MAVLINK_MSG_ID_SET_MODE:
            //return  new msg_set_mode(this);
        	break;
             
        case msg_param_request_read.MAVLINK_MSG_ID_PARAM_REQUEST_READ:
            //return  new msg_param_request_read(this);
        	break;
             
        case msg_param_request_list.MAVLINK_MSG_ID_PARAM_REQUEST_LIST:
           // return  new msg_param_request_list(this);
        	break;
             
        case msg_param_value.MAVLINK_MSG_ID_PARAM_VALUE:
        	msg_param_value val = (msg_param_value)m;
        	
        	System.out.println("Copter got a param: param_value "+val.toString()+" val "+val.param_value+" param_id "+ val.getParam_Id());
        	this.handle_param_msg(val);
            //return  new msg_param_value(this);
        	break;
             
        case msg_param_set.MAVLINK_MSG_ID_PARAM_SET:
            //return  new msg_param_set(this);
        	break;
             
        case msg_gps_raw_int.MAVLINK_MSG_ID_GPS_RAW_INT:
            //return  new msg_gps_raw_int(this);
        	break;
             
        case msg_gps_status.MAVLINK_MSG_ID_GPS_STATUS:
            //return  new msg_gps_status(this);
        	break;
             
        case msg_scaled_imu.MAVLINK_MSG_ID_SCALED_IMU:
            //return  new msg_scaled_imu(this);
        	break;
             
        case msg_raw_imu.MAVLINK_MSG_ID_RAW_IMU:
            //return  new msg_raw_imu(this);
        	break;
             
        case msg_raw_pressure.MAVLINK_MSG_ID_RAW_PRESSURE:
            //return  new msg_raw_pressure(this);
        	break;
        	
        case msg_scaled_pressure.MAVLINK_MSG_ID_SCALED_PRESSURE:
            //return  new msg_scaled_pressure(this);
        	break;
             
        case msg_attitude.MAVLINK_MSG_ID_ATTITUDE:
            //return  new msg_attitude(this);
        	break;
             
        case msg_attitude_quaternion.MAVLINK_MSG_ID_ATTITUDE_QUATERNION:
           // return  new msg_attitude_quaternion(this);
        	break; 
        	
        case msg_local_position_ned.MAVLINK_MSG_ID_LOCAL_POSITION_NED:
            //return  new msg_local_position_ned(this);
        	break; 
        	
        case msg_global_position_int.MAVLINK_MSG_ID_GLOBAL_POSITION_INT:
            //return  new msg_global_position_int(this);
        	break; 
        	
        case msg_rc_channels_scaled.MAVLINK_MSG_ID_RC_CHANNELS_SCALED:
            //return  new msg_rc_channels_scaled(this);
        	break; 
        	
        case msg_rc_channels_raw.MAVLINK_MSG_ID_RC_CHANNELS_RAW:
            //return  new msg_rc_channels_raw(this);
        	break; 
        	
        case msg_servo_output_raw.MAVLINK_MSG_ID_SERVO_OUTPUT_RAW:
            //return  new msg_servo_output_raw(this);
        	break; 
        	
        case msg_mission_request_partial_list.MAVLINK_MSG_ID_MISSION_REQUEST_PARTIAL_LIST:
            //return  new msg_mission_request_partial_list(this);
        	break; 
        	
        case msg_mission_write_partial_list.MAVLINK_MSG_ID_MISSION_WRITE_PARTIAL_LIST:
            //return  new msg_mission_write_partial_list(this);
        	break; 
        	
        case msg_mission_item.MAVLINK_MSG_ID_MISSION_ITEM:
            //return  new msg_mission_item(this);
        	break; 
        	
        case msg_mission_request.MAVLINK_MSG_ID_MISSION_REQUEST:
            //return  new msg_mission_request(this);
        	break; 
        	
        case msg_mission_set_current.MAVLINK_MSG_ID_MISSION_SET_CURRENT:
            //return  new msg_mission_set_current(this);
        	break; 
        	
        case msg_mission_current.MAVLINK_MSG_ID_MISSION_CURRENT:
            //return  new msg_mission_current(this);
        	break; 
        	
        case msg_mission_request_list.MAVLINK_MSG_ID_MISSION_REQUEST_LIST:
           // return  new msg_mission_request_list(this);
        	break; 
        	
        case msg_mission_count.MAVLINK_MSG_ID_MISSION_COUNT:
            //return  new msg_mission_count(this);
        	break; 
        	
        case msg_mission_clear_all.MAVLINK_MSG_ID_MISSION_CLEAR_ALL:
            //return  new msg_mission_clear_all(this);
        	break; 
        	
        case msg_mission_item_reached.MAVLINK_MSG_ID_MISSION_ITEM_REACHED:
            //return  new msg_mission_item_reached(this);
        	break; 
        	
        case msg_mission_ack.MAVLINK_MSG_ID_MISSION_ACK:
            //return  new msg_mission_ack(this);
        	break; 
        	
        case msg_set_gps_global_origin.MAVLINK_MSG_ID_SET_GPS_GLOBAL_ORIGIN:
            //return  new msg_set_gps_global_origin(this);
        	break; 
        	
        case msg_gps_global_origin.MAVLINK_MSG_ID_GPS_GLOBAL_ORIGIN:
            //return  new msg_gps_global_origin(this);
        	break; 
        	
        case msg_param_map_rc.MAVLINK_MSG_ID_PARAM_MAP_RC:
            ///return  new msg_param_map_rc(this);
        	break; 
        	
        case msg_safety_set_allowed_area.MAVLINK_MSG_ID_SAFETY_SET_ALLOWED_AREA:
            //return  new msg_safety_set_allowed_area(this);
        	break; 
        	
        case msg_safety_allowed_area.MAVLINK_MSG_ID_SAFETY_ALLOWED_AREA:
            //return  new msg_safety_allowed_area(this);
        	break; 
        	
        case msg_attitude_quaternion_cov.MAVLINK_MSG_ID_ATTITUDE_QUATERNION_COV:
            //return  new msg_attitude_quaternion_cov(this);
        	break; 
        	
        case msg_nav_controller_output.MAVLINK_MSG_ID_NAV_CONTROLLER_OUTPUT:
            //return  new msg_nav_controller_output(this);
        	break; 
        	
        case msg_global_position_int_cov.MAVLINK_MSG_ID_GLOBAL_POSITION_INT_COV:
            //return  new msg_global_position_int_cov(this);
        	break; 
        	
        case msg_local_position_ned_cov.MAVLINK_MSG_ID_LOCAL_POSITION_NED_COV:
            //return  new msg_local_position_ned_cov(this);
        	break; 
        	
        case msg_rc_channels.MAVLINK_MSG_ID_RC_CHANNELS:
            //return  new msg_rc_channels(this);
        	break; 
        	
        case msg_request_data_stream.MAVLINK_MSG_ID_REQUEST_DATA_STREAM:
            //return  new msg_request_data_stream(this);
        	break; 
        	
        case msg_data_stream.MAVLINK_MSG_ID_DATA_STREAM:
            //return  new msg_data_stream(this);
        	break; 
        	
        case msg_manual_control.MAVLINK_MSG_ID_MANUAL_CONTROL:
            //return  new msg_manual_control(this);
        	break; 
        	
        case msg_rc_channels_override.MAVLINK_MSG_ID_RC_CHANNELS_OVERRIDE:
            //return  new msg_rc_channels_override(this);
        	break; 
        	
        case msg_mission_item_int.MAVLINK_MSG_ID_MISSION_ITEM_INT:
            //return  new msg_mission_item_int(this);
        	break; 
        	
        case msg_vfr_hud.MAVLINK_MSG_ID_VFR_HUD:
            //return  new msg_vfr_hud(this);
        	break; 
        	
        case msg_command_int.MAVLINK_MSG_ID_COMMAND_INT:
            //return  new msg_command_int(this);
        	break; 
        	
        case msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG:
            //return  new msg_command_long(this);
        	break; 
        	
        case msg_command_ack.MAVLINK_MSG_ID_COMMAND_ACK:
            //return  new msg_command_ack(this);
        	System.out.println("Got command ack");
        	break;
             
        case msg_manual_setpoint.MAVLINK_MSG_ID_MANUAL_SETPOINT:
            //return  new msg_manual_setpoint(this);
        	break;
             
        case msg_set_attitude_target.MAVLINK_MSG_ID_SET_ATTITUDE_TARGET:
           // return  new msg_set_attitude_target(this);
        	break;
             
        case msg_attitude_target.MAVLINK_MSG_ID_ATTITUDE_TARGET:
            //return  new msg_attitude_target(this);
        	break;
             
        case msg_set_position_target_local_ned.MAVLINK_MSG_ID_SET_POSITION_TARGET_LOCAL_NED:
            //return  new msg_set_position_target_local_ned(this);
        	break;
             
        case msg_position_target_local_ned.MAVLINK_MSG_ID_POSITION_TARGET_LOCAL_NED:
            //return  new msg_position_target_local_ned(this);
        	break; 
        	
        case msg_set_position_target_global_int.MAVLINK_MSG_ID_SET_POSITION_TARGET_GLOBAL_INT:
           // return  new msg_set_position_target_global_int(this);
        	break; 
        	
        case msg_position_target_global_int.MAVLINK_MSG_ID_POSITION_TARGET_GLOBAL_INT:
            //return  new msg_position_target_global_int(this);
        	break; 
        	
        case msg_local_position_ned_system_global_offset.MAVLINK_MSG_ID_LOCAL_POSITION_NED_SYSTEM_GLOBAL_OFFSET:
           //return  new msg_local_position_ned_system_global_offset(this);
        	break; 
        	
        case msg_hil_state.MAVLINK_MSG_ID_HIL_STATE:
            //return  new msg_hil_state(this);
        	break; 
        	
        case msg_hil_controls.MAVLINK_MSG_ID_HIL_CONTROLS:
            //return  new msg_hil_controls(this);
        	break; 
        	
        case msg_hil_rc_inputs_raw.MAVLINK_MSG_ID_HIL_RC_INPUTS_RAW:
           // return  new msg_hil_rc_inputs_raw(this);
        	break; 
        	
        case msg_optical_flow.MAVLINK_MSG_ID_OPTICAL_FLOW:
            //return  new msg_optical_flow(this);
        	break; 
        	
        case msg_global_vision_position_estimate.MAVLINK_MSG_ID_GLOBAL_VISION_POSITION_ESTIMATE:
            //return  new msg_global_vision_position_estimate(this);
        	break; 
        	
        case msg_vision_position_estimate.MAVLINK_MSG_ID_VISION_POSITION_ESTIMATE:
            //return  new msg_vision_position_estimate(this);
        	break; 
        	
        case msg_vision_speed_estimate.MAVLINK_MSG_ID_VISION_SPEED_ESTIMATE:
            //return  new msg_vision_speed_estimate(this);
        	break; 
        	
        case msg_vicon_position_estimate.MAVLINK_MSG_ID_VICON_POSITION_ESTIMATE:
            //return  new msg_vicon_position_estimate(this);
        	break; 
        	
        case msg_highres_imu.MAVLINK_MSG_ID_HIGHRES_IMU:
            //return  new msg_highres_imu(this);
        	break; 
        	
        case msg_optical_flow_rad.MAVLINK_MSG_ID_OPTICAL_FLOW_RAD:
            //return  new msg_optical_flow_rad(this);
        	break; 
        	
        case msg_hil_sensor.MAVLINK_MSG_ID_HIL_SENSOR:
            //return  new msg_hil_sensor(this);
        	break; 
        	
        case msg_sim_state.MAVLINK_MSG_ID_SIM_STATE:
            //return  new msg_sim_state(this);
        	break; 
        	
        case msg_radio_status.MAVLINK_MSG_ID_RADIO_STATUS:
            //return  new msg_radio_status(this);
        	break; 
        	
        case msg_file_transfer_protocol.MAVLINK_MSG_ID_FILE_TRANSFER_PROTOCOL:
            //return  new msg_file_transfer_protocol(this);
        	break; 
        	
        case msg_timesync.MAVLINK_MSG_ID_TIMESYNC:
            //return  new msg_timesync(this);
        	break; 
        	
        case msg_camera_trigger.MAVLINK_MSG_ID_CAMERA_TRIGGER:
            //return  new msg_camera_trigger(this);
        	break; 
        	
        case msg_hil_gps.MAVLINK_MSG_ID_HIL_GPS:
            //return  new msg_hil_gps(this);
        	break; 
        	
        case msg_hil_optical_flow.MAVLINK_MSG_ID_HIL_OPTICAL_FLOW:
            //return  new msg_hil_optical_flow(this);
        	break; 
        	
        case msg_hil_state_quaternion.MAVLINK_MSG_ID_HIL_STATE_QUATERNION:
            //return  new msg_hil_state_quaternion(this);
        	break; 
        	
        case msg_scaled_imu2.MAVLINK_MSG_ID_SCALED_IMU2:
            //return  new msg_scaled_imu2(this);
        	break; 
        	
        case msg_log_request_list.MAVLINK_MSG_ID_LOG_REQUEST_LIST:
            //return  new msg_log_request_list(this);
        	break; 
        	
        case msg_log_entry.MAVLINK_MSG_ID_LOG_ENTRY:
            //return  new msg_log_entry(this);
        	break;
             
        case msg_log_request_data.MAVLINK_MSG_ID_LOG_REQUEST_DATA:
            //return  new msg_log_request_data(this);
        	break; 
        	
        case msg_log_data.MAVLINK_MSG_ID_LOG_DATA:
            //return  new msg_log_data(this);
        	break; 
        	
        case msg_log_erase.MAVLINK_MSG_ID_LOG_ERASE:
            //return  new msg_log_erase(this);
        	break; 
        	
        case msg_log_request_end.MAVLINK_MSG_ID_LOG_REQUEST_END:
            //return  new msg_log_request_end(this);
        	break; 
        	
        case msg_gps_inject_data.MAVLINK_MSG_ID_GPS_INJECT_DATA:
            //return  new msg_gps_inject_data(this);
        	break; 
        	
        case msg_gps2_raw.MAVLINK_MSG_ID_GPS2_RAW:
            //return  new msg_gps2_raw(this);
        	break; 
        	
        case msg_power_status.MAVLINK_MSG_ID_POWER_STATUS:
            //return  new msg_power_status(this);
        	break; 
        	
        case msg_serial_control.MAVLINK_MSG_ID_SERIAL_CONTROL:
            //return  new msg_serial_control(this);
        	break; 
        	
        case msg_gps_rtk.MAVLINK_MSG_ID_GPS_RTK:
            //return  new msg_gps_rtk(this);
        	break; 
        	
        case msg_gps2_rtk.MAVLINK_MSG_ID_GPS2_RTK:
            //return  new msg_gps2_rtk(this);
        	break; 
        	
        case msg_scaled_imu3.MAVLINK_MSG_ID_SCALED_IMU3:
            //return  new msg_scaled_imu3(this);
        	break; 
        	
        case msg_data_transmission_handshake.MAVLINK_MSG_ID_DATA_TRANSMISSION_HANDSHAKE:
            //return  new msg_data_transmission_handshake(this);
        	break; 
        	
        case msg_encapsulated_data.MAVLINK_MSG_ID_ENCAPSULATED_DATA:
            //return  new msg_encapsulated_data(this);
        	break; 
        	
        case msg_distance_sensor.MAVLINK_MSG_ID_DISTANCE_SENSOR:
            //return  new msg_distance_sensor(this);
        	break; 
        	
        case msg_terrain_request.MAVLINK_MSG_ID_TERRAIN_REQUEST:
            //return  new msg_terrain_request(this);
        	break; 
        	
        case msg_terrain_data.MAVLINK_MSG_ID_TERRAIN_DATA:
            //return  new msg_terrain_data(this);
        	break; 
        	
        case msg_terrain_check.MAVLINK_MSG_ID_TERRAIN_CHECK:
            //return  new msg_terrain_check(this);
        	break;
             
        case msg_terrain_report.MAVLINK_MSG_ID_TERRAIN_REPORT:
            //return  new msg_terrain_report(this);
        	break; 
        	
        case msg_scaled_pressure2.MAVLINK_MSG_ID_SCALED_PRESSURE2:
            //return  new msg_scaled_pressure2(this);
        	break; 
        	
        case msg_att_pos_mocap.MAVLINK_MSG_ID_ATT_POS_MOCAP:
            //return  new msg_att_pos_mocap(this);
        	break; 
        	
        case msg_set_actuator_control_target.MAVLINK_MSG_ID_SET_ACTUATOR_CONTROL_TARGET:
            //return  new msg_set_actuator_control_target(this);
        	break; 
        	
        case msg_actuator_control_target.MAVLINK_MSG_ID_ACTUATOR_CONTROL_TARGET:
            //return  new msg_actuator_control_target(this);
        	break; 
        	
        case msg_altitude.MAVLINK_MSG_ID_ALTITUDE:
            //return  new msg_altitude(this);
        	break; 
        	
        case msg_resource_request.MAVLINK_MSG_ID_RESOURCE_REQUEST:
            //return  new msg_resource_request(this);
        	break; 
        	
        case msg_scaled_pressure3.MAVLINK_MSG_ID_SCALED_PRESSURE3:
            //return  new msg_scaled_pressure3(this);
        	break; 
        	
        case msg_control_system_state.MAVLINK_MSG_ID_CONTROL_SYSTEM_STATE:
            //return  new msg_control_system_state(this);
        	break; 
        	
        case msg_battery_status.MAVLINK_MSG_ID_BATTERY_STATUS:
            //return  new msg_battery_status(this);
        	break; 
        	
        case msg_autopilot_version.MAVLINK_MSG_ID_AUTOPILOT_VERSION:
            //return  new msg_autopilot_version(this);
        	break; 
        	
        case msg_landing_target.MAVLINK_MSG_ID_LANDING_TARGET:
            //return  new msg_landing_target(this);
        	break; 
        	
        case msg_vibration.MAVLINK_MSG_ID_VIBRATION:
            //return  new msg_vibration(this);
        	break; 
        	
        case msg_home_position.MAVLINK_MSG_ID_HOME_POSITION:
            // return  new msg_home_position(this);
        	break; 
        	
        case msg_set_home_position.MAVLINK_MSG_ID_SET_HOME_POSITION:
            //return  new msg_set_home_position(this);
        	break; 
        	
        case msg_message_interval.MAVLINK_MSG_ID_MESSAGE_INTERVAL:
            //return  new msg_message_interval(this);
        	break; 
        	
        case msg_extended_sys_state.MAVLINK_MSG_ID_EXTENDED_SYS_STATE:
           // return  new msg_extended_sys_state(this);
        	break; 
        	
        case msg_adsb_vehicle.MAVLINK_MSG_ID_ADSB_VEHICLE:
            //return  new msg_adsb_vehicle(this);
        	break; 
        	
        case msg_v2_extension.MAVLINK_MSG_ID_V2_EXTENSION:
            //return  new msg_v2_extension(this);
        	break; 
        	
        case msg_memory_vect.MAVLINK_MSG_ID_MEMORY_VECT:
            //return  new msg_memory_vect(this);
        	break; 
        	
        case msg_debug_vect.MAVLINK_MSG_ID_DEBUG_VECT:
            //return  new msg_debug_vect(this);
        	break; 
        	
        case msg_named_value_float.MAVLINK_MSG_ID_NAMED_VALUE_FLOAT:
            //return  new msg_named_value_float(this);
        	break; 
        	
        case msg_named_value_int.MAVLINK_MSG_ID_NAMED_VALUE_INT:
            //return  new msg_named_value_int(this);
        	break; 
        	
        case msg_statustext.MAVLINK_MSG_ID_STATUSTEXT:
            //return  new msg_statustext(this);
        	System.out.println("Copter got statustext");
        	msg_statustext temp = (msg_statustext)m;
        	System.out.println("Statustext: "+temp.toString()+" "+temp.getText());
        	break; 
        	
        case msg_debug.MAVLINK_MSG_ID_DEBUG:
            //return  new msg_debug(this);
        	break;
		}
		//System.out.println("Drone handles packets");
	}
	private void handle_param_msg(msg_param_value val) {
		// TODO Auto-generated method stub
		String param_id = val.getParam_Id();
		int hash_id = val.hashCode();
		if(param_id.compareTo("")==0)
		{
			
		}
	}
	public void get_position()
	{
		msg_param_request_list m =new msg_param_request_list();
		MAVLinkPacket p = m.pack();
		for(int i = 0; i< connections.size(); i++)
		{
			connections.get(i).sendMAV(p);;
		}
	}
	@Override
	public List<Connection> listConnections() {
		// TODO Auto-generated method stub
		return this.connections;
	}
	@Override
	public void closeConnection(Connection c) {
		// TODO Auto-generated method stub
		if(this.connections==null)
			return;
		if(connections.isEmpty())
			return;
		if(c==null)
			return;
		for(int i=0;i<connections.size();i++)
		{
			if(connections.get(i).equals(c))
			{
				connections.get(i).disconnect();
			}
		}
	}
	@Override
	public boolean isArmed() {
		// TODO Auto-generated method stub
		return this.armed;
	}


}
