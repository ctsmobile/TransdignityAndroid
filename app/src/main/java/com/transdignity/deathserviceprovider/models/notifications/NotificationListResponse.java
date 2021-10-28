package com.transdignity.deathserviceprovider.models.notifications;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationListResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private String success;

	@SerializedName("title")
	private String title;

	@SerializedName("message")
	private String message;

	@SerializedName("error")
	private Error error;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setError(Error error){
		this.error = error;
	}

	public Error getError(){
		return error;
	}
	public class Error{

	}
	public class DataItem{

		@SerializedName("entity_type")
		private String entityType;

		@SerializedName("entity_type_event")
		private String entityTypeEvent;

		@SerializedName("notification_timestamp")
		private String notificationTimestamp;

		@SerializedName("read_status")
		private String readStatus;

		@SerializedName("notification_id")
		private String notificationId;

		@SerializedName("entity_type_id")
		private String entityTypeId;

		@SerializedName("notification_to")
		private String notificationTo;

		@SerializedName("notification_message")
		private String notificationMessage;

		@SerializedName("notification_by")
		private String notificationBy;

		@SerializedName("view_status")
		private String viewStatus;

		public void setEntityType(String entityType){
			this.entityType = entityType;
		}

		public String getEntityType(){
			return entityType;
		}

		public void setEntityTypeEvent(String entityTypeEvent){
			this.entityTypeEvent = entityTypeEvent;
		}

		public String getEntityTypeEvent(){
			return entityTypeEvent;
		}

		public void setNotificationTimestamp(String notificationTimestamp){
			this.notificationTimestamp = notificationTimestamp;
		}

		public String getNotificationTimestamp(){
			return notificationTimestamp;
		}

		public void setReadStatus(String readStatus){
			this.readStatus = readStatus;
		}

		public String getReadStatus(){
			return readStatus;
		}

		public void setNotificationId(String notificationId){
			this.notificationId = notificationId;
		}

		public String getNotificationId(){
			return notificationId;
		}

		public void setEntityTypeId(String entityTypeId){
			this.entityTypeId = entityTypeId;
		}

		public String getEntityTypeId(){
			return entityTypeId;
		}

		public void setNotificationTo(String notificationTo){
			this.notificationTo = notificationTo;
		}

		public String getNotificationTo(){
			return notificationTo;
		}

		public void setNotificationMessage(String notificationMessage){
			this.notificationMessage = notificationMessage;
		}

		public String getNotificationMessage(){
			return notificationMessage;
		}

		public void setNotificationBy(String notificationBy){
			this.notificationBy = notificationBy;
		}

		public String getNotificationBy(){
			return notificationBy;
		}

		public void setViewStatus(String viewStatus){
			this.viewStatus = viewStatus;
		}

		public String getViewStatus(){
			return viewStatus;
		}
	}
}