package com.itsalleasy.walker;

public class TrackingPolicies {
	public static final TrackingPolicy TRACK_NOTHING = new TrackingPolicy(){
		public Tracker createTracker() {
			return new TrackerThatDoesNotTrackAnything();
		}
	};
	public static final TrackingPolicy TRACK_OBJECTS = new TrackingPolicy(){
		public Tracker createTracker() {
			return new TrackerThatRemembersObjects();
		}
	};
	public static final TrackingPolicy TRACK_OBJECTS_AND_PATHS = new TrackingPolicy(){
		public Tracker createTracker() {
			return new TrackerThatRemembersObjectsAndTheirPaths();
		}
	};
}
