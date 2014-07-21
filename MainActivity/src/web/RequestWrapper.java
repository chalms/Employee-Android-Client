package web;

public abstract class RequestWrapper implements AsyncJsonRequest {
	public CallbackWrapper callback; 
	RequestWrapper(CallbackWrapper c) {
		callback = c; 
	}
}
